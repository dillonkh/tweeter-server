package edu.byu.cs.tweeter.view.asyncTasks;

import android.os.AsyncTask;

import edu.byu.cs.tweeter.net.request.LoginRequest;
import edu.byu.cs.tweeter.net.request.SignUpRequest;
import edu.byu.cs.tweeter.net.response.LoginResponse;
import edu.byu.cs.tweeter.presenter.LoginPresenter;
import edu.byu.cs.tweeter.presenter.MainPresenter;


public class SignOutTask extends AsyncTask<LoginRequest, Void, LoginResponse> {

    private final MainPresenter mainPresenter;
    private final SignOutObserver observer;

    public interface SignOutObserver {
        void signOutResponded(LoginResponse r);
    }

    public SignOutTask(MainPresenter mainPresenter, SignOutObserver observer) {
        this.mainPresenter = mainPresenter;
        this.observer = observer;
        mainPresenter = null;
    }

    @Override
    protected LoginResponse doInBackground(LoginRequest... signUpRequests) {
        LoginResponse response = mainPresenter.signOut(signUpRequests[0]);
//        loadImages(response);
        return response;
    }

    @Override
    protected void onPostExecute(LoginResponse signInResponse) {

        if(observer != null) {
            observer.signOutResponded(signInResponse);
        }
    }
}
