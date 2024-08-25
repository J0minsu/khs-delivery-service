package kr.sparta.khs.delivery.domain.ai.service;

import kr.sparta.khs.delivery.domain.ai.repository.AIRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class AIService {

    private final AIRepository aiRepository;

}
