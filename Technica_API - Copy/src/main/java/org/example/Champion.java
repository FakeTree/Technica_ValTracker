package org.example;

public class Champion {

    private String id;
    private String name;
    private String title;
    private String blurb;
    private String iconUrl;

    // Constructor
    public Champion(String id, String name, String title, String blurb, String iconUrl) {
        this.id = id;
        this.name = name;
        this.title = title;
        this.blurb = blurb;
        this.iconUrl = iconUrl;
    }

    // Getters
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getTitle() {
        return title;
    }

    public String getBlurb() {
        return blurb;
    }

    public String getIconUrl() {
        return iconUrl;
    }
}
