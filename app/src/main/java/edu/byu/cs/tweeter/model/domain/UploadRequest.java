package edu.byu.cs.tweeter.model.domain;


public class UploadRequest {
    private String byteArray;
    private String fileName;

    public UploadRequest(String byteArray, String fileName) {
        this.byteArray = byteArray;
        this.fileName = fileName;
    }

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
