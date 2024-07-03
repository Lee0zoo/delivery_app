package com.sparta.delivery_app.domain.user.service;

import com.sparta.delivery_app.common.exception.errorcode.UserErrorCode;
import com.sparta.delivery_app.common.globalcustomexception.UserPasswordMismatchException;
import com.sparta.delivery_app.common.security.AuthenticationUser;
import com.sparta.delivery_app.domain.commen.page.util.PageUtil;
import com.sparta.delivery_app.domain.menu.adapter.MenuAdapter;
import com.sparta.delivery_app.domain.menu.entity.Menu;
import com.sparta.delivery_app.domain.menuLiked.adapter.MenuLikedAdapter;
import com.sparta.delivery_app.domain.store.entity.Store;
import com.sparta.delivery_app.domain.user.adapter.PasswordHistoryAdapter;
import com.sparta.delivery_app.domain.user.adapter.UserAdapter;
import com.sparta.delivery_app.domain.user.dto.request.*;
import com.sparta.delivery_app.domain.user.dto.response.ConsumersSignupResponseDto;
import com.sparta.delivery_app.domain.user.dto.response.ManagersSignupResponseDto;
import com.sparta.delivery_app.domain.user.dto.response.UserProfileModifyResponseDto;
import com.sparta.delivery_app.domain.user.entity.PasswordHistory;
import com.sparta.delivery_app.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserAdapter userAdapter;
    private final PasswordHistoryAdapter passwordHistoryAdapter;
    private final PasswordEncoder passwordEncoder;
    private final MenuLikedAdapter menuLikedAdapter;

    /**
     * consumers 회원가입
     * @param requestDto
     * @return 회원가입 정보 email, nickname
     */
    public ConsumersSignupResponseDto consumersUserAdd(final ConsumersSignupRequestDto requestDto) {
        userAdapter.checkDuplicateEmail(requestDto.email());

        User userData = User.saveUser(requestDto);

        PasswordHistory passwordHistory = PasswordHistory.savePasswordHistory(
                userData, passwordEncoder.encode(requestDto.password())
        );

        userAdapter.saveUser(userData);
        passwordHistoryAdapter.savePasswordHistory(passwordHistory);

        return ConsumersSignupResponseDto.of(userData);
    }

    /**
     * manager 회원가입
     * @param requestDto
     * @return 회원가입 정보 email, nickname
     */
    public ManagersSignupResponseDto managersUserAdd(final ManagersSignupRequestDto requestDto) {
        userAdapter.checkDuplicateEmail(requestDto.email());

        User userData = User.saveUser(requestDto);

        PasswordHistory passwordHistoryData = PasswordHistory.savePasswordHistory(
                userData, passwordEncoder.encode(requestDto.password())
        );

        userAdapter.saveUser(userData);
        passwordHistoryAdapter.savePasswordHistory(passwordHistoryData);
        return ManagersSignupResponseDto.of(userData);
    }

    /**
     * 회원탈퇴
     * @param user
     * @param userResignRequestDto
     */
    @Transactional
    public void resignUser(AuthenticationUser user, final UserResignRequestDto userResignRequestDto) {
        User findUser = userAdapter.queryUserByEmailAndStatus(user.getUsername());
        PasswordHistory passwordHistory = passwordHistoryAdapter.queryPasswordHistoryTop1ByUser(findUser);

        if (!passwordEncoder.matches(userResignRequestDto.password(), passwordHistory.getPassword())) {
            log.error("기존 비밀번호와 불일치");
            throw new UserPasswordMismatchException(UserErrorCode.PASSWORD_NOT_MATCH);
        }

        findUser.updateResignUser();
    }

    /**
     * 프로필 수정
     * @param user
     * @param requestDto
     * @return 회원가입 정보 email, name, address
     */
    @Transactional
    public UserProfileModifyResponseDto modifyProfileUser(
            AuthenticationUser user,final  UserProfileModifyRequestDto requestDto) {
        User findUser = userAdapter.queryUserByEmailAndStatus(user.getUsername());
        PasswordHistory passwordHistory = passwordHistoryAdapter.queryPasswordHistoryTop1ByUser(findUser);

        if (!passwordEncoder.matches(requestDto.password(), passwordHistory.getPassword())) {
            log.error("기존 비밀번호와 불일치");
            throw new UserPasswordMismatchException(UserErrorCode.PASSWORD_NOT_MATCH);
        }

        int totalMyMenu = menuLikedAdapter.queryMyMenu(findUser);

        User updateUser = findUser.updateUser(requestDto);
        return UserProfileModifyResponseDto.of(updateUser, totalMyMenu);
    }

    /**
     * 비밀번호 수정
     * @param user
     * @param requestDto
     */
    @Transactional
    public void modifyPasswordUser(AuthenticationUser user, final UserPasswordModifyRequestDto requestDto) {
        User findUser = userAdapter.queryUserByEmailAndStatus(user.getUsername());
        List<PasswordHistory> passwordhistoryList = passwordHistoryAdapter.queryPasswordHistoryTop4ByUser(findUser);

        for (int i = 0; i < passwordhistoryList.size(); i++) {
            if ((i == 0) && !passwordEncoder.matches(requestDto.password(), passwordhistoryList.get(i).getPassword())) {
                log.error("기존 비밀번호와 불일치");
                throw new UserPasswordMismatchException(UserErrorCode.PASSWORD_NOT_MATCH);
            }
            if (passwordEncoder.matches(requestDto.newPassword(), passwordhistoryList.get(i).getPassword())) {
                log.error("최근 사용한 4개의 비밀번호와 일치");
                throw new UserPasswordMismatchException(UserErrorCode.PASSWORD_MATCH);
            }
        }

        PasswordHistory passwordHistory = PasswordHistory.savePasswordHistory(findUser, passwordEncoder.encode(requestDto.newPassword()));
        passwordHistoryAdapter.savePasswordHistory(passwordHistory);
    }
}
