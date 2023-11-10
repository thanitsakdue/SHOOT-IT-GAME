
package shooterGame;

import java.awt.image.BufferedImage;
import java.io.IOException;

public class Bee extends Opponents {
	private final int scorePoint = 300;
	
	protected int strength, shotCount;
	private int limit;// will be used as second living limit.
	
	// Constructor calls Class Bee's constructer with given arguments and
	//		half of the given frameLivingLimit argument because Bat has
	//		a strength value which means that after one shot its shape will be changed
	//		but it will not die untill the shotCount value reaches to strength value.
	public Bee(BufferedImage[] frames, int frameLivingLimit, int x, int y) {
		super(frames, frameLivingLimit / 2, x, y);
		
		setMoveSpeed(6);
		limit = frameLivingLimit;
		shotCount = 0;
		
		// Die after 3 shots.
		setStrength(3);
	}
	
	public Bee(String filePath, int row, int col, int frameLivingLimit,
			int x, int y) throws IOException {
		this(SpriteSheetLoader.createSprites(filePath, row, col), frameLivingLimit, x, y);
	}
	
	// Strength value may be greater or equal to 0 otherwise it throws an exception.
	public void setStrength(int strength) {
		if(strength >= 0)
			this.strength = strength;
		else
			throw new IllegalArgumentException("Value of strength CANNOT BE a negative number!");
	}

	@Override
	public void shooting() {
		// After the first shot the animation will start from 6th frame so
		//		the shape will be changed.
		// When shotCount value equals to strength value the manIsDown will be become to true,
		//		so it will be dead.
		if(++shotCount < strength) {
			frameStart = 6;
			frameLivingLimit = limit;
		}
		else
			manIsDown = true;
		repaint();
	}

	// Bat has own scorePoint value which is different from Opponents's default value 100.
	@Override
	public int getScorePoint() {
		return this.scorePoint;
	}
}
//beejah