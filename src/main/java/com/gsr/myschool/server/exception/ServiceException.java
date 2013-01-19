package com.gsr.myschool.server.exception;

public class ServiceException extends RuntimeException {

    public ServiceException(){
        super();
    }

    public ServiceException(String message){
        super(message);
    }

}
