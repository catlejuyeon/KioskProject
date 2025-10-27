package com.example.kiosk;

public enum Discount {
    NATIONALMERIT("국가유공자",0.1),
    MILITARY("군인",0.05),
    STUDENT("학생",0.03),
    PERSON("일반인",1);

    private final double rate;
    private final String discountName;

    Discount(String discountName, double rate) {
        this.rate = rate;
        this.discountName=discountName;
    }

    public int getDiscount(int price) {
        return (int)(price * rate);
    }

    public int getFinalPrice(int price) {
        return price - getDiscount(price);
    }

    public String getDiscountName(){
        return discountName;
    }
}
