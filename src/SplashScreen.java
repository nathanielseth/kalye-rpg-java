import java.awt.Dimension;
import java.io.File;
import java.io.IOException;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

public class SplashScreen {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                new SplashScreen();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public SplashScreen() throws IOException {
        String iconPath = "media/images/KalyeRPG.png";
        ImageIcon icon = new ImageIcon(iconPath);
        JFrame splashFrame = createSplashFrame("media/images/PokeKalye.gif", 300, 300);
        splashFrame.setIconImage(icon.getImage());
        playAudio("media/audio/splashTheme.wav");
        Timer timer = new Timer(7500, e -> {
            splashFrame.dispose();
            showSecondSplash();
        });
        timer.setRepeats(false);
        timer.start();
        splashFrame.setVisible(true);
    }

    private void showSecondSplash() {
        SwingUtilities.invokeLater(() -> {
            try {
                JFrame splashFrame = createSplashFrame("media/images/Warning.png", 300, 300);
                Timer timer = new Timer(900, e -> {
                    splashFrame.dispose();
                    launchGame();
                });
                timer.setRepeats(false);
                timer.start();
                splashFrame.setVisible(true);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
    }

    private JFrame createSplashFrame(String imagePath, int width, int height) throws IOException {
        JFrame splashFrame = new JFrame();
        splashFrame.setLayout(null);
        JLabel splashLabel = new JLabel();
        splashLabel.setBounds(0, 0, width, height);
        ImageIcon splashIcon = new ImageIcon(imagePath);
        splashLabel.setIcon(splashIcon);
        splashFrame.add(splashLabel);
        splashFrame.setUndecorated(true);
        splashFrame.setSize(new Dimension(width, height));
        splashFrame.setLocationRelativeTo(null);
        return splashFrame;
    }

    private void launchGame() {
        MainMenu.main(null);
    }

    private void playAudio(String audioPath) {
        try {
            File audioFile = new File(audioPath);
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(audioFile);
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
