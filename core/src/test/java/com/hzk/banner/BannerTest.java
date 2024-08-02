package com.hzk.banner;

import com.taobao.arthas.core.util.ArthasBanner;

public class BannerTest {

    public static void main(String[] args) {
        String welcome = ArthasBanner.welcome();
        System.out.println(welcome);

    }

}
