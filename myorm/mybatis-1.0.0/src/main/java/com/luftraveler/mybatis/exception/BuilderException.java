package com.luftraveler.mybatis.exception;

public class BuilderException extends RuntimeException {

    public BuilderException(String s, Exception e) {
        super(e);
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }

    @Override
    public void printStackTrace() {
        super.printStackTrace();
    }
}
