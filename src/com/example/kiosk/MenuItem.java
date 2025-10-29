package com.example.kiosk;

//메뉴 속 개별 메뉴
//치즈버거, 불고기버거, 콜라, 사이다, 바닐라아이스크림, 초코아이스크림...
public class MenuItem {
    String name;
    int price;
    String description;
    int id;
    int quantity;

    public MenuItem(int id, String name, int price, String description){
        this.id=id;
        this.name = name;
        this.price = price;
        this.description = description;
    }

    public void showMenuItemOnlyMenu(){
        System.out.printf("%s | %d원 | %s\n", name, price, description);
    }

    //세터
    public void setQuantity(int quantity){
        this.quantity=quantity;
    }

    public int getQuantity(){
        return quantity;
    }
}
