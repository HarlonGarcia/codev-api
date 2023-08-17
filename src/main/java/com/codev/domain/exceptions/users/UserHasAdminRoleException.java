package com.codev.domain.exceptions.users;

public class UserHasAdminRoleException extends Exception {

    public UserHasAdminRoleException() {
        super("User already has admin role.");
    }

    public UserHasAdminRoleException(String message) {
        super(message);
    }

    public UserHasAdminRoleException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserHasAdminRoleException(Throwable cause) {
        super("User already has admin role.", cause);
    }

}
