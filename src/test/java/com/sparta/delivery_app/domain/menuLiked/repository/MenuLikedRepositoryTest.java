package com.sparta.delivery_app.domain.menuLiked.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.delivery_app.TestConfig;
import com.sparta.delivery_app.domain.menu.entity.Menu;
import com.sparta.delivery_app.domain.menu.entity.MenuStatus;
import com.sparta.delivery_app.domain.menu.repository.MenuRepository;
import com.sparta.delivery_app.domain.menuLiked.entity.MenuLiked;
import com.sparta.delivery_app.domain.store.entity.Store;
import com.sparta.delivery_app.domain.store.entity.StoreStatus;
import com.sparta.delivery_app.domain.store.repository.StoreRepository;
import com.sparta.delivery_app.domain.user.entity.User;
import com.sparta.delivery_app.domain.user.entity.UserRole;
import com.sparta.delivery_app.domain.user.entity.UserStatus;
import com.sparta.delivery_app.domain.user.repository.UserRepository;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.sparta.delivery_app.domain.menu.entity.QMenu.menu;
import static com.sparta.delivery_app.domain.menuLiked.entity.QMenuLiked.menuLiked;
import static com.sparta.delivery_app.domain.user.entity.QUser.user;

@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(TestConfig.class)
class MenuLikedRepositoryTest {

    @Autowired
    EntityManager em;

    @Autowired
    private MenuLikedRepository menuLikedRepository;

    @Autowired
    private MenuRepository menuRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private StoreRepository storeRepository;


    private Menu setMenu;

    private User setUser;

    private MenuLiked menuLiked1;



    private void setUp() {
        System.out.println("test");
        setUser = User.builder()
                .email("email@email.com")
                .name("name")
                .nickName("nickName")
                .userAddress("address")
                .userStatus(UserStatus.ENABLE)
                .userRole(UserRole.CONSUMER).build();

        User manager = User.builder()
                .email("manaber@email.com")
                .name("manager")
                .nickName("abxd")
                .userAddress("aglskentlksdgdfe")
                .userStatus(UserStatus.ENABLE)
                .userRole(UserRole.MANAGER).build();

        userRepository.save(manager);

        Store store = Store.builder()
                .user(manager)
                .storeName("storeName")
                .storeAddress("storeAddress")
                .storeInfo("storeInfo")
                .storeRegistrationNumber("666-45-67890")
                .minTotalPrice(10000L)
                .status(StoreStatus.ENABLE).build();

        storeRepository.save(store);

        setMenu = Menu.builder()
                .menuName("name")
                .menuPrice(10000L)
                .menuInfo("info")
                .menuImagePath("abcd")
                .store(store)
                .menuStatus(MenuStatus.ENABLE)
                .build();

        menuRepository.save(setMenu);
        userRepository.save(setUser);

        menuLiked1 = new MenuLiked(setMenu, setUser);
        menuLikedRepository.saveMenuLiked(menuLiked1);
    }

    @Test
    @DisplayName("메뉴 좋아요 등록 테스트")
    @Transactional
    public void test1() {
        MenuLiked menuLiked = new MenuLiked(setMenu, setUser);
        menuLikedRepository.saveMenuLiked(menuLiked);
    }

    @Test
    @DisplayName("메뉴로 좋아요 top10 조회")
    public void test2() {
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);

        queryFactory.select(menuLiked.menu.menuName)
                .from(menuLiked)
                .groupBy(menuLiked.menu)
                .orderBy(menuLiked.menu.menuName.count().desc())
                .limit(10)
                .fetch();
    }

    @Test
    @DisplayName("메뉴, 사용자 정보로 DB에서 좋아요 찾기")
    public void test3() {
        this.setUp();
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);

        queryFactory
                .selectFrom(menuLiked)
                .where(menu.id.eq(setMenu.getId()).and(user.id.eq(setUser.getId())))
                .fetchFirst();
    }

    @Test
    @DisplayName("좋아요 삭제")
    public void test4() {
        this.setUp();
        em.remove(menuLiked1);
    }

    @Test
    @DisplayName("사용자로 찾기")
    public void test5() {
        this.setUp();
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);

        queryFactory
                .from(menuLiked)
                .where(user.id.eq(setUser.getId()))
                .select(menu.id)
                .fetch();
    }
}