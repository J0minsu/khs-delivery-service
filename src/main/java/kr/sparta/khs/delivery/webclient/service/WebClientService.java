package kr.sparta.khs.delivery.webclient.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.net.URLEncoder;
import java.util.Map;

@Component
@RequiredArgsConstructor
@Slf4j
public class WebClientService {

    private final WebClient webClient;

    String encodedServiceKey = URLEncoder.encode("UuJOPCi2qcZIX3hit9tiwMYo6rfg5kk8BA/rNeNK1INRhx/MwQXZKQ8rL/0rNr4fDLPpV8vaz+5gO6lboNZKhA==");
    String HTTPS = "https";
    String HOST = "generativelanguage.googleapis.com";
    String BASE_PATH = "v1beta/models/gemini-1.5-flash-latest:generateContent";

    @Autowired
    private ObjectMapper objectMapper;

    /**
     *  return webClient.mutate()
     *                 .build()
     *                 .get()
     *                 .uri(uriBuilder -> uriBuilder
     *                                     .scheme(HTTPS)
     *                                     .host(HOST)
     *                                     .path(BASE_PATH + "getBrTitleInfo")
     *                                     .queryParams(makeParams(cityCountyDistrictCode, areaCode, platGbCode, bun, ji, size, page, start, end))
     *                                     .build()
     *                 )
     *                 .retrieve()
     *                 .bodyToMono(Map.class);
     */

    /*public Mono<Map> predict(String prompt) {

        return webClient.mutate()
                   .build()
                   .get()
                   .uri(uriBuilder -> uriBuilder
                                       .scheme(HTTPS)
                                       .host(HOST)
                                       .path(BASE_PATH + "getBrTitleInfo")
                                       .queryParams(makeParams(cityCountyDistrictCode, areaCode, platGbCode, bun, ji, size, page, start, end))
                                       .build()
                   )
                   .retrieve()
                   .bodyToMono(Map.class);

    }*/



}
