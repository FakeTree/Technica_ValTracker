package org.example;

public class Champion {
    private final String id;
    private final String name;
    private final String title;
    private final String blurb;
    private final String iconUrl;
    private final String rank;

    public Champion(String id, String name, String title, String blurb, String iconUrl, String rank) {
        this.id = id;
        this.name = name;
        this.title = title;
        this.blurb = blurb;
        this.iconUrl = iconUrl;
        this.rank = rank;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public String getTitle() { return title; }
    public String getBlurb() { return blurb; }
    public String getIconUrl() { return iconUrl; }
    public String getRank() { return rank; }
}
