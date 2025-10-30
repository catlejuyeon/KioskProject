package com.example.kiosk;

//메뉴 속 개별 메뉴
//치즈버거, 불고기버거, 콜라, 사이다, 바닐라아이스크림, 초코아이스크림...
public class MenuItem implements Product{
    protected int id;
    protected String name;
    protected int price;
    protected String description;
    protected int quantity;

    public MenuItem(int id, String name, int price, String description){
        this.id=id;
        this.name = name;
        this.price = price;
        this.description = description;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override public String getDescription() {
        return description;
    }

    @Override
    public int getPrice() {
        return price;
    }

    @Override
    public void display() {
        System.out.printf("%-13s | %d원 | %s\n", name, price, description);
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
