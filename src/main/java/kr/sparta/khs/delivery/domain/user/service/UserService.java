package kr.sparta.khs.delivery.domain.user.service;

import kr.sparta.khs.delivery.domain.review.entity.Review;
import kr.sparta.khs.delivery.domain.review.vo.ReviewVO;
import kr.sparta.khs.delivery.domain.user.entity.User;
import kr.sparta.khs.delivery.domain.user.repository.UserRepository;
import kr.sparta.khs.delivery.domain.user.vo.UserVO;
import kr.sparta.khs.delivery.endpoint.dto.req.SignUpRequest;
import kr.sparta.khs.delivery.endpoint.dto.req.UserModifyRequest;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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

    @Cacheable(cacheNames = "userVO", key = "args[0]")
    public UserVO findByUsername(String username) {

        User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("can not find user"));

        return user.toUserVO();

    }

    @Transactional
    @CachePut(cacheNames = "userVO", key = "args[0]")
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

    @Transactional
    @CachePut(cacheNames = "userVO", key = "args[0]")
    public UserVO modifyUser(Integer userId, UserModifyRequest request) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException("can not find user"));

        user.modify(
                request.getName(), request.getEmail(),
                request.getContact(), request.getAddress());

        return user.toUserVO();

    }

    @Transactional
    public void delete(Integer userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException("can not find user"));

        user.delete(userId);

    }

    public Page<UserVO> findUsers(String keyword, PageRequest pageRequest) {

        Page<User> users = userRepository.findByKeyword(keyword, keyword, pageRequest);

        Page<UserVO> result = users.map(User::toUserVO);

        return result;

    }
}
