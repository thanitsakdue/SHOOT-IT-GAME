package shooterGame;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

public class EnemyGenerator {
    private final String filebat = "images/creatures/bat.png";
    private final String filebee = "images/creatures/bee.png";

    private BufferedImage[] greenFrames, blueFrames;
    private Random rand;

    // Define a minimum distance for enemy spawn from the player's cannon

    public EnemyGenerator() throws IOException {
        rand = new Random(); // Use the default random seed for variety  image roll
        greenFrames = SpriteSheetLoader.createSprites(filebat, 2, 5); 
        blueFrames = SpriteSheetLoader.createSprites(filebee, 3, 6);
    }






    

    public Opponents generateNewEnemy() {
    // Randomly generates new creatures.
    int creatureType = rand.nextInt(2);
    int spawnX, spawnY;

    // Ensure that enemies spawn from the right bound
    spawnX = Game.WIDTH - 50; // Adjust the value for horizontal positioning

    // Ensure that enemies spawn within a specific vertical range
    spawnY = rand.nextInt(Game.HEIGHT - 300) + 50; // Adjust the values for vertical range

    switch (creatureType) {
        case 0:
            return new Bat(greenFrames, 5, spawnX, spawnY);
        case 1:
            return new Bee(blueFrames, 12, spawnX, spawnY);
    }

    return null;
}

    // Check if the spawn coordinates are too close to the player's cannon
}
