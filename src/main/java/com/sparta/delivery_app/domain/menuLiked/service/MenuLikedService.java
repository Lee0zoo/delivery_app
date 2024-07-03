package com.sparta.delivery_app.domain.menuLiked.service;

import com.sparta.delivery_app.common.exception.errorcode.LikedErrorCode;
import com.sparta.delivery_app.common.globalcustomexception.LikedDuplicatedException;
import com.sparta.delivery_app.common.globalcustomexception.LikedNotFoundException;
import com.sparta.delivery_app.common.security.AuthenticationUser;
import com.sparta.delivery_app.domain.commen.page.util.PageUtil;
import com.sparta.delivery_app.domain.menu.adapter.MenuAdapter;
import com.sparta.delivery_app.domain.menu.entity.Menu;
import com.sparta.delivery_app.domain.menuLiked.adapter.MenuLikedAdapter;
import com.sparta.delivery_app.domain.menuLiked.entity.MenuLiked;
import com.sparta.delivery_app.domain.openApi.dto.MenuListReadResponseDto;
import com.sparta.delivery_app.domain.user.adapter.UserAdapter;
import com.sparta.delivery_app.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class MenuLikedService {

    private final MenuLikedAdapter menuLikedAdapter;
    private final MenuAdapter menuAdapter;
    private final UserAdapter userAdapter;

    /**
     * 좋아요 등록
     * @param user
     * @param menuId
     */
    @Transactional
    public void addLiked(AuthenticationUser user, Long menuId) {
        Menu findMenu = menuAdapter.queryMenuById(menuId);
        User findUser = userAdapter.queryUserByEmailAndStatus(user.getUsername());

        if(menuLikedAdapter.existsByMenuAndUser(findMenu, findUser)) {
            throw new LikedDuplicatedException(LikedErrorCode.LIKED_ALREADY_REGISTERED_ERROR);
        }

        MenuLiked liked = new MenuLiked(findMenu, findUser);
        menuLikedAdapter.saveLiked(liked);
    }

    /**
     * 좋아요 취소
     * @param user
     * @param menuId
     */
    @Transactional
    public void deleteLiked(AuthenticationUser user, Long menuId) {
        Menu findMenu = menuAdapter.queryMenuById(menuId);
        User findUser = userAdapter.queryUserByEmailAndStatus(user.getUsername());

        if (!menuLikedAdapter.existsByMenuAndUser(findMenu, findUser)) {
            throw new LikedNotFoundException(LikedErrorCode.LIKED_UNREGISTERED_ERROR);
        }

        MenuLiked findLiked = menuLikedAdapter.queryLikedByMenuId(menuId, findUser);
        menuLikedAdapter.deleteLiked(findLiked);
    }

    /**
     * 좋아요 등록한 메뉴 조회
     * @param user
     * @param pageNum
     * @param isDesc
     * @return
     */
    public Page<MenuListReadResponseDto> findMyMenuList(AuthenticationUser user, Integer pageNum, Boolean isDesc) {

        User findUser = userAdapter.queryUserByEmailAndStatus(user.getUsername());

        Pageable pageable = PageUtil.createPageable(pageNum, PageUtil.PAGE_SIZE_FIVE, isDesc);

        return menuLikedAdapter.queryMenuByUser(findUser, pageable);
    }

    /**
     * 좋아요가 많은 메뉴 10개 조회
     * @return
     */
    public List<String> countMenuLiked() {
        return menuLikedAdapter.countMenuLiked();
    }
}
