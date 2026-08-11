package org.example.imagegen.dto;

public record ImageResultDTO(
        String filename,
        String prompt,
        String improved) {
}
