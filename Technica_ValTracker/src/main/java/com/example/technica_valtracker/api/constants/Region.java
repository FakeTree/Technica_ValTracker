package com.example.technica_valtracker.api.constants;

public enum Region
{
    BR1("br1"),
    EUN1("eun1"),
    EUW1("euw1"),
    JP1("jp1"),
    KR("kr"),
    LA1("la1"),
    LA2("la2"),
    ME1("me1"),
    NA1("na1"),
    OC1("oc1"),
    PH2("ph2"),
    RU("ru"),
    SG2("sg2"),
    TH2("th2"),
    TR1("tr1"),
    TW2("tw2"),
    VN2("vn2");

    private final String region;

    Region(String reg) {
        this.region = reg;
    }

    public String getRegion() {
        return region;
    }
}
