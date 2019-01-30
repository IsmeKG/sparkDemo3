package com.stonto.DataModel;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ListTest {
    public static void main(String[] args) {
        List<String> list = new ArrayList<String>();
        list.add("aaa");
        list.add("bbb");
        list.add("ccc");

        for(String attribute : list) {
            System.out.println(attribute);
        }
    }
}

