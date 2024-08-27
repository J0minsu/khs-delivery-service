package kr.sparta.khs.delivery.domain.ai.service;

import kr.sparta.khs.delivery.domain.ai.entity.AI;
import kr.sparta.khs.delivery.domain.ai.repository.AIRepository;
import kr.sparta.khs.delivery.domain.ai.vo.AIVO;
import kr.sparta.khs.delivery.domain.user.entity.User;
import kr.sparta.khs.delivery.domain.user.repository.UserRepository;
import kr.sparta.khs.delivery.endpoint.dto.req.AIRequest;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class AIService {

    private final AIRepository aiRepository;
    private final UserRepository userRepository;
    private final String PROMPT_SUFFIX = ". 답변을 최대한 간결하게 50자 이하로 작성해줘.";

    public AIVO findById(UUID id) {
        AIVO ai = aiRepository.findById(id)
                .orElseThrow(NoSuchElementException::new)
                .toVO();

        return ai;
    }

    public void create(AIRequest request) {

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(NoSuchElementException::new);

        String prompt = request.getPrompt();
        request.setPrompt(prompt + PROMPT_SUFFIX);
        //AI Request


        AI ai = AI.create(request.getPrompt(), "answer", user);

        aiRepository.save(ai);

    }

    public Page<AIVO> findByUser(Integer id) {

        User user = userRepository.findById(id)
                .orElseThrow(NoSuchElementException::new);

        Page<AI> ais = aiRepository.findByRequestUser(user);

        Page<AIVO> result = ais.map(AI::toVO);

        return result;
    }
}
