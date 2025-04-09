package com.example.controller;

import org.springframework.web.bind.annotation.RestController;

import com.example.model.Result;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
public class RootController {
    @Operation(summary = "欢迎")
    @GetMapping
    public Result<String> hello() {
        return Result.ok("欢迎", "欢迎");
    }
}
