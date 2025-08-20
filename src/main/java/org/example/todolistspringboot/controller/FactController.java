package org.example.todolistspringboot.controller;

import lombok.RequiredArgsConstructor;
import org.example.todolistspringboot.client.NameAge;
import org.example.todolistspringboot.dto.AgeDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/open")
@RequiredArgsConstructor
public class FactController {
    private final NameAge nameAge;


    @GetMapping("/age")
    public AgeDto showAge(@RequestParam String name) {
        return nameAge.showAge(name);
    }
}
