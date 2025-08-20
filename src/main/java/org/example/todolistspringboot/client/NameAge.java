package org.example.todolistspringboot.client;

import org.example.todolistspringboot.dto.AgeDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "name-age", url = "${user.service.url}")
public interface NameAge {


    @GetMapping
    AgeDto showAge(@RequestParam("name")String name);
}
