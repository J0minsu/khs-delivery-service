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

@Component
@RequiredArgsConstructor
@Slf4j
public class WebClientService {

//    private final WebClient webClient;
//
//    @Value("${bpai.prediction.server.url}")
//   	private String PREDICTION_SERVER_URL;
//
//    @Value("${scai.diagnose.api.url}")
//    private String SCAI_DIAGNOSE_API_URL;
//
//   	@Value("${spring.profiles.active:}")
//   	private String ACTIVE_PROFILES;
//
//    @Autowired
//    private ObjectMapper objectMapper;
//
//    public Mono<PredictionRes> predict(PredictionHolderReq predictionReq) {
//
//        String body;
//
//        try {
//            body = objectMapper.writeValueAsString(predictionReq);
//        } catch (JsonProcessingException e) {
//            throw new RuntimeException(e);
//        }
//
//        Mono<PredictionRes> predictionResMono = webClient
//                .post()
//                .uri(PREDICTION_SERVER_URL + "prediction/bloodpressure")
//                .bodyValue(body)
//                .retrieve()
//                .bodyToMono(PredictionRes.class);
//
//
//        return predictionResMono;
//
//    }
//
//    public Mono<PredictionCheckRes> checkPredictable(PredictionHolderReq predictionReq) {
////    public Mono<Map> checkPredictable(PredictionHolderReq predictionReq) {
//
//        String body;
//
//        try {
//            body = objectMapper.writeValueAsString(predictionReq);
//        } catch (JsonProcessingException e) {
//            throw new RuntimeException(e);
//        }
//
//
//        return webClient
//                .post()
//                .uri(PREDICTION_SERVER_URL + "resource/bloodpressure/check")
//                .bodyValue(body)
//                .retrieve()
//                .bodyToMono(PredictionCheckRes.class);
////                .bodyToMono(Map.class);
//
//    }
//
//    public Mono<DiagnoseAiRes> imageCheck(DiagnoseAiReq diagnoseAiReq){
//
//        MultipartBodyBuilder builder = new MultipartBodyBuilder();
//        try {
//            builder.part("file", new ByteArrayResource(diagnoseAiReq.getFile().getBytes()) {
//                @Override
//                public String getFilename() {
//                    return diagnoseAiReq.getFile().getOriginalFilename();
//                }
//            });
//        }catch (Exception e) {
//            log.error("e {}", e);
//            throw new RuntimeException(e);
//        }
//
//
//        return webClient.mutate()
//                .build()
//                .post()
//                .uri(SCAI_DIAGNOSE_API_URL)
//                .contentType(MediaType.MULTIPART_FORM_DATA)
//                .bodyValue(builder.build())
//                .retrieve().bodyToMono(DiagnoseAiRes.class);
//    }
//
//    public Mono<DiagnoseAiRes> diagnose(DiagnoseAiReq diagnoseAiReq){
//
//        MultipartBodyBuilder builder = new MultipartBodyBuilder();
//        try {
//            builder.part("file", new ByteArrayResource(diagnoseAiReq.getFile().getBytes()) {
//                @Override
//                public String getFilename() {
//                    return diagnoseAiReq.getFile().getOriginalFilename();
//                }
//            });
//        }catch (Exception e) {
//            log.error("e {}", e);
//            throw new RuntimeException(e);
//        }
//
//        builder.part("lesionsite", diagnoseAiReq.getLesionSite());
//
//        return webClient.mutate()
//                .build()
//                .post()
//                .uri(SCAI_DIAGNOSE_API_URL)
//                .contentType(MediaType.MULTIPART_FORM_DATA)
//                .bodyValue(builder.build())
//                .retrieve().bodyToMono(DiagnoseAiRes.class);
//    }

}
