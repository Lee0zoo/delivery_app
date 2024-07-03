package com.sparta.delivery_app.domain.menuLiked.controller;

import com.sparta.delivery_app.common.globalResponse.RestApiResponse;
import com.sparta.delivery_app.common.security.AuthenticationUser;
import com.sparta.delivery_app.common.status.StatusCode;
import com.sparta.delivery_app.domain.menuLiked.service.MenuLikedService;
import com.sparta.delivery_app.domain.openApi.dto.MenuListReadResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users/menuLiked")
public class MenuLikedController {

    private final MenuLikedService menuLikedService;

    @PreAuthorize("hasRole('CONSUMER')")
    @PostMapping("/{menuId}")
    public ResponseEntity<RestApiResponse<Object>> likedAdd(
            @AuthenticationPrincipal AuthenticationUser user,
            @PathVariable final Long menuId
    ) {

        menuLikedService.addLiked(user, menuId);
        return ResponseEntity.status(StatusCode.OK.code)
                .body(RestApiResponse.of("관심 메뉴로 등록되었습니다."));
    }

    @PreAuthorize("hasRole('CONSUMER')")
    @DeleteMapping("/{menuId}")
    public ResponseEntity<RestApiResponse<Object>> likedDelete(
            @AuthenticationPrincipal AuthenticationUser user,
            @PathVariable final Long menuId
    ) {
        menuLikedService.deleteLiked(user, menuId);
        return ResponseEntity.status(StatusCode.OK.code)
                .body(RestApiResponse.of("관심 메뉴 등록이 취소되었습니다."));

    }

    @PreAuthorize("hasRole('CONSUMER')")
    @GetMapping
    public ResponseEntity<RestApiResponse<Object>> likedList(
            @AuthenticationPrincipal AuthenticationUser user,
            @RequestParam(value = "pageNum", required = false, defaultValue = "1") final Integer pageNum,
            @RequestParam(value = "isDesc", required = false, defaultValue = "true") final Boolean isDesc
    ) {
        Page<MenuListReadResponseDto> responseDto = menuLikedService.findMyMenuList(user, pageNum, isDesc);

        return ResponseEntity.status(StatusCode.OK.code)
                .body(RestApiResponse.of("조회에 성공 하였습니다.", responseDto));
    }

    @PreAuthorize("hasRole('CONSUMER')")
    @GetMapping("/menuRank")
    public ResponseEntity<RestApiResponse<Object>> likedList() {
        List<String> list = menuLikedService.countMenuLiked();

        return ResponseEntity.status(StatusCode.OK.code)
                .body(RestApiResponse.of("조회에 성공 하였습니다.", list));
    }
}
