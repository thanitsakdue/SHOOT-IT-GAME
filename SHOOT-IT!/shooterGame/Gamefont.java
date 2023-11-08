package shooterGame;

import java.awt.Font;
import java.io.InputStream;

public class Gamefont {

    public static Font getFont(int size) {
        Font font = null;
        try {
            InputStream fontStream = Gamefont.class.getResourceAsStream("font/SamuraiBlast-YznGj.ttf");
            font = Font.createFont(Font.TRUETYPE_FONT, fontStream);
            return font.deriveFont((float) size);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return font;
    }
}
