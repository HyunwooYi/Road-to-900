package com.backend.roadto900.exhandler;

import com.backend.roadto900.exception.GeneralException;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = {GeneralException.class})
    protected ResponseEntity handleGeneralException(GeneralException e){
        Map<String,String> result = new HashMap<>();
        result.put("ErrorMessage",e.getMessage());
        return ResponseEntity.status(e.getCode()).body(result);
    }
    @ExceptionHandler(HttpMessageNotReadableException.class)
<<<<<<< Updated upstream
    protected ResponseEntity<String> handleHttpMessageNotReadable(HttpMessageNotReadableException ex) {
        return ResponseEntity.badRequest().body("ErrorMessage"+":"+ " 삭제할 단어를 체크하지 않음 "+" Code: 400");
=======
    protected ResponseEntity<String> handleHttpMessageNotReadable(HttpMessageNotReadableException e) {
        Map<String,String> result = new HashMap<>();
        result.put("ErrorMessage","잘못된 형식의 단어 요청입니다.");

        // Convert map to JSON
        String jsonResult = "";
        ObjectMapper mapper = new ObjectMapper();
        try {
            jsonResult = mapper.writeValueAsString(result);
        } catch (JsonProcessingException ex) {
            ex.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(jsonResult);
>>>>>>> Stashed changes
    }
}
