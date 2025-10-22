package com.example.kiosk;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Kiosk {
    ArrayList<Menu> menus = new ArrayList<>();
    Scanner sc = new Scanner(System.in);

    public void addMenu(Menu menu) {
        menus.add(menu);
    }

    public void execute() {

        while (true) {
            //Main 메뉴 출력
            System.out.println("[ MAIN MENU ]");
            //메뉴 카테고리 이름 출력
            for (int i = 0; i < menus.size(); i++) {
                System.out.printf("%d. %s\n", i + 1, menus.get(i).menuCategory);
            }
            System.out.println("0. 종료");
            System.out.println("번호를 선택하세요 : ");

            try {
                int choice = sc.nextInt();

                if (choice == 0) {
                    System.out.println("프로그램을 종료합니다.");
                    break;
                }

                //예외적인 상황?
                //try-catch에서는 자료형,네트워크,파일 등의 오류만 잡아줌.
                //99처럼 숫자라는 자료형은 맞지만 없는 메뉴 번호 선택시는 잡아주질 못함.
                //그래서 범위를 벗어난 숫자 검증 로직 추가
                if (choice < 1 || choice > menus.size()) {
                    System.out.printf("없는 번호입니다. 다시 입력해주세요.");
                    continue;  // 다음 반복으로
                }

                showSubMenu(menus.get(choice - 1));

            } catch (InputMismatchException e) {
                System.out.println("⚠️ 숫자만 입력해주세요!");
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
                System.out.println("선택한 메뉴: ");
                item.showMenuItem();
            }catch(InputMismatchException e){
                System.out.println("숫자만 입력해주세요.");
                sc.nextLine();
            }
        }
    }
}

