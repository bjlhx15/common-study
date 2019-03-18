package com.lhx.cloud;

import java.io.BufferedReader;

public class TestSwitch {
    public static void main(String[] args) {
        String a ="aa";
        switch (a){
            case "aa":
                System.out.println(a);
                break;
            case "bb":
                System.out.println("b");
                break;
        }
        System.out.println("ok");

        try(TryClose tryClose =new TryClose()){

        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
