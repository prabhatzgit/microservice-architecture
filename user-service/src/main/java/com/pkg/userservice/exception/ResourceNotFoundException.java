package com.pkg.userservice.exception;

public class ResourceNotFoundException extends RuntimeException{
    public ResourceNotFoundException(String s){
        super("Resource not found exceptions");
    }
}