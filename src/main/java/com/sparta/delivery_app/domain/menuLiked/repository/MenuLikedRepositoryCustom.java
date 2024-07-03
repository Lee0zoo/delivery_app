package com.sparta.delivery_app.domain.menuLiked.repository;

import com.sparta.delivery_app.domain.menu.entity.Menu;
import com.sparta.delivery_app.domain.menuLiked.entity.MenuLiked;
import com.sparta.delivery_app.domain.openApi.dto.MenuListReadResponseDto;
import com.sparta.delivery_app.domain.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;


public interface MenuLikedRepositoryCustom {

    void saveMenuLiked(MenuLiked liked);

    void deleteMenuLiked(MenuLiked liked);

    boolean existsByMenu(Menu findMenu);

    boolean existsByUser(User findUser);

    Optional<MenuLiked> findByMenuIdAndUser(Long menuId, User user);

    Page<MenuListReadResponseDto> findByUser(User findUser, Pageable pageable);

    int countMyMenu(User user);

    List<String> countMenuLiked();
}