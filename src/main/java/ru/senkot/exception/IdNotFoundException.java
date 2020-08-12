package ru.senkot.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Id Not Found")
public class IdNotFoundException extends Exception {

    public IdNotFoundException(int id) {
        super(" " + id);
    }

}
