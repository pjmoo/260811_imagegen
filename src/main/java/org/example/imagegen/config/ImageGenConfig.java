package org.example.imagegen.config;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.boot.http.client.ClientHttpRequestFactoryBuilder;
import org.springframework.boot.http.client.HttpClientSettings;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestClient;

import java.time.Duration;

@Configuration
public class ImageGenConfig {
    @Bean
    ChatClient promptImproveClient(ChatModel model) {
        return ChatClient.builder(model)
                .defaultSystem(
                        """
                                - 전달받은 내용을 500자 이내의 영문으로 된 이미지 생성용 프롬프트로 개선
                                - 특징이 잘 드러나게 자세하지만 간결한 표현으로 구성
                                """)
                .defaultOptions(ChatOptions.builder()
                        .temperature(0.3)
                        .maxTokens(1000))
                .build();
    }

    @Bean
    RestClient cfWorkersAiClient(RestClient.Builder b, CFProperty p) {
        HttpClientSettings settings = HttpClientSettings.defaults()
                .withTimeouts( // ms 기본 단위 -> 헷갈리니까 Duration
                        // connection (보낼 때)
                        Duration.ofSeconds(5),
                        // read (받을 때) - 이미지 처리라 오래 걸릴 수 있음
                        Duration.ofSeconds(60)
                );
        String baseUrl = "https://api.cloudflare.com/client/v4/accounts/%s/ai"
                .formatted(p.accountId());
        String authHeader = "Bearer %s".formatted(p.apiToken());
        return b
                .clone()
                .baseUrl(baseUrl)
                .defaultHeader(
                        // import org.springframework.http.HttpHeaders;
                        HttpHeaders.AUTHORIZATION,
                        authHeader
                )
                .requestFactory(ClientHttpRequestFactoryBuilder.detect().build(
                        settings
                ))
                .build();
    }
}
