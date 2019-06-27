package com.example.demo.model;

public class ImportResult {
    private int success;
    private int skip;
    private int error;
    private int modifi;
    private int accError;

    public int getSuccess() {
        return success;
    }

    public void setSuccess(int success) {
        this.success = success;
    }

    public int getModifi() {
        return modifi;
    }

    public void setModifi(int modifi) {
        this.modifi = modifi;
    }

    public int getSkip() {
        return skip;
    }

    public void setSkip(int skip) {
        this.skip = skip;
    }

    public int getError() {
        return error;
    }

    public void setError(int error) {
        this.error = error;
    }

    public int getAccError() {
        return accError;
    }

    public void setAccError(int accError) {
        this.accError = accError;
    }

    public ImportResult(){
    };
    public ImportResult(int success,int skip, int error){
        this.success = success;
        this.skip = skip;
        this.error = error;
    }
    public ImportResult(int success,int skip, int error,int accError){
        this.success = success;
        this.skip = skip;
        this.error = error;
        this.accError = accError;
    }
}
