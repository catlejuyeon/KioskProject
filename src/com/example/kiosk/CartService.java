package com.example.kiosk;

import java.util.InputMismatchException;

/*
    1. 장바구니 주문, 수정, 취소등 관련 서비스 로직
 */
public class CartService {
    private final CartManager cartManager;
    private final InputManager inputManager;

    private static final int EXIT = 0;
    private static final int CONFIRM = 1;
    private static final int CANCEL = 2;

    public CartService(CartManager cartManager, InputManager inputManager){
        this.cartManager = cartManager;
        this.inputManager = inputManager;
    }

    //--------주문관련---------

    //장바구니 확인 및 주문
    public void showCartAndOrder(){
        while(true){
            System.out.println("\n[ Orders ]");
            cartManager.displayCart();

            int total = cartManager.getTotalPrice();
            System.out.println("Total: " + total + "원");

            System.out.println("1. 주문     2. 메뉴판");
            System.out.print("번호를 입력하세요: ");
            Integer choice = inputManager.getIntegerTryCatch();
            if(choice == CANCEL)break;
            if(choice == CONFIRM){
                discountAndOrder();
                break;
            }
        }
    }

    //할인 적용 & 주문 완료
    private void discountAndOrder(){
        Discount[] discounts = Discount.values();

        while(true){
            System.out.println("할인 정보를 입력해 주세요.");

            int total = cartManager.getTotalPrice();

            for(int i=0; i<discounts.length; i++){
                System.out.println((i + 1) + ". " + discounts[i].getDiscountName());
            }
            System.out.print("번호를 입력해 주세요: ");

            Integer choice = inputManager.getIntegerTryCatch();
            if(choice<1 || choice>discounts.length){
                System.out.println("잘못된 번호입니다.");
                continue;
            }
            Discount selectedDiscount = discounts[choice - 1];

            int discountAmount=selectedDiscount.getDiscount(total);
            int finalPrice = selectedDiscount.getFinalPrice(total);

            System.out.println("할인된 금액: " + discountAmount + "원");
            System.out.println("최종 금액: " + finalPrice + "원");

            cartManager.clear();
            break;
        }
    }

    //----------취소---------

    //취소 메뉴
    public void showCancel(){
        while(true){
            System.out.println("\n[ Cancel ]");
            System.out.println("주문을 취소 하시겠습니까?");
            System.out.println("1. 부분 취소");
            System.out.println("2. 전체 취소");
            System.out.println("0. 메뉴판");
            System.out.print("번호를 입력하세요: ");

            Integer cancelChoice = inputManager.getIntegerTryCatch();
            if(cancelChoice==EXIT) break;

            if(cancelChoice == 2){
                cancelAllItems();
                break;
            }

            if(cancelChoice==1){
                cancelPartialOrder();
            }
        }
    }

    //전체 취소
    private void cancelAllItems(){
        if(inputManager.getConfirm("정말 주문을 전체 취소하시겠습니까?")){
            cartManager.clear();
            System.out.println("주문이 전체 취소되었습니다.");
        }
    }

    //부분취소
    private void cancelPartialOrder(){
        while (true){
            cartManager.displayDetailedCart();
            System.out.println("0. 뒤로가기");
            System.out.println("-------------");
            System.out.print("취소할 메뉴 번호를 선택하세요: ");

            Integer choice = inputManager.getIntegerTryCatch();

            if (choice<1 || choice> cartManager.size()){
                System.out.println("잘못된 번호입니다.");
            }

        }
    }

    //아이템 수정 &  삭제
    private void editCartItem(MenuItem selectedItem){
        System.out.println("현재 수량: " + selectedItem.getQuantity() + "개");
        System.out.println("1. 수량 수정");
        System.out.println("2. 메뉴 전체 삭제");
        System.out.println("0. 뒤로가기");
        System.out.print("번호를 선택하세요: ");

        Integer editChoice = inputManager.getIntegerTryCatch();

        if(editChoice==EXIT) return;

        if(editChoice==1){
            changeQuantity(selectedItem);
        }else if(editChoice==2){
            deleteItem(selectedItem);
        }


    }

    //수량 변경
    private void changeQuantity(MenuItem selectedItem){
        System.out.print("변경할 수량을 입력하세요: ");
        int newQuantity = inputManager.getIntInput();

        if(newQuantity>0){
            selectedItem.setQuantity(newQuantity);
            System.out.println("수량이 "+ newQuantity + "개로 변경되었습니다.");
        }else if(newQuantity==0){
            if(inputManager.getConfirm("수량을 0으로하면 삭제됩니다. 삭제하시겠습니까?")){
            cartManager.removeItem(selectedItem.id);}
        }else{
            System.out.println("잘못된 수량입니다.");
        }
    }

    //아이템 삭제
    private void deleteItem(MenuItem selectedItem){
        if(inputManager.getConfirm("정말 삭제하시겠습니까?")){
            cartManager.removeItem(selectedItem.id);
        }
    }
}
