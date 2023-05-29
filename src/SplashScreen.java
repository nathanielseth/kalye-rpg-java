import java.awt.Dimension;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
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
        JFrame splashFrame = createSplashFrame("images/PokeKalye.png", 300, 300);
        Timer timer = new Timer(7000, e -> {
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
                JFrame splashFrame = createSplashFrame("images/Warning.png", 300, 300);
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
        JLabel splashLabel = new JLabel();
        Image splashImage = createImage(imagePath, width, height);
        ImageIcon splashIcon = new ImageIcon(splashImage);

        splashLabel.setIcon(splashIcon);
        splashFrame.add(splashLabel);
        splashFrame.setUndecorated(true);
        splashFrame.setSize(new Dimension(width, height));
        splashFrame.setLocationRelativeTo(null);
        return splashFrame;
    }

    private Image createImage(String imagePath, int width, int height) throws IOException {
        File file = new File(imagePath);
        if (!file.exists()) {
            throw new IllegalArgumentException("Image file not found: " + imagePath);
        }
        BufferedImage img = ImageIO.read(file);
        return img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
    }

    private void launchGame() {
        Professor.main(null);
    }
}
