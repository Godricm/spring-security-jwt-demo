package org.buding.springsecurityjwtdemo.exception;

import org.springframework.http.HttpStatus;

/**
 * @program: spring-security-jwt-demo
 * @author: miaochen
 * @create: 2019-07-05 09:46
 * @description:自定义异常
 **/
public class CustomException  extends RuntimeException {


    private static final long serialVersionUID = -2953100687098032377L;

    private final String message;
    private final HttpStatus httpStatus;

    public CustomException(String message, HttpStatus httpStatus) {
        this.message = message;
        this.httpStatus = httpStatus;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
