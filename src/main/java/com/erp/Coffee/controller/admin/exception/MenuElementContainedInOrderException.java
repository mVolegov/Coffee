package com.erp.Coffee.controller.admin.exception;

public class MenuElementContainedInOrderException extends RuntimeException {

    public MenuElementContainedInOrderException(String message) {
        super(message);
    }
}
