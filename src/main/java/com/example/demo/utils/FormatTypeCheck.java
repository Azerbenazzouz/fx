package com.example.demo.utils;

public class FormatTypeCheck {

    public static boolean checkInt(String s){
        try {
            Integer.parseInt(s);
            return true;
        }catch (NumberFormatException e){
            return false;
        }
    }

    public static boolean checkFloat(String s){
        try {
            Float.parseFloat(s);
            return true;
        }catch (NumberFormatException e){
            return false;
        }
    }

}
