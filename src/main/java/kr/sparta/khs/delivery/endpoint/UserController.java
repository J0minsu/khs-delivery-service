package kr.sparta.khs.delivery.endpoint;

import jakarta.validation.Valid;
import kr.sparta.khs.delivery.config.holder.Result;
import kr.sparta.khs.delivery.domain.report.vo.ReportVO;
import kr.sparta.khs.delivery.domain.user.entity.AuthType;
import kr.sparta.khs.delivery.domain.user.service.UserService;
import kr.sparta.khs.delivery.domain.user.vo.UserVO;
import kr.sparta.khs.delivery.endpoint.dto.req.SortStandard;
import kr.sparta.khs.delivery.endpoint.dto.req.UserModifyRequest;
import kr.sparta.khs.delivery.endpoint.dto.res.ReportResponse;
import kr.sparta.khs.delivery.endpoint.dto.res.UserResponse;
import kr.sparta.khs.delivery.security.SecurityUserDetails;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.BadRequestException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;


    @GetMapping
    @PreAuthorize("hasAnyRole('MANAGER', 'MASTER')")
    public ResponseEntity<Result<Page<UserResponse>>> search(
            @RequestParam(defaultValue = "0") int pageNumber,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "") String keyword,
            @RequestParam(defaultValue = "CREATED_DESC") SortStandard sort) {

        Page<UserVO> users =  userService.findUsers(
                keyword, PageRequest.of(pageNumber, size, sort.getSort())
        );

        Page<UserResponse> result = users.map(this::toUserResponse);

        return ResponseEntity.ok(Result.success(result));

    }

    @PatchMapping("/{userId}")
    public ResponseEntity<Result<UserResponse>> modifyUser(
            @PathVariable Integer userId,
            @AuthenticationPrincipal SecurityUserDetails userDetails,
            @RequestBody @Valid UserModifyRequest request,
            BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            throw new IllegalArgumentException("");
        }

        if(userDetails.getAuthType() == AuthType.CUSTOMER && userId != userDetails.getId()) {
            throw new IllegalArgumentException("Customers can only modify their own infos.");
        }

        UserVO user = userService.modifyUser(userId, request);

        UserResponse result = toUserResponse(user);

        return ResponseEntity.ok(Result.success(result));

    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Result<Void>> deleteUser(
            @PathVariable Integer userId,
            @AuthenticationPrincipal SecurityUserDetails userDetails
    ) {
        if(userDetails.getAuthType() != AuthType.MASTER && userId != userDetails.getId()) {
            throw new IllegalArgumentException("Customers can only delete their own.");
        }

        userService.delete(userId);

        return ResponseEntity.noContent().build();

    }

    public UserResponse toUserResponse(UserVO user) {
        return new UserResponse(user.getId(), user.getName(), user.getEmail(), user.getContact(), user.getAddress());
    }

}
