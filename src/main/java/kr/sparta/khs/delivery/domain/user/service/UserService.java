package kr.sparta.khs.delivery.domain.user.service;

import kr.sparta.khs.delivery.domain.user.entity.User;
import kr.sparta.khs.delivery.domain.user.repository.UserRepository;
import kr.sparta.khs.delivery.domain.user.vo.UserVO;
import kr.sparta.khs.delivery.endpoint.dto.req.SignUpRequest;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;

    public UserVO findById(Integer id) {

        User user = userRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException("can not find user"));

        return user.toUserVO();

    }

    public UserVO findByUsername(String username) {

        User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("can not find user"));

        return user.toUserVO();

    }

    @Transactional
    public UserVO entry(SignUpRequest request) {

        User user = User.createUser(request.getUsername(), request.getPassword(),
                request.getName(), request.getEmail(), request.getContact(), request.getAddress(),
                request.getAuthType());

        try {

            User saved = userRepository.save(user);
            return saved.toUserVO();

        } catch (DataIntegrityViolationException e) {

            throw new IllegalArgumentException("Already exists");

        }

    }
}
