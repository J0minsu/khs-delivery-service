package kr.sparta.khs.delivery.webclient.service;

import kr.sparta.khs.delivery.webclient.service.dto.req.Content;
import kr.sparta.khs.delivery.webclient.service.dto.req.GeminiRequest;
import kr.sparta.khs.delivery.webclient.service.dto.req.Part;
import kr.sparta.khs.delivery.webclient.service.dto.res.GeminiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.net.URLEncoder;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class WebClientService {

    private final WebClient webClient;

    @Value("${gemini.api.key}")
    String key;
    String HTTPS = "https";
    String HOST = "generativelanguage.googleapis.com";
    String BASE_PATH = "v1beta/models/gemini-1.5-flash-latest:generateContent";

    public Mono<GeminiResponse> aiRequest(String prompt) {

        String encoded = URLEncoder.encode(key);

        Mono<GeminiResponse> geminiResponseMono = webClient.mutate()
                .build()
                .post()
                .uri(uriBuilder -> uriBuilder
                            .scheme(HTTPS)
                            .host(HOST)
                            .path(BASE_PATH)
                            .queryParams(makeParams(key))
                            .build()
                )
                .bodyValue(new GeminiRequest(
                        List.of(
                                new Content(
                                        List.of(new Part(prompt))
                                )
                        )
                ))
                .retrieve()
                .bodyToMono(GeminiResponse.class);


        return geminiResponseMono;

    }

    private MultiValueMap<String, String> makeParams(String key) {

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();

        params.add("key", key);

        return params;
    }


}
