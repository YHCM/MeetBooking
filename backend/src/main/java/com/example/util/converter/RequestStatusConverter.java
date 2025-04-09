package com.example.util.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import com.example.entity.RequestStatus;

@Component
public class RequestStatusConverter implements Converter<String, RequestStatus> {
    @Override
    public RequestStatus convert(@NonNull String source) {
        return RequestStatus.valueOf(source.toUpperCase());
    }
}
