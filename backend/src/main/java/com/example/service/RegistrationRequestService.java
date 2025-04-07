package com.example.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.entity.RegistrationRequest;
import com.example.mapper.RegistrationRequestMapper;

@Service
public class RegistrationRequestService {
    private final RegistrationRequestMapper registrationRequestMapper;

    public RegistrationRequestService(RegistrationRequestMapper registrationRequestMapper) {
        this.registrationRequestMapper = registrationRequestMapper;
    }

    public List<RegistrationRequest> getAllRegistrationRequests() {
        return registrationRequestMapper.selectAllRegistrationRequests();
    }
}
