package com.sparta.delivery_app.domain.user.entity;

import com.sparta.delivery_app.domain.commen.BaseTimeEntity;
import com.sparta.delivery_app.domain.liked.entity.Liked;
import com.sparta.delivery_app.domain.menuLiked.entity.MenuLiked;
import com.sparta.delivery_app.domain.review.entity.ManagerReviews;
import com.sparta.delivery_app.domain.review.entity.UserReviews;
import com.sparta.delivery_app.domain.user.dto.request.ConsumersSignupRequestDto;
import com.sparta.delivery_app.domain.user.dto.request.ManagersSignupRequestDto;
import com.sparta.delivery_app.domain.user.dto.request.UserProfileModifyRequestDto;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "user")
public class User extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false, length = 10)
    private String name;

    @Column(nullable = false, length = 20)
    private String nickName;

    @Column(nullable = false)
    private String userAddress;

    @OneToMany(mappedBy = "user")
    private List<Liked> likedList = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<MenuLiked> menuLikedList = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<UserReviews> userReviewList = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<ManagerReviews> managerReviewList = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<PasswordHistory> passwordHistoryList = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserStatus userStatus;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserRole userRole;

    @Column(columnDefinition = "TEXT")
    private String refreshToken;

    @Builder
    public User(String email, String name, String nickName, String userAddress, UserStatus userStatus, UserRole userRole) {
        this.email = email;
        this.name = name;
        this.nickName = nickName;
        this.userAddress = userAddress;
        this.userStatus = userStatus;
        this.userRole = userRole;
    }

    public static User saveUser(final ConsumersSignupRequestDto requestDto) {
        return User.builder()
                .email(requestDto.email())
                .name(requestDto.name())
                .nickName(requestDto.nickName())
                .userAddress(requestDto.address())
                .userStatus(UserStatus.ENABLE)
                .userRole(UserRole.CONSUMER)
                .build();
    }

    public static User saveUser(final ManagersSignupRequestDto requestDto) {
        return User.builder()
                .email(requestDto.email())
                .name(requestDto.name())
                .nickName(requestDto.nickName())
                .userAddress(requestDto.address())
                .userStatus(UserStatus.ENABLE)
                .userRole(UserRole.MANAGER)
                .build();
    }

    public User updateUser(final UserProfileModifyRequestDto requestDto) {
        this.nickName = requestDto.nickName();
        this.name = requestDto.name();
        this.userAddress = requestDto.address();
        return this;
    }

    public void updateRefreshToken(final String newRefreshToken) {
        this.refreshToken = newRefreshToken;
    }

    public void updateResignUser() {
        this.refreshToken = null;
        this.userStatus = UserStatus.DISABLE;
    }
}
