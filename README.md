# Kiosk Project 🍔🥤🍦

## 프로젝트 개요
Java 콘솔 기반 키오스크 시스템.  
사용자는 메뉴를 선택하고, 수량 입력, 장바구니 관리, 주문, 취소까지 수행 가능.  
세트 메뉴 할인 및 구성 아이템 표시 기능 포함.

## 주요 기능

1. **메뉴 관리**
   - 햄버거, 음료, 디저트 카테고리
   - 각 메뉴는 이름, 가격, 설명을 포함
   - 세트 메뉴는 구성 아이템과 할인 금액 표시

2. **장바구니**
   - 메뉴 아이템 추가, 중복 체크 및 수량 조정
   - 장바구니 내용 조회
   - 전체/부분 취소 가능
   - 총액 계산 시 세트 할인 적용

3. **주문 처리**
   - 장바구니 확인 후 주문
   - 세트 메뉴 할인 가격 반영
   - 주문 완료 시 장바구니 초기화

4. **입력 검증**
   - 숫자만 입력 가능
   - 수량 1 이상 체크
   - 확인/취소 선택 처리

## 클래스 구조
| 클래스 | 역할 |
|--------|------|
| `Main` | 초기 메뉴 구성 및 키오스크 실행 |
| `MenuItem` | 개별 메뉴 아이템 정보 |
| `SetItem` | 세트 메뉴 아이템, 할인/구성 포함 |
| `Menu` | 메뉴 카테고리, 메뉴 출력 및 선택 |
| `Kiosk` | 전체 흐름 제어, 메인 메뉴 출력, 하위 메뉴 호출 |
| `CartManager` | 장바구니 추가, 삭제, 수정, 조회, 총액 계산 |
| `InputManager` | 사용자 입력 처리, 수량/확인 검증 |
| `CartService` | 주문/취소/장바구니 UI 처리 |

## 실행 방법
1. 프로젝트를 Java IDE(IntelliJ, Eclipse 등)에 임포트
2. `Main.java` 실행
3. 콘솔에서 번호 선택 후 메뉴 진행

## 예시 출력
```
[ MAIN MENU ]
1. Burgers
2. Drinks
3. Desserts
---------------------
0. 종료
번호를 선택하세요 : 1

[ BURGERS MENU ]
1. ShackeBurger  | 6900원 | 토마토, 양상추, 쉑소스가 토핑된 치즈버거
2. SmokeShack    | 8900원 | 베이컨, 체리 페퍼에 쉑소스가 토핑된 치즈버거
3. Cheeseburger  | 6900원 | 포테이토 번과 비프패티, 치즈가 토핑된 치즈버거
4. Hamburger     | 5400원 | 비프패티를 기반으로 야채가 들어간 기본버거
0. 뒤로가기
번호를 선택하세요 : 1

선택한 메뉴: ShackeBurger  | 6900원 | 토마토, 양상추, 쉑소스가 토핑된 치즈버거
수량을 입력해 주세요: 3
장바구니에 추가하시겠습니까?
1. 확인     2. 취소
번호를 입력하세요: 1
ShackeBurger  3개가 장바구니에 추가되었습니다.

[ BURGERS MENU ]
1. ShackeBurger  | 6900원 | 토마토, 양상추, 쉑소스가 토핑된 치즈버거
2. SmokeShack    | 8900원 | 베이컨, 체리 페퍼에 쉑소스가 토핑된 치즈버거
3. Cheeseburger  | 6900원 | 포테이토 번과 비프패티, 치즈가 토핑된 치즈버거
4. Hamburger     | 5400원 | 비프패티를 기반으로 야채가 들어간 기본버거
0. 뒤로가기
번호를 선택하세요 : 0

[ MAIN MENU ]
1. Burgers
2. Drinks
3. Desserts
[ ORDER MENU ]
4. Orders    | 장바구니를 확인 후 주문합니다.
5. Cancel    | 진행중인 주문을 취소합니다.
---------------------
0. 종료
번호를 선택하세요 : 4

[ Orders ]
ShackeBurger | 6900원 | 3개
Total: 20700원
1. 주문     2. 메뉴판
번호를 입력하세요: 1
할인 정보를 입력해 주세요.
1. 국가유공자
2. 군인
3. 학생
4. 일반인
번호를 입력해 주세요: 1
할인된 금액: 2070원
최종 금액: 18630원
```
## 🏗️ 아키텍처
#### 클래스구조
```
🔑 클래스/메서드 요약

com/example/kiosk/
├── Main.java        // 프로그램 시작, 메뉴/세트 아이템 생성, Kiosk 초기화
├── Kiosk.java       // 키오스크 전체 흐름 제어 (메뉴 선택, 주문, 취소)
│   ├── addMenu(Menu menu)
│   ├── execute()
│   └── showSubMenu(Menu menu)
├── Menu.java        // 메뉴 카테고리 관리, 메뉴 항목 보관 및 출력
│   ├── addItem(MenuItem item)
│   ├── showMenu()
│   ├── selectMenuItem(int choice)
│   └── getItemCount()
├── MenuItem.java    // 단일 메뉴 아이템 정보 저장 및 표시
│   ├── getId()
│   ├── getName()
│   ├── getPrice()
│   ├── getDescription()
│   ├── getQuantity()
│   ├── setQuantity(int quantity)
│   └── display()
├── SetItem.java     // 세트 메뉴 정보 관리 (할인, 포함 아이템)
│   ├── getItems()
│   ├── getSetDiscount()
│   ├── getPrice()
│   ├── getOriginalPrice()
│   └── display()
├── CartManager.java // 장바구니 관리 (추가, 삭제, 조회, 총액 계산)
│   ├── addItem(Product item, int quantity)
│   ├── removeItem(int id)
│   ├── clear()
│   ├── getTotalPrice()
│   ├── displayCart()
│   ├── displayDetailedCart()
│   ├── isEmpty()
│   ├── size()
│   └── getItem(int index)
├── CartService.java // 장바구니 화면 및 주문/취소 로직 처리
│   ├── showCartAndOrder()
│   └── showCancel()
├── InputManager.java // 사용자 입력 처리 및 검증
│   ├── getIntInput()
│   ├── getValidQuantity()
│   ├── getConfirm(String message)
│   └── clearInputValue()
├── Product.java     // 메뉴/세트 아이템 공통 기능 정의
│   ├── getId()
│   ├── getName()
│   ├── getPrice()
│   ├── getDescription()
│   ├── getQuantity()
│   ├── setQuantity(int quantity)
│   └── display()
└── Discount.java    // 할인 종류 및 계산
    ├── getDiscount(int price)
    ├── getFinalPrice(int price)
    └── getDiscountName()
```
#### 메뉴 계층 구조
```
Product (인터페이스)
    ↑
MenuItem (기본 상품)
    ↑
SetItem (세트 상품 - 할인 적용)
```
## 🎯 주요 디자인 패턴
#### 1. 인터페이스 기반 설계
- Product 인터페이스: 모든 상품의 공통 규격 정의

#### 2. 상속을 통한 확장
- SetItem: MenuItem을 상속받아 디저트 세트(아이스크림+와플) 구현
- 세트 할인 기능 추가
- 다형성을 활용한 유연한 상품 관리

#### 3. 단일 책임 원칙 (SRP)
- 각 클래스가 하나의 책임만 담당
- CartManager: 데이터 관리
- CartService: 비즈니스 로직
- InputHandler: 입력 처리

## 📁 프로젝트 구조
```
src/com/example/kiosk/
├── Kiosk.java              # 메인 컨트롤러
├── Main.java               # 프로그램 진입점
├── Menu.java               # 메뉴 카테고리
├── Product.java            # 상품 인터페이스
├── MenuItem.java           # 기본 상품
├── SetItem.java            # 세트 상품
├── CartManager.java        # 장바구니 관리
├── CartService.java        # 주문/취소 로직
├── InputHandler.java       # 입력 검증
└── Discount.java           # 할인 정책 (Enum)
```
## 📝 핵심 기능 상세
#### 장바구니 중복 체크
```
Product existingItem = storage.findById(item.getId());
if (existingItem != null) {
    existingItem.setQuantity(existingItem.getQuantity() + quantity);
}
```
#### 할인 적용(SetItem)
```
@Override
public int getPrice() {
    return price - setDiscount;  // 자동으로 할인된 가격 반환
}
```
#### 입력 검증
```
public int getValidQuantity() {
    while (true) {
        try {
            int quantity = sc.nextInt();
            if (quantity < 1) {
                System.out.println("수량은 최소 1개부터 입력할 수 있습니다.");
                continue;
            }
            return quantity;
        } catch (InputMismatchException e) {
            System.out.println("숫자만 입력해주세요.");
            sc.nextLine();
        }
    }
}
```
## 🎓 학습 포인트
1. 객체지향 설계: 인터페이스와 상속을 통한 확장 가능한 구조
2. 디자인 패턴: DI, SRP, Strategy Pattern
3. 함수형 프로그래밍: Stream API, Lambda
4. 예외 처리: try-catch를 통한 안정적인 입력 처리
5. 코드 리팩토링: 단일 파일에서 역할별 클래스 분리

## 📈 확장 가능성
#### 현재 구조의 장점
- 새로운 상품 타입 추가 용이 (Product 인터페이스 구현)
- 메뉴 카테고리 동적 추가
- 할인 정책 확장 가능 (Discount Enum)
