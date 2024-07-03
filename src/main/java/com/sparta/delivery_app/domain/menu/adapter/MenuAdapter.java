package com.sparta.delivery_app.domain.menu.adapter;

import com.sparta.delivery_app.common.exception.errorcode.MenuErrorCode;
import com.sparta.delivery_app.common.globalcustomexception.MenuNotFoundException;
import com.sparta.delivery_app.domain.menu.entity.Menu;
import com.sparta.delivery_app.domain.menu.entity.MenuStatus;
import com.sparta.delivery_app.domain.menu.repository.MenuRepository;
import com.sparta.delivery_app.domain.store.entity.Store;
import com.sparta.delivery_app.domain.store.entity.StoreStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MenuAdapter {

    private final MenuRepository menuRepository;

    /**
     * 메뉴 등록
     * @param menu
     */
    public void saveMenu(Menu menu) {
        menuRepository.save(menu);
    }

    /**
     * 메뉴 id, 상태 검증
     * @param menuId
     * @return menu
     */
    public Menu queryMenuByIdAndMenuStatus(Long menuId) {
        Menu menu = findById(menuId);
        MenuStatus.checkMenuStatus(menu);
        return menu;
    }

    /**
     * 메뉴 id 검증
     * @param menuId
     * @return menu
     */
    public Menu queryMenuById(Long menuId) {
        return findById(menuId);
    }

    /**
     * 메뉴 id 검증
     * @param menuId
     * @return menu
     */
    private Menu findById(Long menuId) {
        return menuRepository.findById(menuId).orElseThrow(() ->
                new MenuNotFoundException(MenuErrorCode.MENU_NOT_FOUND));
    }

    /**
     * 특정 매장의 메뉴 조회
     * @param storeId
     * @param pageable
     * @return
     */
    public Page<Menu> queryMenuListByStoreId(Long storeId, Pageable pageable) {
        return menuRepository.findAllMenuByStoreId(storeId, pageable);
    }

    public void deleteTempMenu(Menu menu) {
        menuRepository.delete(menu);
    }

    public Page<Menu> queryMenu(Pageable pageable) {
        return menuRepository.findAllByMenuStatus(pageable, StoreStatus.ENABLE);
    }

}
