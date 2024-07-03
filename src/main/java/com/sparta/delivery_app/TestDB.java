package com.sparta.delivery_app;

import com.sparta.delivery_app.domain.liked.entity.Liked;
import com.sparta.delivery_app.domain.menu.entity.Menu;
import com.sparta.delivery_app.domain.menu.entity.MenuStatus;
import com.sparta.delivery_app.domain.menuLiked.entity.MenuLiked;
import com.sparta.delivery_app.domain.order.entity.Order;
import com.sparta.delivery_app.domain.order.entity.OrderItem;
import com.sparta.delivery_app.domain.order.entity.OrderStatus;
import com.sparta.delivery_app.domain.review.entity.ManagerReviews;
import com.sparta.delivery_app.domain.review.entity.ManagerReviewsStatus;
import com.sparta.delivery_app.domain.review.entity.ReviewStatus;
import com.sparta.delivery_app.domain.review.entity.UserReviews;
import com.sparta.delivery_app.domain.store.entity.Store;
import com.sparta.delivery_app.domain.store.entity.StoreStatus;
import com.sparta.delivery_app.domain.user.entity.PasswordHistory;
import com.sparta.delivery_app.domain.user.entity.User;
import com.sparta.delivery_app.domain.user.entity.UserRole;
import com.sparta.delivery_app.domain.user.entity.UserStatus;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class TestDB {

    private final InitService initService;


    @PostConstruct
    public void init() {
        initService.dbInit1();
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService {
        private final EntityManager em;

        @Autowired
        PasswordEncoder passwordEncoder;

        public void dbInit1() {
            /**
             * 회원
             */
            User consumer = User.builder()
                    .email("b2@gmail.com")
                    .userAddress("고객님 여기는 어디야")
                    .nickName("b2consumernickname")
                    .name("고객님-스파르타")
                    .userRole(UserRole.CONSUMER)
                    .userStatus(UserStatus.ENABLE)
                    .build();
            save(consumer);

            PasswordHistory ph1 = PasswordHistory.builder()
                    .user(consumer)
                    .password(passwordEncoder.encode("Passw0rd!"))
                    .build();
            save(ph1);

            PasswordHistory ph2 = PasswordHistory.builder()
                    .user(consumer)
                    .password(passwordEncoder.encode("Passw0rd!"))
                    .build();
            save(ph2);
            /**
             * 판매자
             */
            User manager1 = User.builder()
                    .email("b2manager1@gmail.com")
                    .userAddress("사장님1 여기는 어디야")
                    .nickName("b2manager1nickname")
                    .name("사장님1-스파르타")
                    .userRole(UserRole.MANAGER)
                    .userStatus(UserStatus.ENABLE)
                    .build();
            save(manager1);

            PasswordHistory manager1Ph1 = PasswordHistory.builder()
                    .user(manager1)
                    .password(passwordEncoder.encode("Passw0rd!!"))
                    .build();
            save(manager1Ph1);

            User manager2 = User.builder()
                    .email("b2manager2@gmail.com")
                    .userAddress("사장님2 여기는 어디야")
                    .nickName("b2manager2nickname")
                    .name("사장님2-스파르타")
                    .userRole(UserRole.MANAGER)
                    .userStatus(UserStatus.ENABLE)
                    .build();
            save(manager2);

            PasswordHistory manager2Ph2 = PasswordHistory.builder()
                    .user(manager2)
                    .password(passwordEncoder.encode("Passw0rd!!"))
                    .build();
            save(manager2Ph2);

            User manager3 = User.builder()
                    .email("b2manager3@gmail.com")
                    .userAddress("사장님3 여기는 어디야")
                    .nickName("b2managern3ickname")
                    .name("사장님3-스파르타")
                    .userRole(UserRole.MANAGER)
                    .userStatus(UserStatus.ENABLE)
                    .build();
            save(manager3);

            PasswordHistory manager3Ph3 = PasswordHistory.builder()
                    .user(manager3)
                    .password(passwordEncoder.encode("Passw0rd!!"))
                    .build();
            save(manager3Ph3);

            User manager4 = User.builder()
                    .email("b2manager4@gmail.com")
                    .userAddress("사장님4 여기는 어디야")
                    .nickName("b2manager4nickname")
                    .name("사장님4-스파르타")
                    .userRole(UserRole.MANAGER)
                    .userStatus(UserStatus.ENABLE)
                    .build();
            save(manager4);

            PasswordHistory manager4Ph4 = PasswordHistory.builder()
                    .user(manager4)
                    .password(passwordEncoder.encode("Passw0rd!!"))
                    .build();
            save(manager4Ph4);

            User manager5 = User.builder()
                    .email("b2manager5@gmail.com")
                    .userAddress("사장님5 여기는 어디야")
                    .nickName("b2manage5rnickname")
                    .name("사장님5-스파르타")
                    .userRole(UserRole.MANAGER)
                    .userStatus(UserStatus.ENABLE)
                    .build();
            save(manager5);

            PasswordHistory manager5Ph5 = PasswordHistory.builder()
                    .user(manager5)
                    .password(passwordEncoder.encode("Passw0rd!!"))
                    .build();
            save(manager5Ph5);

            User manager6 = User.builder()
                    .email("b2manager6@gmail.com")
                    .userAddress("사장님6 여기는 어디야")
                    .nickName("b2manager6nickname")
                    .name("사장님6-스파르타")
                    .userRole(UserRole.MANAGER)
                    .userStatus(UserStatus.ENABLE)
                    .build();
            save(manager6);

            PasswordHistory manager6ph6 = PasswordHistory.builder()
                    .user(manager6)
                    .password(passwordEncoder.encode("Passw0rd!!"))
                    .build();
            save(manager6ph6);

            User manager7 = User.builder()
                    .email("b2manager7@gmail.com")
                    .userAddress("사장님7 여기는 어디야")
                    .nickName("b2manager7nickname")
                    .name("사장님7-스파르타")
                    .userRole(UserRole.MANAGER)
                    .userStatus(UserStatus.ENABLE)
                    .build();
            save(manager7);

            PasswordHistory manager7ph7 = PasswordHistory.builder()
                    .user(manager7)
                    .password(passwordEncoder.encode("Passw0rd!!"))
                    .build();
            save(manager7ph7);

            User admin = User.builder()
                    .email("admin@gmail.com")
                    .userAddress("서울시")
                    .nickName("어드민닉네임")
                    .name("관리자")
                    .userRole(UserRole.ADMIN)
                    .userStatus(UserStatus.ENABLE)
                    .build();

            save(admin);
            PasswordHistory ph3 = PasswordHistory.builder()
                    .user(admin)
                    .password(passwordEncoder.encode("adminpassword!"))
                    .build();
            save(ph3);

            /**
             * 매장
             */
            Store store1 = Store.builder()
                    .user(manager1)
                    .storeName("이태리식 레스토랑")
                    .storeAddress("서울시 강남구")
                    .storeRegistrationNumber("111-45-67890")
                    .minTotalPrice(5000L)
                    .storeInfo("정통 이태리 음식점입니다.")
                    .status(StoreStatus.ENABLE)
                    .build();
            save(store1);

            Store store2 = Store.builder()
                    .user(manager2)
                    .storeName("중국집")
                    .storeAddress("서울시 중구")
                    .storeRegistrationNumber("222-56-78901")
                    .minTotalPrice(15_000L)
                    .storeInfo("퓨전 중식집")
                    .status(StoreStatus.ENABLE)
                    .build();
            save(store2);

            Store store3 = Store.builder()
                    .user(manager3)
                    .storeName("음료수")
                    .storeAddress("서울시 광진구")
                    .storeRegistrationNumber("333-67-89012")
                    .minTotalPrice(11_000L)
                    .storeInfo("세상 모든 음료")
                    .status(StoreStatus.ENABLE)
                    .build();
            save(store3);

            Store store4 = Store.builder()
                    .user(manager4)
                    .storeName("치킨을 튀긴다")
                    .storeAddress("서울시 서초구")
                    .storeRegistrationNumber("444-78-90123")
                    .minTotalPrice(20_000L)
                    .storeInfo("찹쌀반죽을 입혀 더 바삭합니다!")
                    .status(StoreStatus.ENABLE)
                    .build();
            save(store4);

            Store store5 = Store.builder()
                    .user(manager5)
                    .storeName("떡볶이")
                    .storeAddress("서울시 강남구")
                    .storeRegistrationNumber("555-45-67890")
                    .minTotalPrice(15_000L)
                    .storeInfo("쌀 떡만 사용합니다")
                    .status(StoreStatus.ENABLE)
                    .build();
            save(store5);

            Store store6 = Store.builder()
                    .user(manager6)
                    .storeName("초밥")
                    .storeAddress("서울시 관악구")
                    .storeRegistrationNumber("666-45-67890")
                    .minTotalPrice(27_000L)
                    .storeInfo("장인 정신")
                    .status(StoreStatus.ENABLE)
                    .build();
            save(store6);

            Store store7 = Store.builder()
                    .user(manager7)
                    .storeName("라면")
                    .storeAddress("서울시 서초구")
                    .storeRegistrationNumber("777-45-67890")
                    .minTotalPrice(8_000L)
                    .storeInfo("가성비 최고")
                    .status(StoreStatus.DISABLE)
                    .build();
            save(store7);

            /**
             * 메뉴
             */
            Menu menu = Menu.builder()
                    .store(store1)
                    .menuName("1번 매장 음식")
                    .menuPrice(10_000L)
                    .menuInfo("맛있다!")
                    .menuImagePath("image1/abc")
                    .menuStatus(MenuStatus.ENABLE)
                    .build();
            save(menu);

            Menu menu1 = Menu.builder()
                    .store(store1)
                    .menuName("1번 매장 음식")
                    .menuPrice(10_000L)
                    .menuInfo("맛있다!")
                    .menuImagePath("image1/abc")
                    .menuStatus(MenuStatus.ENABLE)
                    .build();
            save(menu1);

            for (int i = 1; i <= 2; i++) {
                Menu menu2 = Menu.builder()
                        .store(store2)
                        .menuName("2번 매장 음식" + i)
                        .menuPrice(12_000L)
                        .menuInfo("맛있다!")
                        .menuImagePath("image2/abc")
                        .menuStatus(MenuStatus.ENABLE)
                        .build();
                save(menu2);
            }

            for (int i = 1; i <= 3; i++) {
                Menu menu3 = Menu.builder()
                        .store(store3)
                        .menuName("3번 매장 음식" + i)
                        .menuPrice(13_000L)
                        .menuInfo("맛있다!")
                        .menuImagePath("image3/abc")
                        .menuStatus(MenuStatus.ENABLE)
                        .build();
                save(menu3);
            }

            for (int i = 1; i <= 2; i++) {
                Menu menu4 = Menu.builder()
                        .store(store4)
                        .menuName("4번 매장 음식" + i)
                        .menuPrice(14_000L)
                        .menuInfo("맛있다!")
                        .menuImagePath("image4/abc")
                        .menuStatus(MenuStatus.ENABLE)
                        .build();
                save(menu4);
            }

            for (int i = 1; i <= 3; i++) {
                Menu menu5 = Menu.builder()
                        .store(store5)
                        .menuName("5번 매장 음식" + i)
                        .menuPrice(15_000L)
                        .menuInfo("맛있다!")
                        .menuImagePath("image5/abc")
                        .menuStatus(MenuStatus.ENABLE)
                        .build();
                save(menu5);
            }

            //주문
            Order order1 = Order.builder()
                    .user(consumer)
                    .store(store1)
                    .orderStatus(OrderStatus.DELIVERY_COMPLETED)
                    .build();
            save(order1);

            OrderItem orderItem1 = OrderItem.builder()
                    .order(order1)
                    .menu(menu)
                    .quantity(2)
                    .build();

            OrderItem orderItem2 = OrderItem.builder()
                    .order(order1)
                    .menu(menu1)
                    .quantity(2)
                    .build();

            order1.addOrderItem(orderItem1);
            order1.addOrderItem(orderItem2);

            Order order2 = Order.builder()
                    .user(consumer)
                    .store(store1)
                    .orderStatus(OrderStatus.DELIVERY_COMPLETED)
                    .build();
            save(order2);

            OrderItem orderItem3 = OrderItem.builder()
                    .order(order2)
                    .menu(menu)
                    .quantity(5)
                    .build();

            OrderItem orderItem4 = OrderItem.builder()
                    .order(order2)
                    .menu(menu1)
                    .quantity(1)
                    .build();

            order2.addOrderItem(orderItem3);
            order2.addOrderItem(orderItem4);


            /**
             * 리뷰
             */
            UserReviews userReviews1 = UserReviews.builder()
                    .content("맛집입니다!! 강추")
                    .reviewImagePath("")
                    .rating(5)
                    .order(order1)
                    .user(consumer)
                    .reviewStatus(ReviewStatus.ENABLE)
                    .build();
            save(userReviews1);

            UserReviews userReviews2 = UserReviews.builder()
                    .content("최악입니다!! 절대가지마")
                    .reviewImagePath("")
                    .rating(1)
                    .order(order2)
                    .user(consumer)
                    .reviewStatus(ReviewStatus.DISABLE)
                    .build();
            save(userReviews2);

            ManagerReviews managerReviews1 = ManagerReviews.builder()
                    .content("감사합니다~!")
                    .userReviews(userReviews1)
                    .user(manager1)
                    .managerReviewsStatus(ManagerReviewsStatus.ENABLE)
                    .build();
            save(managerReviews1);

//            ManagerReviews managerReviews2 = ManagerReviews.builder()
//                    .content("진상고객 우~~~")
//                    .reviewsId(userReviews2.getId())
//                    .user(manager1)
//                    .managerReviewsStatus(ManagerReviewsStatus.ENABLE)
//                    .build();
//            save(managerReviews2);

            /**
             * 좋아요
             */
            Liked liked1 = Liked.builder()
                    .user(consumer)
                    .store(store1)
                    .build();
            save(liked1);

            Liked liked2 = Liked.builder()
                    .user(consumer)
                    .store(store1)
                    .build();
            save(liked2);

            MenuLiked liked3 = MenuLiked.builder()
                    .user(consumer)
                    .menu(menu1)
                    .build();
            save(liked3);

            /**
             *
             * 추가 데이터 내용
             */
        }


        public void save(Object... objects) {
            for (Object object : objects) {
                em.persist(object);
            }
        }
    }
}

