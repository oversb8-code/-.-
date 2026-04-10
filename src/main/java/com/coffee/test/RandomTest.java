package com.coffee.test;

import java.util.Random;

// 실행 단축키 Shift + f10
public class RandomTest {
    public static void main(String[] args) {
        Random rand = new Random();

        boolean bool = rand.nextBoolean();
        System.out.println(bool);

        int jusawee = rand.nextInt(6) +1;
        System.out.println(jusawee);

        String[] menu = {"제육볶음","돈가스","오므라이스","떡볶이", "마라탕"};


        String item = menu[rand.nextInt(menu.length)];
        String message = "오늘 점심 메뉴 : "+ item ;
        System.out.println(message);

        int price = 1000 * (rand.nextInt(5) +3) ; // 3000원 << 7000원
        System.out.println("가격 :" + price);

    }
}
