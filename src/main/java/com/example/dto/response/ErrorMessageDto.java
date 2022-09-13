package com.example.dto.response;

import java.util.Date;
import lombok.Data;

@Data
public class ErrorMessageDto {
    private int statusCode;
    private Date timestamp;
    private String message;

    public ErrorMessageDto(int statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
        this.timestamp = new Date();
    }
}
