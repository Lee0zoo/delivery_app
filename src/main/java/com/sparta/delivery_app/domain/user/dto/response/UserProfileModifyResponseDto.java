package com.sparta.delivery_app.domain.user.dto.response;

import com.sparta.delivery_app.domain.user.entity.User;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserProfileModifyResponseDto {
    private String nickName;
    private String name;
    private String address;
    private int myMenuCount;

    public static UserProfileModifyResponseDto of(User user, int totalMyMenu) {
        return UserProfileModifyResponseDto.builder()
                .nickName(user.getNickName())
                .name(user.getName())
                .address(user.getUserAddress())
                .myMenuCount(totalMyMenu)
                .build();
    }
}
