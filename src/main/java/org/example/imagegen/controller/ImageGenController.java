package org.example.imagegen.controller;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import org.example.imagegen.dto.GenResultDTO;
import org.example.imagegen.service.ImageGenService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/gen")
@RequiredArgsConstructor
public class ImageGenController {
    private final ImageGenService imageGenService;

    @GetMapping
    public String gen() {
        return "gen/page";
    }

    @PostMapping
    public String form(
            @Validated @NotBlank @Size(max = 500) @RequestParam String prompt
    ) {
        System.out.println("prompt = " + prompt);
        GenResultDTO result = imageGenService.generate(prompt);
        System.out.println("result = " + result);
        return "redirect:/gen";
    }
}
