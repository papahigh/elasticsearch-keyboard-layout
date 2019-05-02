package com.github.papahigh.keyboardswitcher;

public class Test {

    public static void main(String[] args) {
        System.out.println(KeyboardSwitcherProvider
                .provide("belarusian")
                .encode("выявіў некалькі выпадкаў"));
    }
}
