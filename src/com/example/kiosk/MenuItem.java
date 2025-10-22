package com.example.kiosk;

//메뉴 속 개별 메뉴
//치즈버거, 불고기버거, 콜라, 사이다, 바닐라아이스크림, 초코아이스크림...
public class MenuItem {
    String name;
    int price;
    String description;

    public MenuItem(String name, int price, String description){
        this.name = name;
        this.price = price;
        this.description = description;
    }

    public void showMenuItem(){
        System.out.printf("%s | %d원 | %s\n", name, price, description);
    }
}
