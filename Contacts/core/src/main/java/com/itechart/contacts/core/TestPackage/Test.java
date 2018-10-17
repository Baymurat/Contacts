package com.itechart.contacts.core.TestPackage;

public class Test {
    public static void main(String[] args) {
        String string = "SELECT * FROM persons WHERE     AND surname = ? AND middlename = ?";
        System.out.println(string);
        string = string.replace("WHERE+\\sAND", "WHERE");
        System.out.println(string);
    }
}
