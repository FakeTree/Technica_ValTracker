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
            // Fallback to a default "Unranked" image
            rankImagePath = "/Ranked_Emblems_Latest/Unranked.png"; // Default/fallback image
            imageStream = RankImageLoader.class.getResourceAsStream(rankImagePath);
            if (imageStream == null) {
                System.out.println("Fallback rank image not found: Unranked");
                return null;
            }
        }

        return new Image(imageStream);
    }
}
