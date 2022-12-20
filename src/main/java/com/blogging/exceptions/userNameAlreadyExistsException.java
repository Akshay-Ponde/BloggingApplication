package com.blogging.exceptions;

public class userNameAlreadyExistsException extends RuntimeException {

    private String email;
    public userNameAlreadyExistsException(String email) {
        super(String.format("User already exists with UserName : %s" , email));
        this.email = email;
    }
}
