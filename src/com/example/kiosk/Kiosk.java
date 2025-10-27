package com.example.kiosk;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Kiosk {
    ArrayList<Menu> menus = new ArrayList<>();
    ArrayList<MenuItem> cart = new ArrayList<>();
    Scanner sc = new Scanner(System.in);


    public void addMenu(Menu menu) {
        menus.add(menu);
    }

    public void execute() {
        int maxOption = menus.size();
        while (true) {
            //Main 메뉴 출력
            System.out.println("\n[ MAIN MENU ]");

            //메뉴 카테고리 이름 출력
            for (int i = 0; i < menus.size(); i++) {
                System.out.printf("%d. %s\n", i + 1, menus.get(i).menuCategory);
            }


            //장바구니가 비어있지 않으면 order menu 출력 -> 여기까진 잘 실행됨
            if(!cart.isEmpty()) {
                System.out.println("[ ORDER MENU ]");
                System.out.printf("%d. Orders    | 장바구니를 확인 후 주문합니다.\n", 4);
                System.out.printf("%d. Cancel    | 진행중인 주문을 취소합니다.\n", 5);
            }
            System.out.println("---------------------");
            System.out.println("0. 종료");
            System.out.printf("번호를 선택하세요 : ");

            try {
                int choice = sc.nextInt();

                if (choice == 0) {
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
                if(choice < 4) {
                    showSubMenu(menus.get(choice - 1));
                } else if(!cart.isEmpty() && choice == 4) {
                    showCart();
                } else if(choice == 5) {
                    cancelCart();
                }

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

                System.out.printf("수량을 입력해 주세요: ");
                int quantity = sc.nextInt();

                if(quantity<1){
                    System.out.println("수량은 최소 1개부터 입력할 수 있습니다.");
                    continue;
                }

                System.out.println("장바구니에 추가하시겠습니까?");
                System.out.println("1. 확인     2. 취소");
                System.out.print("번호를 입력하세요: ");
                int confirm = sc.nextInt();

                if(confirm==1){
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

                if(confirm==2){
                    break;
                }

            }catch(InputMismatchException e){
                System.out.println("숫자만 입력해주세요.");
                sc.nextLine();
            }
        }
    }

    private void showCart(){
        while(true){
            System.out.println("\n[ Orders ]");
            for (MenuItem menuItem : cart) {
                menuItem.showMenuItem();
            }
            int sum = cart.stream()
                    .mapToInt(item -> item.price)
                    .sum();

            System.out.println("Total: " + sum+"원");
            System.out.println("1. 주문     2. 메뉴판");
            System.out.printf("번호를 입력하세요: ");

            try{
                int cartChoice = sc.nextInt();

                if(cartChoice==2) break;

                if(cartChoice ==1){
                    discountAndOrder();
                    break;
                }

            }catch(InputMismatchException e){
                System.out.println("숫자만 입력해주세요.");
                sc.nextLine();
            }
        }
    }

    //취소를 좀 더 자세히 구현해?(현재 모두 취소 뿐)
    private void cancelCart(){
        while(true){
            System.out.println("\n[ Cancel ]");
                System.out.println("주문을 취소 하시겠습니까?");
                System.out.println("1. 부분 취소");
                System.out.println("2. 전체 취소");
                System.out.println("0. 메뉴판");
                System.out.printf("번호를 입력하세요: \n");

            try{
                int cancelChoice = sc.nextInt();

                if(cancelChoice==0) break;

                if(cancelChoice == 2){
                    System.out.println("정말 주문을 전체 취소하시겠습니까?");
                    System.out.println("1. 확인      2. 취소");
                    int confirm = sc.nextInt();
                    if(confirm==1){
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

    //장바구니 부분취소..?일단해봐 + 스트림 사용해서
    //같은 메뉴가 두개 이상일 때 다 사라지는지, 하나만 삭제되는지 확인..(몇개 뺄지도 값을 받아야하는 건가?)
    //몇개 뺼건지 받자
    private void cancelPartialOrder(){
        while(true){
            System.out.println("[ Cart List ]");
            for(MenuItem menuItem : cart){
                menuItem.showMenuItem();
            }
            System.out.println("0. 뒤로가기");
            System.out.println("-------------");
            System.out.print("취소할 메뉴 번호를 선택하세요: \n");

            try{
                int choice=sc.nextInt();

                if(choice==0) return;

                if(choice>=1&&choice<=cart.size()) {
                    MenuItem selectedItem = cart.get(choice - 1);//메뉴이름가져오기

                    System.out.println("현재 수량: " + selectedItem.getQuantity() + "개");
                    System.out.println("1. 수량 수정");
                    System.out.println("2. 메뉴 전체 삭제");
                    System.out.println("0. 뒤로가기");
                    System.out.print("번호를 선택하세요: ");

                    int editChoice = sc.nextInt();

                    if(editChoice == 0) continue;

                    if(editChoice == 1){
                        System.out.print("변경할 수량을 입력하세요: ");
                        int newQuantity = sc.nextInt();

                        if(newQuantity > 0){
                            selectedItem.setQuantity(newQuantity);
                            System.out.println("수량이 " + newQuantity + "개로 변경되었습니다.");
                        }else if(newQuantity == 0){
                            System.out.println("수량을 0으로 설정하면 삭제됩니다. 삭제하시겠습니까?");
                            System.out.println("1. 확인     2. 취소");
                            int confirmDelete = sc.nextInt();
                            if (confirmDelete == 1){
                                removeFromCart(selectedItem.name);
                            }
                        }else{
                            System.out.println("잘못된 수량입니다.");
                        }

                    }else if(editChoice == 2){
                        System.out.println("정말 삭제하시겠습니까?");
                        System.out.println("1. 확인     2. 취소");
                        int confirmDelete = sc.nextInt();
                        if(confirmDelete == 1){
                            removeFromCart(selectedItem.name);
                        }
                    }
                }else{
                    System.out.println("잘못된 번호입니다.");
                }
            }catch(InputMismatchException e){
                System.out.println("숫자만 입력해 주세요.");
                sc.nextLine();
            }
        }
    }

    private void removeFromCart(String menuName){
        cart.removeIf(item -> item.name.equals(menuName));
        System.out.println(menuName+"이(가) 장바구니에서 제거되었습니다.");
    }

    private void discountAndOrder(){
        Discount[] discount = Discount.values();
        while(true){
            System.out.println("할인 정보를 입력해주세요.");

            int sum = cart.stream()
                    .mapToInt(item -> item.price)
                    .sum();

            for(int i=0; i<discount.length; i++){
                System.out.println((i+1)+ ". "+discount[i].getDiscountName());
            }
            System.out.printf("번호를 입력해 주세요: ");

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

