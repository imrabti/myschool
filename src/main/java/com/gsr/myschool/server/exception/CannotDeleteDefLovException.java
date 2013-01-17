package com.gsr.myschool.server.exception;

public class CannotDeleteDefLovException extends ServiceException {

    public CannotDeleteDefLovException(){
        super("cannotDeleteDefLovException");
    }

    public CannotDeleteDefLovException(String message){
        super(message);
    }
}
