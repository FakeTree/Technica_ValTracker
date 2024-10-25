//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.example.technica_valtracker;


//THIS CLASS IS USED TO PROVIDE THE API KEY FOR RIOT API, THIS KEY MUST BE REPLACED EVERY 24 HOURS, AS PER RIOT's API TERMS



public class Constants {
    public static String RIOT_API_KEY = "RGAPI-2f78b945-0429-4c6e-822b-c72446f2ca5f";
    public static String[] requestHeaders;

    public Constants() {
    }

    static {
        requestHeaders = new String[]{"X-Riot-Token", RIOT_API_KEY};
    }
}
