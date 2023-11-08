

package shooterGame;

import java.awt.HeadlessException;
import java.io.IOException;

import javax.swing.JOptionPane;

public class Main {

    public static void main(String[] args) {
        try {
            Game game = new Game("SHOOT IT!");
            game.startGame();
        } catch (HeadlessException e) {
            handleException("HeadlessException", e);
        } catch (IOException e) {
            handleException("IOException", e);
        } catch (IllegalArgumentException e) {
            handleException("IllegalArgumentException", e);
        }
    }

    private static void handleException(String exceptionType, Exception e) {
        JOptionPane.showMessageDialog(null,
                "Error: " + exceptionType + "\n" + e.getMessage(),
                "Something went wrong!", JOptionPane.ERROR_MESSAGE);
    }
}
