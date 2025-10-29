package com.example.kiosk;

import java.util.ArrayList;

/*
    1. 장바구니 관련 클래스
    2. 장바구니 추가, 수정, 삭제, 조회 기능 담당
 */
public class CartManager {
    private final ArrayList<MenuItem> cart;

    public CartManager() {
        this.cart = new ArrayList<>();
    }

    //장바구니 추가 + 중복 체크
    public void addItem(MenuItem item, int quantity) {
        MenuItem existingItem = finder(item.id);

        if(existingItem!= null) {
            existingItem.setQuantity(existingItem.getQuantity() + quantity);
            System.out.println(item.name + "의 수량이 변경되었습니다. (총 수량: "+existingItem.getQuantity()+")");
        }else {
            item.setQuantity(quantity);
            cart.add(item);
            System.out.println(item.name+"  "+quantity+"개가 장바구니에 추가되었습니다.");
        }
    }

    //id로 아이템 찾기
    private MenuItem finder(int id) {
        return cart.stream()
                .filter(item -> item.id==id)
                .findFirst()
                .orElse(null);
    }

    //id로 아이템 삭제
    public void removeItem(int id){
        MenuItem itemRemove = finder(id);
        if(itemRemove != null) {
            cart.removeIf(item -> item.id==id);
            System.out.println(itemRemove.name + "가(이) 장바구니에서 제거되었습니다.");
        }
    }

    //장바구니 전체 비우기
    public void clear(){
        cart.clear();
    }

    //장바구니 총액 계산
    public int getTotalPrice(){
        return cart.stream()
                .mapToInt(item -> item.price*item.getQuantity())
                .sum();
    }

    //장바구니 내용 출력
    public void displayCart(){
        for(MenuItem item : cart){
            System.out.printf("%s | %d원 | %d개\n",
                    item.name, item.price, item.getQuantity());
        }
    }

    // 장바구니 내용 출력(상세)
    public void displayDetailedCart() {
        System.out.println("\n[ Cart List ]");
        for (int i=0; i < cart.size(); i++) {
            MenuItem item = cart.get(i);
            System.out.printf("%d. %-13s | %d원 | %s | %d개\n",
                    i + 1, item.name, item.price, item.description, item.getQuantity());
        }
    }

    //장바구니가 비었나?
    public boolean isEmpty(){
        return cart.isEmpty();
    }

    //장바구니 사이즈
    public int size(){
        return cart.size();
    }

    //인덱스로 아이템 가져오기
    public MenuItem getItem(int index){
        return cart.get(index);
    }
}
