//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.example.technica_valtracker;


//THIS CLASS IS USED TO PROVIDE THE API KEY FOR RIOT API, THIS KEY MUST BE REPLACED EVERY 24 HOURS, AS PER RIOT's API TERMS



public class Constants {
    public static String RIOT_API_KEY = "RGAPI-e2a1ac63-de2e-4cde-a599-7bbf016b7ac0";
    public static String[] requestHeaders;

    public Constants() {
    }

    static {
        requestHeaders = new String[]{"X-Riot-Token", RIOT_API_KEY};
    }
}
