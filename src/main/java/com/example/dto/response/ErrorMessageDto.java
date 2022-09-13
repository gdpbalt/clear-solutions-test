package com.example.dto.response;

import java.util.Date;
import java.util.List;
import lombok.Data;

@Data
public class ErrorMessageDto {
    private int statusCode;
    private Date timestamp;
    private List<String> messages;
}
