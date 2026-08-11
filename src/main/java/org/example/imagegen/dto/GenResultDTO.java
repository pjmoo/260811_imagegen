package org.example.imagegen.dto;

import java.util.List;

public record GenResultDTO(boolean success, ImageResult result, List<CFMessage> errors) {
    public record ImageResult(String image) {
    } // base64

    public record CFMessage(int code, String message) {
    }
}
