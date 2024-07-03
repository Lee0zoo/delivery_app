package com.sparta.delivery_app.domain.menuLiked.repository;

import com.sparta.delivery_app.domain.menuLiked.entity.MenuLiked;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuLikedRepository extends JpaRepository<MenuLiked, Long>, MenuLikedRepositoryCustom {
}
