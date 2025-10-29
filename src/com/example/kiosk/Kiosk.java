package com.example.kiosk;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class Kiosk {
    ArrayList<Menu> menus = new ArrayList<>();
    ArrayList<MenuItem> cart = new ArrayList<>();
    Scanner sc = new Scanner(System.in);

    private static final int EXIT = 0;
    private static final int CONFIRM = 1;
    private static final int CANCEL = 2;
    // 고정된 메뉴 번호 (Burgers, Drinks, Desserts는 1-3번)
    private static final int ORDERS_MENU = 4;
    private static final int CANCEL_MENU = 5;

    public void addMenu(Menu menu) {
        menus.add(menu);
    }

    public void execute() {
        int maxOption = menus.size();
        while (true) {
            //Main 메뉴 출력
            System.out.println("\n[ MAIN MENU ]");

            //메뉴 카테고리 이름 출력-> 여기까지 menuUI
            for (int i = 0; i < menus.size(); i++) {
                System.out.printf("%d. %s\n", i + 1, menus.get(i).menuCategory);
            }


            //장바구니가 비어있지 않으면 order menu 출력 -> 여기까진 잘 실행됨
            if(!cart.isEmpty()) {
                System.out.println("[ ORDER MENU ]");
                System.out.printf("%d. Orders    | 장바구니를 확인 후 주문합니다.\n", ORDERS_MENU);
                System.out.printf("%d. Cancel    | 진행중인 주문을 취소합니다.\n", CANCEL_MENU);
            }
            System.out.println("---------------------");
            System.out.println("0. 종료");
            System.out.print("번호를 선택하세요 : ");

            try {
                int choice = sc.nextInt();

                if (choice == EXIT) {
                    System.out.println("프로그램을 종료합니다.");
                    break;
                }

                if (!cart.isEmpty()) {
                    maxOption=menus.size();
                    maxOption += 2; // Orders, Cancel 추가
                }

                //예외적인 상황?
                //try-catch에서는 자료형,네트워크,파일 등의 오류만 잡아줌.
                //99처럼 숫자라는 자료형은 맞지만 없는 메뉴 번호 선택시는 잡아주질 못함.
                //그래서 범위를 벗어난 숫자 검증 로직 추가
                if (choice < 1 || choice > maxOption) {
                    System.out.println("없는 번호입니다. 다시 입력해주세요.");
                    continue;  // 다음 반복으로
                }

                //장바구니 메뉴 처리
                if(choice <= menus.size()) {
                    showSubMenu(menus.get(choice - 1));
                } else if(!cart.isEmpty() && choice == ORDERS_MENU) {
                    showCart();
                } else if(choice == CANCEL_MENU) {
                    cancelCart();
                }

            } catch (InputMismatchException e) {
                System.out.println("숫자만 입력해주세요!");
                sc.nextLine();
            }
        }
        sc.close();
    }

    //메뉴 아이템 보여주고 선택하는 메소드
    private void showSubMenu(Menu menu) {
        while (true) {
            menu.showMenu();
            System.out.print("번호를 선택하세요 : ");

            try{
                int subChoice = sc.nextInt();

                //뒤로가기
                if(subChoice==EXIT) break;

                //메뉴 번호 검증
                if(subChoice < 1 || subChoice>menu.getItemCount()){
                    System.out.println("없는 번호입니다. 다시 입력해주세요.");
                    continue;
                }

                //정상 처리
                MenuItem item = menu.selectMenuItem(subChoice);
                System.out.print("\n선택한 메뉴: ");
                item.showMenuItemOnlyMenu();

                System.out.print("수량을 입력해 주세요: ");
                int quantity = sc.nextInt();

                if(quantity<1){
                    System.out.println("수량은 최소 1개부터 입력할 수 있습니다.");
                    continue;
                }

                System.out.println("장바구니에 추가하시겠습니까?");
                System.out.println("1. 확인     2. 취소");
                System.out.print("번호를 입력하세요: ");
                int confirm = sc.nextInt();

                if(confirm==CONFIRM){
                    //장바구니에 같은 메뉴가 있는가?
                    boolean finder = false;
                    for(int i=0; i<cart.size(); i++){
                        MenuItem cartItem = cart.get(i);
                        if(cartItem.id == item.id){ //id로 확인
                            cartItem.setQuantity(cartItem.getQuantity()+quantity);
                            System.out.println(item.name+"의 수량이 변경되었습니다. (총 수량: "+cartItem.getQuantity()+")");
                            finder=true;
                            break;
                        }
                    }

                    if(!finder){
                        item.setQuantity(quantity);
                        cart.add(item);
                        System.out.println(item.name +"  "+ item.quantity+ "개가 장바구니에 추가되었습니다.");
                    }
                }

                if(confirm==CANCEL){
                    break;
                }

            }catch(InputMismatchException e){
                System.out.println("숫자만 입력해주세요.");
                sc.nextLine();
            }
        }
    }

    //장바구니 주문 메소드
    private void showCart(){
        while(true){
            System.out.println("\n[ Orders ]");
            for (MenuItem menuItem : cart) {
                System.out.printf("%s | %d원 | %d개\n",
                        menuItem.name, menuItem.price, menuItem.getQuantity());
            }
            int sum = cart.stream()
                    .mapToInt(item -> item.price * item.getQuantity())
                    .sum();

            System.out.println("Total: " + sum+"원");
            System.out.println("1. 주문     2. 메뉴판");
            System.out.print("번호를 입력하세요: ");

            try{
                int cartChoice = sc.nextInt();

                if(cartChoice==CANCEL) break;

                if(cartChoice ==CONFIRM){
                    discountAndOrder();
                    break;
                }

            }catch(InputMismatchException e){
                System.out.println("숫자만 입력해주세요.");
                sc.nextLine();
            }
        }
    }

    //취소메소드
    private void cancelCart(){
        while(true){
            System.out.println("\n[ Cancel ]");
                System.out.println("주문을 취소 하시겠습니까?");
                System.out.println("1. 부분 취소");
                System.out.println("2. 전체 취소");
                System.out.println("0. 메뉴판");
                System.out.print("번호를 입력하세요: \n");

            try{
                int cancelChoice = sc.nextInt();

                if(cancelChoice==EXIT) break;

                if(cancelChoice == 2){
                    System.out.println("정말 주문을 전체 취소하시겠습니까?");
                    System.out.println("1. 확인      2. 취소");
                    int confirm = sc.nextInt();
                    if(confirm==CONFIRM){
                        cart.clear();
                        System.out.println("주문이 전체 취소되었습니다.");
                        break;
                    }
                }

                if(cancelChoice==1){
                    cancelPartialOrder();
                }

            }catch(InputMismatchException e){
                System.out.println("숫자만 입력해주세요.");
                sc.nextLine();
            }
        }
    }

    //주문 부분 취소
    private void cancelPartialOrder(){
        while(true){
            displayCartList();

            try{
                int choice=sc.nextInt();

                if(choice==EXIT) return;

                if(choice==1||choice>cart.size()){
                    System.out.println("잘못된 번호입니다.");
                    continue;
                }

                cartItemEdit(cart.get(choice -1));

            }catch(InputMismatchException e){
                System.out.println("숫자만 입력해 주세요.");
                sc.nextLine();
            }
        }
    }

    //장바구니 목록 출력
    private void displayCartList(){
        System.out.println("[ Cart List ]");

        for(int i = 0; i < cart.size(); i++) {
            MenuItem item = cart.get(i);
            System.out.printf(
                    "%d. %-13s | %d원 | %s | %d개\n",
                    i + 1,
                    item.name,
                    item.price,
                    item.description,
                    item.getQuantity()
            );
        }

        System.out.println("0. 뒤로가기");
        System.out.println("-------------");
        System.out.print("취소할 메뉴 번호를 선택하세요: \n");
    }

    //아이템 수정/삭제
    private void cartItemEdit(MenuItem selectedItem){
        System.out.println("현재 수량: " + selectedItem.getQuantity() + "개");
        System.out.println("1. 수량 수정");
        System.out.println("2. 메뉴 전체 삭제");
        System.out.println("0. 뒤로가기");
        System.out.print("번호를 선택하세요: ");

        try{
            int editChoice = sc.nextInt();
            if(editChoice==EXIT) return;

            if(editChoice == 1){
                //수량수정
                System.out.println("변경할 수량을 입력하세요: ");
                int newQuantity = sc.nextInt();

                if(newQuantity>0) {
                    selectedItem.setQuantity(newQuantity);
                    System.out.println("수량이 " + newQuantity + "개로 변경되었습니다.");
                } else if(newQuantity==0) {
                    System.out.println("수량을 0으로 설정하면 삭제됩니다. 삭제하시겠습니까?");
                    System.out.println("1. 확인     2. 취소");
                    if(sc.nextInt()==CONFIRM) {
                        removeFromCart(selectedItem.name);
                    }
                } else {
                    System.out.println("잘못된 수량입니다.");
                }
            }
            else if(editChoice == 2) {
                // 메뉴 삭제
                System.out.println("정말 삭제하시겠습니까?");
                System.out.println("1. 확인     2. 취소");
                if(sc.nextInt()==CONFIRM) {
                    removeFromCart(selectedItem.name);
                }
            }
        }catch (InputMismatchException e){
            System.out.println("숫자만 입력해 주세요.");
            sc.nextLine();
        }
    }

    //부분취소연산되는 메소드
    private void removeFromCart(String menuName){
        cart.removeIf(item -> item.name.equals(menuName));
        System.out.println(menuName+"이(가) 장바구니에서 제거되었습니다.");
    }

    //할인 메소드
    private void discountAndOrder(){
        Discount[] discount = Discount.values();
        while(true){
            System.out.println("할인 정보를 입력해주세요.");

            int sum = cart.stream()
                    .mapToInt(item -> item.price * item.getQuantity())
                    .sum();

            for(int i=0; i<discount.length; i++){
                System.out.println((i+1)+ ". "+discount[i].getDiscountName());
            }
            System.out.print("번호를 입력해 주세요: ");

            try{
                Discount selectDiscount = discount[sc.nextInt()-1];

                int discountAmount=selectDiscount.getDiscount(sum);
                int finalPrice = selectDiscount.getFinalPrice(sum);

                System.out.println("할인된 금액: " + discountAmount + "원");
                System.out.println("최종 금액: " + finalPrice + "원");
                cart.clear();
                break;

            }catch(InputMismatchException e){
                System.out.println("번호를 다시 입력해주세요.");
            }
        }
    }
}

