package edu.byu.cs.tweeter.view.main;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.UUID;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import edu.byu.cs.tweeter.R;
import edu.byu.cs.tweeter.model.SessionManager;
import edu.byu.cs.tweeter.model.domain.UploadRequest;
import edu.byu.cs.tweeter.net.request.LoginRequest;
import edu.byu.cs.tweeter.net.request.SignUpRequest;
import edu.byu.cs.tweeter.net.response.LoginResponse;
import edu.byu.cs.tweeter.net.response.UploadResponse;
import edu.byu.cs.tweeter.presenter.LoginPresenter;
import edu.byu.cs.tweeter.presenter.MainPresenter;
import edu.byu.cs.tweeter.view.asyncTasks.GetLoginTask;
import edu.byu.cs.tweeter.view.asyncTasks.GetSignUpTask;
import edu.byu.cs.tweeter.view.asyncTasks.UploadImageTask;

public class LoginActivity extends AppCompatActivity implements
        LoginPresenter.View,
        MainPresenter.View,
        GetSignUpTask.GetLoginObserver,
        GetLoginTask.GetLoginObserver ,
        UploadImageTask.uploadImageObserver
{

    private LoginPresenter presenter;

    private EditText signUpFirst;
    private EditText signUpLast;
    private EditText signUpHandle;
    private EditText signUpPassword;
    private EditText signUpURL;
    private Button selectImageButton;
    private TextView savedText;

    private EditText handleInput;
    private EditText passwordInput;

    public static final int GET_FROM_GALLERY = 3;

    private String fileName = "";

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        presenter = new LoginPresenter(this);

        TextView signUpPrompt = findViewById(R.id.signUpPrompt);
        final CardView signInCard = findViewById(R.id.signInCard);
        signInCard.setVisibility(View.VISIBLE);
        final CardView signUpCard = findViewById(R.id.signUpCard);
        Button signInButton = findViewById(R.id.signInButton);
        Button signUpButton = findViewById(R.id.signUpButton);

        signUpFirst = findViewById(R.id.firstNameInput);
        signUpLast = findViewById(R.id.lastNameInput);
        signUpHandle = findViewById(R.id.signUpHandleInput);
        signUpPassword = findViewById(R.id.signUpPasswordInput);
        signUpURL = findViewById(R.id.signUpImageURL);
        selectImageButton = findViewById(R.id.selectImageButton);
        savedText = findViewById(R.id.savedText);

        handleInput = findViewById(R.id.handleInput);
        passwordInput = findViewById(R.id.passwordInput);

        selectImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI), GET_FROM_GALLERY);
            }
        });

        signUpPrompt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signInCard.setVisibility(View.GONE);
                signUpCard.setVisibility(View.VISIBLE);
            }
        });
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn(view);
            }
        });
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signUp(view);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //Detects request codes
        if(requestCode==GET_FROM_GALLERY && resultCode == Activity.RESULT_OK) {
            Uri selectedImage = data.getData();
            Bitmap bitmap = null;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);

                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
                byte[] byteArray = byteArrayOutputStream.toByteArray();

                uploadImageToS3(byteArray);

            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                return;
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                return;
            }
        }
    }

    private void uploadImageToS3(byte[] byteArray) {
        UploadImageTask task = new UploadImageTask(presenter, this);
        String encoded = Base64.encodeToString(byteArray, Base64.DEFAULT);
        encoded = encoded.replace("\n", "").replace("\r", "");
        String imageID = UUID.randomUUID().toString();
        fileName = "https://tweeter-dillonkh.s3-us-west-2.amazonaws.com/"+imageID+".png";
        task.execute(new UploadRequest(encoded, imageID+".png"));
    }

    private void signIn(View v) {
        if (userInfoIsValid()) {
            authenticated();
        }

    }

    private void signUp(View v) {
        if (userInfoIsValid() && !alreadySignedUp()) {
//            switchToMainActivity(v);
            String imageURL = getImageURL();
            GetSignUpTask getSignUpTask = new GetSignUpTask(presenter,LoginActivity.this);
            SignUpRequest request = new SignUpRequest(
                    signUpFirst.getText().toString(),
                    signUpLast.getText().toString(),
                    signUpHandle.getText().toString(),
                    signUpPassword.getText().toString(),
                    imageURL
            );
            getSignUpTask.execute(request);
        }
    }

    private String getImageURL() {
        if (fileName.equals("")) {
            return signUpURL.getText().toString();
        }
        else {
            return fileName;
        }
    }


    private  void authenticated() {
        GetLoginTask getLoginTask = new GetLoginTask(presenter,LoginActivity.this);
        LoginRequest request = new LoginRequest(
                passwordInput.getText().toString(),
                handleInput.getText().toString()
        );
        getLoginTask.execute(request);


    }

    private boolean userInfoIsValid() {
        return true;
    }

    private boolean alreadySignedUp() {
        return false;
    }

    private void switchToMainActivity() {
//        Toast.makeText(v.getContext(),"TODO: Actually sign in/sign up",Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void userLoginResponded(LoginResponse r) {
        if (r != null) {
            if (r.isAuthentcated() && r.getUserSignedIn() != null) {
                SessionManager.getInstance().setUserLoggedIn(r.getUserSignedIn());
                SessionManager.getInstance().setUserShown(r.getUserSignedIn());
                SessionManager.getInstance().setUserLoggedInAuthToken(r.getAuthToken());
                switchToMainActivity();
            }
            else {
                Toast.makeText(this, "Sorry, something went wrong.", Toast.LENGTH_LONG).show();
            }
        }
        else {
            Toast.makeText(this, "Sorry, something went wrong.", Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public void uploadResponded(UploadResponse uploaded) {
        if (uploaded == null) {
            Toast.makeText(this, "Sorry, something went wrong, try again", Toast.LENGTH_LONG).show();
        } else {
            if (uploaded.isSuccess()) {
                savedText.setVisibility(View.VISIBLE);
            }
        }
    }
}