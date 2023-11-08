

package shooterGame;

import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

public class SpriteSheetLoader {
	
	public SpriteSheetLoader() {		
	}
	
	// It reads the image file from filePath and creates a BufferedImage array
	//		with dividing the image file into subimages according to given row and col values.
	public static BufferedImage[] createSprites(String filePath, int row, int col) 
			throws IOException {
		if(row <= 0 || col <= 0)
			throw new IllegalArgumentException("row and col must be positive!");
		
		// Size of array will be col * row
		BufferedImage[] sprites = new BufferedImage[row * col];
		
		// Read the Image
		BufferedImage spriteSheet = ImageIO.read(SpriteSheetLoader.class.getResourceAsStream(filePath)); //ImageIO.read(new File(filePath));
		
		// Calculate width and height of the each sprite.
		//		We assume that sprite sheet consists of all same sized sprites.
		int width = spriteSheet.getWidth() / col;
		int height = spriteSheet.getHeight() / row;

		// Get sprites (sub images) by left to right order from sprite sheet. 
		for(int i = 0; i < row; ++i)
			for(int j = 0; j < col; ++j)
				sprites[(i * col) + j] = spriteSheet.getSubimage(j * width, i * height, width, height);
		
		return sprites;
	}
}
