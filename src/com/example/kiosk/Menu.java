package com.example.kiosk;

import java.util.ArrayList;

//1. 메뉴 카테고리 저장
//2. MenuItem를 리스트로 보관
//3. 메뉴 출력
//4. 메뉴 항목 접근 (특정 메뉴의 정보 가져오기)
public class Menu {
    String menuCategory;
    ArrayList<MenuItem> items = new ArrayList<>();  //메뉴아이템저장

    public Menu(String menuType){
        this.menuCategory = menuType;
    }

    public void addItem(MenuItem item){
        items.add(item);
    }

    public void showMenu(){
        System.out.println("\n[ " + menuCategory.toUpperCase() + " MENU ]");
        int i=1;
        for (MenuItem item : items){
            System.out.printf("%d. %-13s | %d원 | %s\n", i, item.name, item.price, item.description);
            i++;
        }
        System.out.println("0. 뒤로가기");
    }

    public MenuItem selectMenuItem(int choice){
        if(choice>=1 && choice<=items.size()){
            return items.get(choice);
        }
        return null;
    }

    //메뉴 항목의 개수를 반환
    public int getItemCount(){
        return items.size();
    }

    /*
    //특정 인덱스의 메뉴 항목을 반환
    public MenuItem getItem(int index){
        if(index >=0 && index < items.size()){  //인덱스가 유효한 범위인지 검사
            return items.get(index);
        }
        return null;
    }*/
}
