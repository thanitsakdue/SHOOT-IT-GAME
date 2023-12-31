
package shooterGame;


import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

import shooterGame.Game.GameState;

public class GameBoard extends JPanel {
	private final String fileBackground = "images/backgrounds/background.png";
	private final String fileBackground2 = "images/backgrounds/background2.png";
	private final String fileBackground3 = "images/backgrounds/lastbkg.png";
	private EnemyGenerator generator;
	protected static boolean isGameOver = false;
	public int score;
	
	private boolean isStopped;
	
	private Cannon cannon;
	private JLabel lblScore;
	private JPanel headerPanel;
	private JButton restartButton, stopButton;
	private Timer mainTimer;
	private Font defaultFont;
	private JCheckBox effectOn;

	
	public GameBoard() throws IOException {
		isStopped = false;

		generator = new EnemyGenerator();
		isGameOver = false; // It changes to the true by move function of Class Opponents.
		score = 0;
		defaultFont = new Font(Font.SERIF, Font.BOLD, 24);
		
		setEffectOn();
		
		// If file is missing do not interrupt the program just disable sound option.
		URL clipUrl = getClass().getResource("sound/laser.wav");
		if(clipUrl == null) {
			effectOn.setSelected(false);
			effectOn.setEnabled(false);
		}
		else
			
		
		// Set position and size.
		setBounds(0, 0, Game.WIDTH, Game.HEIGHT);
		
		// Set layout as null since every component's position are declared by setLocation or setBounds functions.
		setLayout(null);
		
		setCursor(Game.CURSOR_UNLOCKED);
		setHeader();
		setCannon();
	}
	
	private void setEffectOn() {
		effectOn = new JCheckBox("SFX (Not working) ", true);
		effectOn.setBackground(new Color(0, 0, 0, 0)); // Transparent background.
		effectOn.setFont(defaultFont);
		effectOn.setFocusable(false);
	}
	public int getScore() {
		return score;
	}
	public void updateScore(int newScore) {
        score = newScore;
    }
	private void setHeader() {
		JLabel scoreMsg = new JLabel("Score:");
		scoreMsg.setForeground(Color.BLACK);
		scoreMsg.setFont(defaultFont);
		
		lblScore = new JLabel(Integer.toString(score));
		lblScore.setForeground(Color.BLACK);
		lblScore.setFont(defaultFont);
		
		headerPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		headerPanel.setBounds(0, 0, Game.WIDTH-25, 50);
		
		headerPanel.setBackground(new Color(0, 0, 0, 0));
		
		headerPanel.add(effectOn);
		headerPanel.add(scoreMsg);
		headerPanel.add(lblScore);
		
		//Add restart and stop buttons
		BufferedImage[] buttonIcons = null;
		try {
			buttonIcons = SpriteSheetLoader.createSprites("images/buttons/innerbuttons.png", 2, 3);
		} catch (IOException e) {
			e.printStackTrace();
		}
		ImageIcon[] icons = new ImageIcon[buttonIcons.length];
		for(int i = 0; i < buttonIcons.length; ++i)
			icons[i] = new ImageIcon(buttonIcons[i]);

		restartButton = createButton(icons[0], icons[1], icons[2]);
		stopButton = createButton(icons[3], icons[4], icons[5]);
		
		headerPanel.add(restartButton);
		headerPanel.add(stopButton);

		add(headerPanel);
		
		headerPanel.setVisible(true);
	}
	
	// Helper function to create a button.
	// Gets 3 file paths to creates 3 icons.
	// icon[0]: button icon
	// icon[1]: roll over icon.
	// icon[2]: pressed icon.
	private JButton createButton(Icon icon0, Icon icon1, Icon icon2) {
//		ImageIcon[] icon = new ImageIcon[iconFiles.length];
//		for(int i = 0; i < iconFiles.length; ++i)
//			icon[i] = new ImageIcon(getClass().getResource(iconFiles[i]));

		JButton button = new JButton(icon0);
		button.setPreferredSize(new Dimension(icon0.getIconWidth(), icon0.getIconHeight()));
		button.setBorderPainted(false);
		button.setFocusable(true);
		button.setFocusPainted(false);
		button.setRolloverEnabled(true);
		button.setRolloverIcon(icon1);
		button.setPressedIcon(icon2);
		button.setContentAreaFilled(false);
		button.addActionListener(new InnerButtonListener());
		
		return button;
	}
	
	private void setCannon() {
		cannon = new Cannon();
		add(cannon);
		
		// MouseMotionListener calls Cannon's rotate function to follow cursor.
		addMouseMotionListener(new MouseMotionListener() {		
			@Override
			public void mouseMoved(MouseEvent e) {
				cannon.rotate(e.getX(), e.getY(), false);
			}		
			@Override
			public void mouseDragged(MouseEvent arg0) {
			}
		});// End of addMouseMotionListener. 
		
		// When clicked on blank area cannon will be fired with MouseListener.
		// If there is no mouselistener added in the GameBoard, 
		// 		cannon be fired only when clicked on Enemys.
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Cannon.setFire(true);
			}
			@Override
			public void mousePressed(MouseEvent e) {
				Cannon.setFire(true);
			}
			@Override
			public void mouseReleased(MouseEvent e) {
				Cannon.setFire(false);
			}		
		});// End of addMouseListener.
	}// End of setCannon
	
	public Thread generator() {
		// Begin with 4 Enemy;
		for(int i = 0; i < 4; ++i)
			add(generator.generateNewEnemy());
		
		// Generates new Enemy until game is over.
		// Generation time decreases every second.
		return new Thread(new Runnable() {	

			@Override
			public void run() {
				int s = 1000;
				while(!isGameOver && !isStopped) {
					try {
						Thread.sleep(s);
					} catch (InterruptedException e) {
						JOptionPane.showMessageDialog(null,
								"We are sorry about that!\nError: " + e.getMessage(),
								"Opps!! Something went wrong!", JOptionPane.ERROR_MESSAGE);
					}
					if(isStopped)
						return;
					if(!isGameOver)
						add(generator.generateNewEnemy());
					if(s > Game.REFRESH_TIME + 50)
						s -= 4;
					else
						s = Game.REFRESH_TIME + 50;
				}// End of while
			}});// End of new Thread
	}// End of generator()
	
	public void gameLoop() {
		generator().start();
		mainTimer = new Timer(Game.REFRESH_TIME, new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				repaint();
				if(isGameOver) {
					updateScore(score);
					gameOver();
					return;
				}
				for(Component e : getComponents()) {
					if(e instanceof Creature){
						Creature i = (Creature) e;
						if(!i.isAlive()) {
							if(effectOn.isSelected())
								
							score += i.getScorePoint();
							lblScore.setText(Integer.toString(score));
							remove(e);
						}
					}
				}// End of for
			}// End of ActionPerformed
		});// End of mainTimer
		mainTimer.start();
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		// Draw backgroung image.
		if(score >= 1200){
			g.drawImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource(fileBackground3)), 0, 0, null);
		}
		else if(score>=500)
		{
			g.drawImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource(fileBackground2)), 0, 0, null);	
		}
		else{
			g.drawImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource(fileBackground)), 0, 0, null);	
		}
	}
	
public void gameOver() {
    mainTimer.stop();
    for (Component c : getComponents()) {
        if (c instanceof Creature)
            ((Creature) c).getAnimationTimer().stop();
        remove(c);
    }
	Game.updateScore(score);
    Game.setHighScore(score);
    Game.setState(GameState.OVER); // Set the game state to "OVER" directly
}
	
	private class InnerButtonListener implements ActionListener {
		public void gameStop() {
			isStopped = true;
			mainTimer.stop();
			for(Component c : getComponents()) {
				if(c instanceof Creature)
					((Creature) c).getAnimationTimer().stop();
				remove(c);
			}
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			if(e.getSource() == restartButton) {
				gameStop();
				Game.setState(GameState.CONTINUE);
			}
			else if(e.getSource() == stopButton) {
				gameStop();
				Game.setState(GameState.OVER);
			}
		}
	}
}
