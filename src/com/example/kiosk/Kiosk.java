package com.example.kiosk;

import java.util.*;

public class Kiosk {
    private final ArrayList<Menu> menus = new ArrayList<>();
    private final Scanner sc = new Scanner(System.in);

    private final CartManager cartManager;
    private final InputManager inputManager;
    private final CartService cartService;

    private static final int EXIT = 0;
    // 고정된 메뉴 번호 (Burgers, Drinks, Desserts는 1-3번)
    private static final int ORDERS_MENU = 4;
    private static final int CANCEL_MENU = 5;

    public Kiosk() {
        this.cartManager = new CartManager();
        this.inputManager = new InputManager(sc);
        this.cartService = new CartService(cartManager, inputManager);
    }

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
            if(!cartManager.isEmpty()) {
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

                if (!cartManager.isEmpty()) {
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
                } else if(!cartManager.isEmpty() && choice == ORDERS_MENU) {
                    cartService.showCartAndOrder();
                } else if(choice == CANCEL_MENU) {
                    cartService.showCancel();
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
                item.display();

                int quantity = inputManager.getValidQuantity();

                if(inputManager.getConfirm("장바구니에 추가하시겠습니까?")){
                    cartManager.addItem(item,quantity);
                }else break;

            }catch(InputMismatchException e){
                System.out.println("숫자만 입력해주세요.");
                sc.nextLine();
            }
        }
    }
}

