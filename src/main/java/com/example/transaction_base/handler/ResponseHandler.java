package com.example.transaction_base.handler;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ResponseHandler {

    public ResponseEntity<Object> generateResponse(String message,
                                                   HttpStatus status,
                                                   Object responseObj,
                                                   HttpServletRequest request) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("success",!status.isError());
        map.put("status", status.value());
        map.put("content",responseObj == null ? message : responseObj);
        map.put("timestamp", new Date());
        return new ResponseEntity<>(map,status);
    }

}
