package kr.sparta.khs.delivery.domain.ai.service;

import kr.sparta.khs.delivery.domain.ai.entity.AI;
import kr.sparta.khs.delivery.domain.ai.repository.AIRepository;
import kr.sparta.khs.delivery.domain.ai.vo.AIVO;
import kr.sparta.khs.delivery.domain.report.entity.Report;
import kr.sparta.khs.delivery.domain.report.vo.ReportVO;
import kr.sparta.khs.delivery.domain.user.entity.User;
import kr.sparta.khs.delivery.domain.user.repository.UserRepository;
import kr.sparta.khs.delivery.endpoint.dto.req.AIRequest;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
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

    @Transactional
    public AIVO create(AIRequest request) {

        User user = userRepository.getOne(request.getUserId());

        String prompt = request.getPrompt();
        request.setPrompt(prompt + PROMPT_SUFFIX);
        // TODO AI Request

        AI ai = AI.create(request.getPrompt(), "answer", user);

        AI savedAI = aiRepository.save(ai);

        return savedAI.toVO();

    }

    public Page<AIVO> findByUser(Integer id) {

//        User user = userRepository.getOne(id);
        PageRequest pageRequest = PageRequest.of(0, 100);

        Page<AI> ais = aiRepository.findByRequestUserId(id, pageRequest);

        Page<AIVO> result = ais.map(AI::toVO);

        return result;
    }

    @Transactional
    public void delete(UUID aiId, Integer userId) {

        AI ai = aiRepository.findById(aiId)
                .orElseThrow(NoSuchElementException::new);

        if(ai.getRequestUser().getId() != userId){
            throw new IllegalArgumentException("Customers can only delete their own.");
        }

        ai.delete(userId);

    }

    public Page<AIVO> findReports(String keyword, PageRequest pageRequest) {


        Page<AI> reports = aiRepository.findByKeyword(keyword, pageRequest);

        Page<AIVO> result = reports.map(AI::toVO);

        return result;
    }

}
