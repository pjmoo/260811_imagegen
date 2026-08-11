package org.example.imagegen.service;

import io.awspring.cloud.s3.ObjectMetadata;
import io.awspring.cloud.s3.S3Template;
import lombok.RequiredArgsConstructor;
import org.example.imagegen.dto.GenRequestDTO;
import org.example.imagegen.dto.GenResultDTO;
import org.example.imagegen.dto.ImageResultDTO;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Base64;
import java.util.UUID;

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

    public ImageResultDTO generateImage(String prompt) {
        String improved = improvePrompt(prompt);
        GenResultDTO result = generate(improved);
        String key = upload(result);
        return new ImageResultDTO(key, prompt, improved);
    }

    private final S3Template s3Template;

    // import org.springframework.beans.factory.annotation.Value;
    @Value("${app.sb.bucket}")
    private String bucket;

    public String upload(GenResultDTO result) {
        String filename = "%s.jpg".formatted(UUID.randomUUID());
        byte[] bytes = Base64.getDecoder().decode(result.result().image());
        InputStream data = new ByteArrayInputStream(bytes);
        ObjectMetadata metadata = ObjectMetadata.builder()
                .contentType(MediaType.IMAGE_JPEG_VALUE)
                .build();
        System.out.println("bucket = " + bucket);
        System.out.println("filename = " + filename);
        s3Template.upload(bucket, filename,
                data, metadata);
        return filename; // 호출 시 Key 값 filename만 return
    }

    public Resource download(String filename) {
        return s3Template.download(bucket, filename);
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
