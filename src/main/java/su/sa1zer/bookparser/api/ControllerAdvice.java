package su.sa1zer.bookparser.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ControllerAdvice {

    @ExceptionHandler(value = {BindException.class, })
    public ResponseEntity<?> handleBindingResult(BindException exception) {
        return validBindingResult(exception.getBindingResult());
    }

    private ResponseEntity<Object> validBindingResult(BindingResult result) {
        if(result.hasErrors()) {
            Map<String, String> errors = new HashMap<>();

            if(!CollectionUtils.isEmpty(result.getFieldErrors())) {
                for(FieldError e : result.getFieldErrors()) {
                    errors.put(e.getField(), e.getDefaultMessage());
                }
            }

            return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
        }
        return null;
    }
}
