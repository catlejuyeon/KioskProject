package com.example.kiosk;

import java.util.InputMismatchException;
import java.util.Scanner;

/*
    1. 사용자의 입력 담당 클래스
    2. 입력 검증 및 확인 메시지 처리
 */
public class InputManager {
    private Scanner sc;

    private static final int CONFIRM = 1;

    public InputManager(Scanner sc) {
        this.sc = sc;
    }

    //정수 입력 받기
    public int getIntInput(String message){
        System.out.println(message);
        return sc.nextInt();
    }

    //유효한 "수량" 입력 받기
    public int getValidQuantity(){
        while (true){
            try {
                System.out.print("수량을 입력해 주세요: ");
                int quantity = sc.nextInt();

                if(quantity<1){
                    System.out.println("수량은 최소 1개부터 입력할 수 있습니다.");
                    continue;
                }
                return quantity;
            }catch (InputMismatchException e){
                System.out.println("숫자만 입력해주세요.");
                sc.nextLine();
            }
        }
    }

    // 확인 메세지
    public boolean getConfirm(String message){
        System.out.println(message);
        System.out.println("1. 확인     2. 취소");
        System.out.print("번호를 입력하세요: ");
        try {
            return sc.nextInt() == CONFIRM;
        }catch (InputMismatchException e){
            sc.nextLine();
            return false;
        }
    }

    // 범위 내 선택 받기
    public int getValidChoice(int min, int max, String errorMessage){
        while (true){
            try {
                int choice = sc.nextInt();

                if(choice<min || choice>max){
                    System.out.println(errorMessage);
                    continue;
                }
                return choice;
            }catch (InputMismatchException e){
                System.out.println("숫자만 입력해주세요.");
                sc.nextLine();
            }
        }
    }
}


