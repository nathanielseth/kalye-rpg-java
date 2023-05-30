import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import java.awt.Dimension;
import java.awt.Toolkit;

public class Game {
    public static void main(String[] args) {
        Professor professor = new Professor();
        String playerName = professor.getPlayerName();
        String selectedPokeKalye = PokeKalyeChooser.getSelectedPokeKalye();

        JFrame frame = new JFrame("PokeKalye");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);

        frame.setPreferredSize(new Dimension(600, 400));

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int screenWidth = screenSize.width;
        int screenHeight = screenSize.height;
        int frameWidth = frame.getPreferredSize().width;
        int frameHeight = frame.getPreferredSize().height;
        int x = (screenWidth - frameWidth) / 2;
        int y = (screenHeight - frameHeight) / 2;

        frame.setLocation(x, y);

        GamePanel gamePanel = new GamePanel(playerName, selectedPokeKalye);

        SwingUtilities.invokeLater(() -> {
            gamePanel.showIntroScreen();
        });

        frame.getContentPane().add(gamePanel);

        frame.pack();
        frame.setVisible(true);
    }
}
