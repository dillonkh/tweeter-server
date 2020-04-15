package edu.byu.cs.tweeter.server.model.request;

public class UploadRequest {
    public String byteArray;
    public String fileName;

    public UploadRequest(String byteArray, String fileName) {
        this.byteArray = byteArray;
        this.fileName = fileName;
    }

    public UploadRequest() {}

    public String getByteArray() {
        return byteArray;
    }

    public void setByteArray(String byteArray) {
        this.byteArray = byteArray;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
