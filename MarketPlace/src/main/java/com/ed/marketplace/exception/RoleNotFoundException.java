package com.ed.marketplace.exception;

public class RoleNotFoundException extends RuntimeException {

    public RoleNotFoundException(String roleName) {

        super(String.format("Role %s not found", roleName));
    }
}
