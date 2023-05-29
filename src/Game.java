import javax.swing.JFrame;
import java.awt.Dimension;
import java.awt.Toolkit;

public class Game {
    public static void main(String[] args) {
        // Create an instance of the Professor class to retrieve the player name and
        // selected PokeKalye
        Professor professor = new Professor();
        String playerName = professor.getPlayerName();
        String selectedPokeKalye = professor.getSelectedPokeKalye();

        JFrame frame = new JFrame("PokeKalye");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);

        // Set the preferred size of the frame
        frame.setPreferredSize(new Dimension(600, 400));

        // Calculate the center position of the screen
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int screenWidth = screenSize.width;
        int screenHeight = screenSize.height;
        int frameWidth = frame.getPreferredSize().width;
        int frameHeight = frame.getPreferredSize().height;
        int x = (screenWidth - frameWidth) / 2;
        int y = (screenHeight - frameHeight) / 2;

        // Set the frame's position
        frame.setLocation(x, y);

        // Create an instance of your game panel class with the player name and selected
        // PokeKalye
        GamePanel gamePanel = new GamePanel(playerName, selectedPokeKalye);
        frame.getContentPane().add(gamePanel);

        frame.pack();
        frame.setVisible(true);
    }
}
