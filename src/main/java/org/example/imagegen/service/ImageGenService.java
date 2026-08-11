package org.example.imagegen.service;

import lombok.RequiredArgsConstructor;
import org.example.imagegen.dto.GenRequestDTO;
import org.example.imagegen.dto.GenResultDTO;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
@RequiredArgsConstructor
public class ImageGenService {
    // cfWorkersAiClient
    private final RestClient aiClient;

    public GenResultDTO generate(String prompt) {
//        return new GenResultDTO();
//        return invokeImage(prompt);
        String improved = improvePrompt(prompt);
        return invokeImage(improved);
    }

    private final ChatClient promptImproveClient;

    public String improvePrompt(String prompt) {
        String improved = promptImproveClient
                .prompt().user(prompt)
                .call().content();
        System.out.println("old = " + prompt);
        System.out.println("new = " + improved);
        return improved;
    }

    public GenResultDTO invokeImage(String prompt) {
        // https://developers.cloudflare.com/workers-ai/models/flux-1-schnell/
        String modelName = "@cf/black-forest-labs/flux-1-schnell";
        GenResultDTO result = aiClient.post()
                .uri("/run/%s".formatted(modelName))
                .contentType(MediaType.APPLICATION_JSON)
                .body(new GenRequestDTO(prompt, 4))
                .retrieve()
                .body(GenResultDTO.class);
        return result;
    }
}
