package com.example.kiosk;

public class SetItem extends MenuItem{
    private String[] items;
    private int setDiscount;

    public SetItem(int id, String name, int price, String description, String[] items, int setDiscount) {
        super(id,name,price,description);
        this.setDiscount = setDiscount;
        this.items=items;
    }

    @Override
    public int getPrice() {
        return price - setDiscount;  // 할인 적용된 가격
    }

    public int getOriginalPrice() {
        return price;  // 원래 가격
    }

    @Override
    public void display() {
        System.out.printf("%s | %d원 (원가: %d원, -%d원 할인) | %s [",
                name, getPrice(), getOriginalPrice(), setDiscount, description);
        for(int i=0; i<items.length; i++){
            System.out.print(items[i]);
            if(i < items.length - 1) System.out.print(" + ");
        }
        System.out.println("]");
    }
}
