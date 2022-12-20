package com.blogging.exceptions;



public class InvalidLoginCredentialsException extends RuntimeException {

    String userName;
    String message;

    public InvalidLoginCredentialsException(String userName , Exception ex)
    {
        super(String.format("Invalid credentials username : %s or password. /n %s"  , userName , ex.getMessage()));
        this.userName = userName;
        this.message = ex.getMessage();
    }


}
