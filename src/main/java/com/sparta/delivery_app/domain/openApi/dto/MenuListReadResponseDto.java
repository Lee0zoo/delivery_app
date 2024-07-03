package com.sparta.delivery_app.domain.openApi.dto;


import com.querydsl.core.annotations.QueryProjection;
import com.sparta.delivery_app.domain.menu.entity.Menu;
import lombok.Getter;

@Getter
public class MenuListReadResponseDto {
    private String menuName;
    private Long menuPrice;
    private String menuInfo;
    private String menuImagePath;

    public MenuListReadResponseDto(Menu menu) {
        this.menuName = menu.getMenuName();
        this.menuPrice = menu.getMenuPrice();
        this.menuInfo = menu.getMenuInfo();
        this.menuImagePath = menu.getMenuImagePath();
    }
}