package com.co.grandmasfood.domain.exception;

public class NoChangesDetectedException extends DomainException {

    public NoChangesDetectedException(String message) {
        super("NO_CHANGES_DETECTED",
                message);
    }
}