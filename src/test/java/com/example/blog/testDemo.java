package com.example.blog;

import org.junit.Test;

public class testDemo {

    @Test
    public void contextLoads() {
        String url = "aaa?nihc";
        System.out.println(url.substring(0,url.indexOf('?')));
    }



}
