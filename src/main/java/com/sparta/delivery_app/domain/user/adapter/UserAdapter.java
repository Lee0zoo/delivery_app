package com.sparta.delivery_app.domain.user.adapter;

import com.sparta.delivery_app.common.globalcustomexception.UserDuplicatedException;
import com.sparta.delivery_app.common.globalcustomexception.UserNotExistException;
import com.sparta.delivery_app.domain.menuLiked.repository.MenuLikedRepository;
import com.sparta.delivery_app.domain.user.entity.User;
import com.sparta.delivery_app.domain.user.entity.UserStatus;
import com.sparta.delivery_app.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import static com.sparta.delivery_app.common.exception.errorcode.UserErrorCode.*;

@Component
@RequiredArgsConstructor
public class UserAdapter {

    private final UserRepository userRepository;

    public void checkDuplicateEmail(String email) {
        userRepository.findByEmail(email).ifPresent(u -> {
                    throw new UserDuplicatedException(DUPLICATED_USER);
                }
        );
    }

    /*
     * @throws UserNotExistException if(회원가입을 하지 않은 경우)
     */
    public User checkManagerRole(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new UserNotExistException(NOT_SIGNED_UP_USER)
        );
        UserStatus.checkManagerEnable(user);
        return user;
    }

    /**
     * 특정 email 조회
     * Status
     */
    public User queryUserByEmailAndStatus(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotExistException(NOT_SIGNED_UP_USER));
        UserStatus.checkUserStatus(user.getUserStatus());
        return user;
    }

    public User queryUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotExistException(NOT_SIGNED_UP_USER));
    }

    public Page<User> queryAllUserPage(Pageable pageable) {
        return userRepository.findAll(pageable);
    }


    public User saveUser(User userData) {
        return userRepository.save(userData);
    }

}
