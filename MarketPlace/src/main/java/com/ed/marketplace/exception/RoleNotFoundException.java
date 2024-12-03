package com.ed.marketplace.exception;

public class RoleNotFoundException extends RuntimeException{

    public RoleNotFoundException(String roleName) {

        super("Role " + roleName + " not found");
    }
}
