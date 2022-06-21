package ru.mironov.marketapi.domain.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter @Setter
@RequiredArgsConstructor
public class Error {

    private int code;
    private String message;

    public Error(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
