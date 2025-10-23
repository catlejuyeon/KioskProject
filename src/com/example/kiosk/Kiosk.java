package com.example.kiosk;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Kiosk {
    ArrayList<Menu> menus = new ArrayList<>();
    ArrayList<MenuItem> cart = new ArrayList<>();
    Scanner sc = new Scanner(System.in);
    int maxOption = menus.size();

    public void addMenu(Menu menu) {
        menus.add(menu);
    }

    public void execute() {
        while (true) {
            //Main 메뉴 출력
            System.out.println("\n[ MAIN MENU ]");

            //메뉴 카테고리 이름 출력
            for (int i = 0; i < menus.size(); i++) {
                System.out.printf("%d. %s\n", i + 1, menus.get(i).menuCategory);
            }
            System.out.println("0. 종료");

            //장바구니가 비어있지 않으면 order menu 출력 -> 여기까진 잘 실행됨
            if(!cart.isEmpty()) {
                System.out.println("[ ORDER MENU ]");
                System.out.printf("%d. Orders    | 장바구니를 확인 후 주문합니다.\n", menus.size()+1);
                System.out.printf("%d. Cancel    | 진행중인 주문을 취소합니다.\n",menus.size()+2);
            }

            System.out.printf("번호를 선택하세요 : ");

            try {
                int choice = sc.nextInt();


                if (!cart.isEmpty()) {
                    maxOption=menus.size();
                    maxOption += 2; // Orders, Cancel 추가
                }

                if (choice == 0) {
                    System.out.println("프로그램을 종료합니다.");
                    break;
                }

                //장바구니 메뉴 처리
                if(!cart.isEmpty()) {
                    if(choice ==menus.size()+1){
                        showCart();
                    }
                }

                //예외적인 상황?
                //try-catch에서는 자료형,네트워크,파일 등의 오류만 잡아줌.
                //99처럼 숫자라는 자료형은 맞지만 없는 메뉴 번호 선택시는 잡아주질 못함.
                //그래서 범위를 벗어난 숫자 검증 로직 추가
                if (choice < 1 || choice > menus.size()) {
                    System.out.println("없는 번호입니다. 다시 입력해주세요.");
                    continue;  // 다음 반복으로
                }

                showSubMenu(menus.get(choice - 1));

            } catch (InputMismatchException e) {
                System.out.println("숫자만 입력해주세요!");
                sc.nextLine();
            }
        }
        sc.close();
    }

    private void showSubMenu(Menu menu) {
        while (true) {
            menu.showMenu();
            System.out.print("번호를 선택하세요 : ");

            try{
                int subChoice = sc.nextInt();

                //뒤로가기
                if(subChoice==0) break;

                //메뉴 번호 검증
                if(subChoice < 1 || subChoice>menu.getItemCount()){
                    System.out.println("없는 번호입니다. 다시 입력해주세요.");
                    continue;
                }

                //정상 처리
                MenuItem item = menu.selectMenuItem(subChoice);
                System.out.printf("\n선택한 메뉴: ");
                item.showMenuItem();
                System.out.println("장바구니에 추가하시겠습니까?");
                System.out.println("1. 확인     2. 취소");
                System.out.print("번호를 입력하세요: ");
                int confirm = sc.nextInt();

                if(confirm==1){
                    cart.add(item);
                    System.out.println(item.name + "이 장바구니에 추가되었습니다.");
                }

            }catch(InputMismatchException e){
                System.out.println("숫자만 입력해주세요.");
                sc.nextLine();
            }
        }
    }

    private void showCart(){
            System.out.println("\n[ Orders ]");
            for (MenuItem menuItem : cart) {
                menuItem.showMenuItem();
            }
            int sum = cart.stream()
                    .mapToInt(item -> item.price)
                    .sum();

            System.out.println("Total: " + sum+"원");
            System.out.println("1. 주문     2. 메뉴판");


    }
}

