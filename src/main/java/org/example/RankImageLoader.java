package org.example;

import javafx.scene.image.Image;
import java.io.InputStream;

public class RankImageLoader {

    // Load rank image based on the rank
    public static Image loadRankImage(String rank) {
        String rankImagePath = "/Ranked_Emblems_Latest/" + rank + ".png"; // Ensure path starts with '/'

        InputStream imageStream = RankImageLoader.class.getResourceAsStream(rankImagePath);
        if (imageStream == null) {
            System.out.println("Rank image not found for rank: " + rank);
            return null;
        }

        return new Image(imageStream);
    }
}
