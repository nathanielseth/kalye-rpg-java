import javax.sound.sampled.*;
import javax.swing.*;
import javax.swing.border.Border;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainMenu extends JFrame {

  private List<Clip> audioClips;
  private JLabel randomStringLabel;

  private class InitializationWorker extends SwingWorker<Void, Void> {
    @Override
    protected Void doInBackground() throws Exception {
      preloadAudio("media/audio/hover.wav");
      preloadAudio("media/audio/click.wav");
      preloadAudio("media/audio/nilalangaw.wav");
      playSound("media/audio/nilalangaw.wav", true);
      return null;
    }

    @Override
    protected void done() {
      startBlinkingAnimation();
    }
  }

  private final String[] randomStrings = {
      "Best Game !", "Arats na !", "by nathanielseth.dev", "Ingat sa RABIES!",
      "Speedrun Tatum Jersey%", "Waksploit pa more!", "Java Swing Edition",
      "mobile coming soon...?", "Kalye Grind !", "Save Queensrow !", "i h8 java swing",
      "Ako ay Nilalangaw !", "Best Game...!?"
  };

  public MainMenu() {
    String iconPath = "media/images/KalyeRPG.png";
    ImageIcon icon = new ImageIcon(iconPath);
    setIconImage(icon.getImage());
    setTitle("KalyeRPG");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setSize(600, 400);
    setLocationRelativeTo(null);

    JPanel contentPane = new JPanel(new BorderLayout());
    contentPane.setBackground(new Color(82, 113, 255));
    setContentPane(contentPane);

    Border frameBorder = BorderFactory.createLineBorder(Color.WHITE, 1);
    contentPane.setBorder(frameBorder);

    ImageIcon logoImage = new ImageIcon("media/images/pokekals.png");
    JLabel logoLabel = new JLabel(logoImage);
    logoLabel.setHorizontalAlignment(SwingConstants.CENTER);

    randomStringLabel = new JLabel(getRandomString());
    randomStringLabel.setFont(new Font("Montserrat", Font.BOLD + Font.ITALIC, 16));
    randomStringLabel.setForeground(Color.WHITE);
    randomStringLabel.setHorizontalAlignment(SwingConstants.CENTER);
    randomStringLabel.setVerticalAlignment(SwingConstants.CENTER);
    randomStringLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 5, 0));

    JButton playButton = createButton("START GAME");
    JButton aboutButton = createButton("ABOUT");
    JButton quitButton = createButton("QUIT");

    JPanel buttonPanel = new JPanel(new GridBagLayout());
    buttonPanel.setBackground(new Color(82, 113, 255));
    buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 20, 0));

    GridBagConstraints gbc = new GridBagConstraints();
    gbc.fill = GridBagConstraints.HORIZONTAL;
    gbc.gridx = 0;
    gbc.gridy = 0;
    gbc.weighty = 1;
    gbc.insets = new Insets(10, 0, 10, 0);
    buttonPanel.add(playButton, gbc);

    gbc.gridy = 1;
    buttonPanel.add(aboutButton, gbc);

    gbc.gridy = 2;
    buttonPanel.add(quitButton, gbc);

    JPanel centerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
    centerPanel.setBackground(new Color(82, 113, 255));
    centerPanel.add(buttonPanel);

    contentPane.add(logoLabel, BorderLayout.NORTH);
    contentPane.add(randomStringLabel, BorderLayout.CENTER);
    contentPane.add(centerPanel, BorderLayout.SOUTH);

    setVisible(true);
    audioClips = new ArrayList<>();
    InitializationWorker worker = new InitializationWorker();
    worker.execute();
  }

  private void startBlinkingAnimation() {
    Timer timer = new Timer(300, new ActionListener() {
      private boolean isVisible = true;

      @Override
      public void actionPerformed(ActionEvent e) {
        randomStringLabel.setVisible(isVisible);
        isVisible = !isVisible;
      }
    });
    timer.start();
  }

  private JButton createButton(String text) {
    JButton button = new JButton(text);
    Dimension buttonSize = new Dimension(200, 30);
    button.setPreferredSize(buttonSize);

    button.setBackground(Color.WHITE);
    button.setForeground(Color.BLACK);
    button.setFont(new Font("Montserrat", Font.BOLD, 14));
    button.setFocusPainted(false);

    button.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseEntered(MouseEvent e) {
        button.setBackground(new Color(102, 255, 51));
        button.setBorder(BorderFactory.createLineBorder(new Color(255, 51, 102), 4));
        playSound("media/audio/hover.wav", false);
      }

      @Override
      public void mouseExited(MouseEvent e) {
        button.setBackground(Color.WHITE);
        button.setBorder(BorderFactory.createEmptyBorder());
      }
    });

    button.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        playSound("media/audio/click.wav", false);
        if (text.equals("START GAME")) {
          stopMusic();
          Professor.main(null);
          dispose();
        } else if (text.equals("ABOUT")) {
          openWebpage("https://github.com/nathanielseth/KalyeRPG");
        } else if (text.equals("QUIT")) {
          System.exit(0);
        }
      }
    });
    return button;
  }

  private void openWebpage(String url) {
    try {
      Desktop.getDesktop().browse(new URI(url));
    } catch (IOException | URISyntaxException e) {
      e.printStackTrace();
    }
  }

  private void preloadAudio(String filePath) {
    try {
      AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(filePath));
      Clip clip = AudioSystem.getClip();
      clip.open(audioInputStream);
      audioClips.add(clip);
    } catch (IOException | UnsupportedAudioFileException | LineUnavailableException e) {
      e.printStackTrace();
    }
  }

  private void playSound(String filePath, boolean loop) {
    new Thread(() -> {
      try {
        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(filePath));
        Clip clip = AudioSystem.getClip();
        clip.open(audioInputStream);
        if (loop) {
          clip.loop(Clip.LOOP_CONTINUOUSLY);
        } else {
          clip.start();
        }
        audioClips.add(clip);
      } catch (IOException | UnsupportedAudioFileException | LineUnavailableException e) {
        e.printStackTrace();
      }
    }).start();
  }

  private void stopMusic() {
    for (Clip clip : audioClips) {
      clip.stop();
    }
  }

  private String getRandomString() {
    Random random = new Random();
    int index = random.nextInt(randomStrings.length);
    return randomStrings[index];
  }

  public static void main(String[] args) {
    SwingUtilities.invokeLater(new Runnable() {
      @Override
      public void run() {
        new MainMenu();
      }
    });
  }
}
