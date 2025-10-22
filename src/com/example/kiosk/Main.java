package com.example.kiosk;

public class Main {
    public static void main(String[] args) {
        //버거 메뉴
        MenuItem shackBurger = new MenuItem("ShackeBurger", 6900, "토마토, 양상추, 쉑소스가 토핑된 치즈버거");
        MenuItem smokeShack = new MenuItem("SmokeShack", 8900, "베이컨, 체리 페퍼에 쉑소스가 토핑된 치즈버거");
        MenuItem cheeseBurger = new MenuItem("Cheeseburger",6900,"포테이토 번과 비프패티, 치즈가 토핑된 치즈버거");
        MenuItem hamBurger = new MenuItem("Hamburger", 5400, "비프패티를 기반으로 야채가 들어간 기본버거");

        Menu burgerMenu = new Menu("Burgers");
        burgerMenu.addItem(shackBurger);
        burgerMenu.addItem(smokeShack);
        burgerMenu.addItem(cheeseBurger);
        burgerMenu.addItem(hamBurger);

        //음료 메뉴
        MenuItem cokeZero = new MenuItem("CokeZero", 3000, "설탕이 들어가 있지 않은 콜라");
        MenuItem americano = new MenuItem("Americano", 4500, "정신 차려!하고 싶을 때~");

        Menu drinkMenu = new Menu("Drinks");
        drinkMenu.addItem(cokeZero);
        drinkMenu.addItem(americano);

        //디저트 메뉴
        MenuItem vanillaIceCream = new MenuItem("VanillaIceCream", 3000, "바닐라 향이 나는 소프트아이스크림");
        MenuItem waffle = new MenuItem("Waffle", 4000, "달콤 바삭 와플, 아이스크림이랑 먹으면 최고");

        Menu dessersMenu = new Menu("Desserts");
        dessersMenu.addItem(vanillaIceCream);
        dessersMenu.addItem(waffle);

        //키오스크
        Kiosk kiosk = new Kiosk();
        kiosk.addMenu(burgerMenu);
        kiosk.addMenu(drinkMenu);
        kiosk.addMenu(dessersMenu);

        kiosk.execute();

    }
}
