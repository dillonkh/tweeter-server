package edu.byu.cs.tweeter.view.asyncTasks;

import android.os.AsyncTask;

import edu.byu.cs.tweeter.model.domain.UploadRequest;
import edu.byu.cs.tweeter.net.response.UploadResponse;
import edu.byu.cs.tweeter.presenter.LoginPresenter;


public class UploadImageTask extends AsyncTask<UploadRequest, Void, UploadResponse> {

    private final LoginPresenter presenter;
    private final uploadImageObserver observer;

    public interface uploadImageObserver {
        void uploadResponded(UploadResponse uploaded);
    }

    public UploadImageTask(LoginPresenter presenter, uploadImageObserver observer) {
        this.presenter = presenter;
        this.observer = observer;
    }


    @Override
    protected UploadResponse doInBackground(UploadRequest... uploadRequests) {
        UploadResponse response = presenter.uploadImage(uploadRequests[0]);

        return response;

    }

    @Override
    protected void onPostExecute(UploadResponse uploaded) {

        if(observer != null) {
            observer.uploadResponded(uploaded);
        }
    }
}
