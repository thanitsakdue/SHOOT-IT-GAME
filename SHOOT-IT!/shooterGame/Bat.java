

package shooterGame;

import java.awt.image.BufferedImage;
import java.io.IOException;

public class Bat extends Opponents {

	// Constructor calls Class Opponents constructer with given arguments
	//		and sets speed to 5.
	public Bat(BufferedImage[] frames, int frameLivingLimit, int x, int y) {
		super(frames, frameLivingLimit, x, y);
		
		setMoveSpeed(5);
	}

	// Constructor creates a BufferedImage array with given filePath argument
	//		and calls the constructor Bat(BufferedImage[] frames, int frameLivingLimit, int x, int y)
	public Bat(String filePath, int row, int col, int frameLivingLimit,
			int x, int y) throws IOException {
		this(SpriteSheetLoader.createSprites(filePath, row, col), frameLivingLimit, x, y);
	}

	// shooting function only changes manIsDown field to true,
	//		because the strength of the Bat is 0 which
	//		means that it will die after one shot.
	@Override
	public void shooting() {
		manIsDown = true;
	}

}
