package com.sparta.delivery_app.domain.admin.adminuser.controller;

import com.sparta.delivery_app.common.globalResponse.RestApiResponse;
import com.sparta.delivery_app.common.security.AuthenticationUser;
import com.sparta.delivery_app.common.status.StatusCode;
import com.sparta.delivery_app.domain.admin.adminuser.service.AdminUserService;
import com.sparta.delivery_app.domain.admin.adminuser.dto.AdminUserResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.nio.file.AccessDeniedException;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/admin/users")
public class AdminUserController {

    private final AdminUserService adminUserService;

    /*
     * 회원 목록 전체 조회
     */
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping ///페이지 바꾸고 싶을 땐 api/admin/users?page=6&size=20 등으로 접속
    public ResponseEntity<RestApiResponse<List<AdminUserResponseDto>>> allUserList(
            @AuthenticationPrincipal AuthenticationUser authenticationUser,
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "5") int size) {

        List<AdminUserResponseDto> allUserList = adminUserService.getAllUserList(page - 1, size, authenticationUser);
        return ResponseEntity.status(StatusCode.CREATED.code)
                .body(RestApiResponse.of("조회 성공", allUserList));
    }

}