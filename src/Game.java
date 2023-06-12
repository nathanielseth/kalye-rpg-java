import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import java.awt.Dimension;
import java.awt.Toolkit;

public class Game {
    public static void main(String[] args) {
        String selectedPokeKalye = PokeKalyeChooser.getSelectedPokeKalye();

        String iconPath = "media/images/KalyeRPG.png";
        ImageIcon icon = new ImageIcon(iconPath);

        JFrame frame = new JFrame("KalyeRPG");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setIconImage(icon.getImage());

        frame.setPreferredSize(new Dimension(600, 400));

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int screenWidth = screenSize.width;
        int screenHeight = screenSize.height;
        int frameWidth = frame.getPreferredSize().width;
        int frameHeight = frame.getPreferredSize().height;
        int x = (screenWidth - frameWidth) / 2;
        int y = (screenHeight - frameHeight) / 2;

        frame.setLocation(x, y);

        GamePanel gamePanel = new GamePanel(selectedPokeKalye);

        frame.getContentPane().add(gamePanel);

        SwingUtilities.invokeLater(() -> {
            frame.pack();
            frame.setVisible(true);
            gamePanel.showIntroScreen();
        });
    }
}