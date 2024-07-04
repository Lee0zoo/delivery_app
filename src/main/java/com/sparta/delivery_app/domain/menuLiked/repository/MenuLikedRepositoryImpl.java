package com.sparta.delivery_app.domain.menuLiked.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.delivery_app.domain.menuLiked.entity.MenuLiked;
import com.sparta.delivery_app.domain.openApi.dto.MenuListReadResponseDto;
import com.sparta.delivery_app.domain.user.entity.User;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static com.sparta.delivery_app.domain.menu.entity.QMenu.menu;
import static com.sparta.delivery_app.domain.menuLiked.entity.QMenuLiked.menuLiked;
import static com.sparta.delivery_app.domain.user.entity.QUser.user;

@RequiredArgsConstructor
@Repository
public class MenuLikedRepositoryImpl implements MenuLikedRepositoryCustom {

    private final EntityManager em;
    private final JPAQueryFactory queryFactory;

    @Override
    public void saveMenuLiked(MenuLiked liked) {
        em.persist(liked);
    }

    @Override
    public void deleteMenuLiked(MenuLiked liked) {
        em.remove(liked);
    }

    @Override
    public List<Long> existsByUser(User findUser) {
        return queryFactory
                .from(menuLiked)
                .where(user.id.eq(findUser.getId()))
                .select(menu.id)
                .fetch();
    }

    @Override
    public Optional<MenuLiked> findByMenuIdAndUser(Long menuId, User findUser) {
        return Optional.ofNullable(queryFactory
                .selectFrom(menuLiked)
                .where(menu.id.eq(menuId).and(user.id.eq(findUser.getId())))
                .fetchFirst());
    }

    @Override
    public Page<MenuListReadResponseDto> findByUser(User findUser, Pageable pageable) {
        List<MenuListReadResponseDto> list = queryFactory
                .select(Projections.constructor(MenuListReadResponseDto.class, menu))
                .from(menuLiked)
                .where(menuLiked.user.id.eq(findUser.getId()))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        int total = queryFactory
                .selectFrom(menuLiked)
                .where(menuLiked.user.id.eq(findUser.getId()))
                .fetch().size();

        return new PageImpl<>(list, pageable, total);
    }

    @Override
    public int countMyMenu(User user) {
        return queryFactory
                .selectFrom(menuLiked)
                .where(menuLiked.user.id.eq(user.getId()))
                .fetch().size();
    }

    @Override
    public List<String> countMenuLiked() {
        return queryFactory.select(menuLiked.menu.menuName)
                .from(menuLiked)
                .groupBy(menuLiked.menu)
                .orderBy(menuLiked.menu.menuName.count().desc())
                .limit(10)
                .fetch();
    }
}
