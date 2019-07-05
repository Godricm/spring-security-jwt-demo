package org.buding.springsecurityjwtdemo.exception;

import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * @program: spring-security-jwt-demo
 * @author: miaochen
 * @create: 2019-07-05 09:48
 * @description:全局异常处理,RestController Advice
 **/
@RestControllerAdvice
public class GlobalExceptionHandlerController {

    @Bean
    public ErrorAttributes errorAttributes(){
        return new DefaultErrorAttributes(){
            @Override
            public Map<String, Object> getErrorAttributes(WebRequest webRequest, boolean includeStackTrace) {
                Map<String,Object> errorAttributes= super.getErrorAttributes(webRequest, includeStackTrace);
                errorAttributes.remove("exception");
                return errorAttributes;
            }
        };
    }

    /**
     * 自定义异常处理
     * @param response
     * @param ex
     * @throws IOException
     */
    @ExceptionHandler(CustomException.class)
    public void handleCustomException(HttpServletResponse response,CustomException ex) throws IOException {
        response.sendError(ex.getHttpStatus().value(),ex.getMessage());
    }

    /**
     * 通用异常处理
     */
    @ExceptionHandler(Exception.class)
    public void handleException(HttpServletResponse response) throws IOException {
        response.sendError(HttpStatus.BAD_REQUEST.value(),"Something went wrong");
    }
}
