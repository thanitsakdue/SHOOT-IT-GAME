
package shooterGame;

import javax.swing.Timer;

public interface Creature {
	public void move();// Calculates new position of the Creature.
	public boolean isAlive();
	public void update();// Decides which frame should be current.
	public int getScorePoint();
	Timer getAnimationTimer();
}
