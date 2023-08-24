package com.artyomhack.todo.common.value.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

public interface DomainException {
    String getName();
    String getMessage();

    class BadRequest implements DomainException{

        @Override
        public String getName() {
            return HttpStatus.BAD_REQUEST.name();
        }

        @Override
        public String getMessage() {
            return "Client error (e.g., malformed request syntax, invalid request " +
                    "message framing, or deceptive request routing).";
        }
    }

    class NotFound implements DomainException {

        @Override
        public String getName() {
            return HttpStatus.NOT_FOUND.name();
        }

        @Override
        public String getMessage() {
            return "The server cannot find the requested resource.";
        }
    }

    class ValidateError implements DomainException {

        @Override
        public String getName() {
            return this.getClass().getSimpleName();
        }

        @Override
        public String getMessage() {
            return "Validate error.";
        }
    }
}
