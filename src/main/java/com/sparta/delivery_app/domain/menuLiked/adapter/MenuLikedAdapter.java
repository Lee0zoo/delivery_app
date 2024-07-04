package com.sparta.delivery_app.domain.menuLiked.adapter;

import com.sparta.delivery_app.common.exception.errorcode.LikedErrorCode;
import com.sparta.delivery_app.common.globalcustomexception.LikedNotFoundException;
import com.sparta.delivery_app.domain.menu.entity.Menu;
import com.sparta.delivery_app.domain.menuLiked.entity.MenuLiked;
import com.sparta.delivery_app.domain.menuLiked.repository.MenuLikedRepository;
import com.sparta.delivery_app.domain.openApi.dto.MenuListReadResponseDto;
import com.sparta.delivery_app.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class MenuLikedAdapter {

    private final MenuLikedRepository menuLikedRepository;

    /**
     * 이미 좋아요가 등록되었는지 확인
     * @param findMenu
     * @param findUser
     * @return
     */
    public boolean existsByMenuAndUser(Menu findMenu, User findUser) {
        List<Long> menuLikedId = menuLikedRepository.existsByUser(findUser);

        if(menuLikedId.contains(findMenu.getId())) {
            return true;
        }

        return false;
    }

    /**
     * 좋아요 등록
     * @param liked
     */
    public void saveLiked(MenuLiked liked) {
        menuLikedRepository.saveMenuLiked(liked);
    }

    /**
     * 삭제 대상인 좋아요 찾기
     * @param menuId
     * @return
     */
    public MenuLiked queryLikedByMenuId(Long menuId, User user) {
        return menuLikedRepository.findByMenuIdAndUser(menuId, user).orElseThrow(() ->
                new LikedNotFoundException(LikedErrorCode.LIKED_UNREGISTERED_ERROR));
    }

    /**
     * 좋아요 삭제
     * @param findLiked
     */
    public void deleteLiked(MenuLiked findLiked) {
        menuLikedRepository.deleteMenuLiked(findLiked);
    }

    /**
     * 좋아요 페이징 조회
     * @param findUser
     * @param pageable
     * @return
     */
    public Page<MenuListReadResponseDto> queryMenuByUser(User findUser, Pageable pageable) {
        return menuLikedRepository.findByUser(findUser, pageable);
    }

    /**
     * 좋아요 목록 조회
     * @param user
     * @return
     */
    public int queryMyMenu(User user) {
        return menuLikedRepository.countMyMenu(user);
    }

    /**
     * top10 조회
     * @return
     */
    public List<String> countMenuLiked() {
        return menuLikedRepository.countMenuLiked();
    }
}
