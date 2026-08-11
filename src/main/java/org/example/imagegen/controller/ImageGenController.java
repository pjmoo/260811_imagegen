package org.example.imagegen.controller;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import org.example.imagegen.dto.ImageResultDTO;
import org.example.imagegen.service.ImageGenService;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
            @Validated @NotBlank @Size(max = 500) @RequestParam String prompt,
            RedirectAttributes ra
    ) {
        System.out.println("prompt = " + prompt);
//        GenResultDTO result = imageGenService.generate(prompt);
//        System.out.println("result = " + result);
        ImageResultDTO result = imageGenService.generateImage(prompt);
        ra.addFlashAttribute("result", result);
        return "redirect:/gen";
    }

    @GetMapping("/{filename}")
    // import org.springframework.core.io.Resource;
    public ResponseEntity<Resource> download(@PathVariable String filename) {
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(imageGenService.download(filename));
    }
}
