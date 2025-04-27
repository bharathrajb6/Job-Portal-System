package com.example.job_application_service.exceptions;

import com.example.job_application_service.dto.response.ErrorResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Response;
import feign.Util;
import feign.codec.ErrorDecoder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class CustomErrorDecoder implements ErrorDecoder {

    private final ErrorDecoder decoder = new Default();

    private final ObjectMapper objectMapper;

    /**
     * Decode the error response from the another microservice.
     *
     * @param s
     * @param response
     * @return
     */
    @Override
    public Exception decode(String s, Response response) {
        try {
            String errorMessage = Util.toString(response.body().asReader());
            ErrorResponse errorResponse = objectMapper.readValue(errorMessage, ErrorResponse.class);
            throw new JobApplicationException(errorResponse.getMessage());
        } catch (IOException e) {
            return decoder.decode(s, response);
        }
    }
}
