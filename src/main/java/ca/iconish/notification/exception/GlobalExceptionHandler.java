package ca.iconish.notification.exception;

import com.google.firebase.messaging.FirebaseMessagingException;
import javassist.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.AbstractMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@ControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(FirebaseMessagingException.class)
    public ResponseEntity<Map<String, String>> entityNotFoundExceptionHandler(FirebaseMessagingException e) {

        e.printStackTrace();

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Stream.of(new AbstractMap.SimpleEntry<>("error", e.getMessage())).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)));
    }


    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<String> clientNotFound(NotFoundException e) {
        e.printStackTrace();
        logger.warn("not found client for sending notification");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }



    @ExceptionHandler(FireBaseException.class)
    public ResponseEntity<String> fireBaseException(FireBaseException e) {
        e.printStackTrace();
        logger.warn("not found client for sending notification");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }


}
