package com.zr.blog.demo;

public class Demo1 {

    public static void main(String[] args) {
        String  t = "123456789;";
        System.err.println( t.substring(0, t.lastIndexOf(";")) );
    }

}
