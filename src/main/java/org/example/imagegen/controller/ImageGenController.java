package org.example.imagegen.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/gen")
@RequiredArgsConstructor
public class ImageGenController {
    @GetMapping
    public String gen() {
        return "gen/page";
    }

    @PostMapping
    public String form() {
        return "redirect:/gen";
    }
}
