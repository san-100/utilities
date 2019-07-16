package com.bbva.intranet.utilities.firestore.exceptions;

/**
 * Created by Omar on 6/23/17.
 */
public class MultipleDocumentsException extends Exception {

    private transient Throwable cause;
    private transient String message;

    public static final String MESSAGE = "Multiples documents retrieved";

    public MultipleDocumentsException() {
        super();
    }

    public MultipleDocumentsException(final Throwable cause) {
        this.cause = cause;
    }

    public MultipleDocumentsException(final String message) {
        this.message = message;
    }

    public MultipleDocumentsException(final String message, final Throwable cause) {
        this.message = message;
        this.cause = cause;
    }

    @Override
    public Throwable getCause() {
        return this.cause;
    }

    @Override
    public String getMessage() {
        return this.message;
    }
}
