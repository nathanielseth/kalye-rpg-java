import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;

public class ShopPanel extends JPanel {
    private JButton backButton;
    private GamePanel gamePanel;
    private Clip musicClip;
    private JLabel pesosLabel;

    public ShopPanel(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        setBackground(new Color(82, 113, 255));
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(600, 400));
        setOpaque(true);

        JPanel shopContentPanel = new JPanel();
        shopContentPanel.setLayout(new BorderLayout());
        shopContentPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        shopContentPanel.setOpaque(false);

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setOpaque(false);

        JLabel shopLabel = new JLabel(" Manggo Lloyd's Sari-Sari Store");
        shopLabel.setFont(new Font("Courier New", Font.BOLD, 17));
        shopLabel.setForeground(Color.WHITE);
        topPanel.add(shopLabel, BorderLayout.WEST);

        pesosLabel = new JLabel("GCash:" + gamePanel.getPesos());
        pesosLabel.setFont(new Font("Impact", Font.PLAIN, 17));
        pesosLabel.setForeground(Color.WHITE);
        topPanel.add(pesosLabel, BorderLayout.EAST);

        ImageIcon shopIcon = new ImageIcon("images/gcash.png");
        JLabel shopImageLabel = new JLabel(shopIcon);
        topPanel.add(shopImageLabel);

        JPanel imagePanel = new JPanel();
        imagePanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
        imagePanel.setOpaque(false);

        topPanel.add(imagePanel, BorderLayout.CENTER);
        shopContentPanel.add(topPanel, BorderLayout.NORTH);

        JPanel itemsPanel = new JPanel(new GridLayout(3, 3, 10, 10));
        itemsPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        itemsPanel.setOpaque(false);

        String[] itemNames = {
                "Ice-y Tubig",
                "Shtick-O",
                "Coke Omsim",
                "Anti-Rabies",
                "Rugby",
                "Dengue Vaccine",
                "Bye-gon",
                "Mouse Trap",
                "Infinity Edge"
        };

        for (int i = 0; i < itemNames.length; i++) {
            JPanel itemPanel = new JPanel(new BorderLayout());
            itemPanel.setBorder(BorderFactory.createLineBorder(Color.WHITE));
            itemPanel.setOpaque(true);
            itemPanel.setBackground(new Color(255, 51, 102));

            JLabel itemLabel = new JLabel(itemNames[i]);
            itemLabel.setHorizontalAlignment(SwingConstants.CENTER);
            itemPanel.add(itemLabel, BorderLayout.NORTH);
            itemLabel.setForeground(new Color(102, 255, 51));

            JLabel itemPrice = new JLabel(((i + 1) * 10) + " pesos");
            itemPrice.setHorizontalAlignment(SwingConstants.CENTER);
            itemPrice.setForeground(Color.WHITE);
            itemPanel.add(itemPrice, BorderLayout.SOUTH);

            String imagePath = "media/images/item" + (i + 1) + ".png";
            ImageIcon itemIcon = new ImageIcon(imagePath);
            JLabel itemImage = new JLabel(itemIcon);
            itemPanel.add(itemImage, BorderLayout.CENTER);

            JButton buyButton = new JButton("Buy");
            itemPanel.add(buyButton, BorderLayout.EAST);

            if (gamePanel.getPesos() < ((i + 1) * 10)) {
                buyButton.setEnabled(false);
            } else {

                int itemIndex = i;
                buyButton.addActionListener(e -> {
                    int price = (itemIndex + 1) * 10;
                    gamePanel.setPesos(gamePanel.getPesos() - price);

                    switch (itemIndex) {
                        case 0: // Ice-y Tubig
                            gamePanel.setPlayerCurrentHealth(gamePanel.getPlayerCurrentHealth() + 10);
                            break;
                        case 1: // Shtick-O
                            gamePanel.setPlayerCurrentHealth(gamePanel.getPlayerCurrentHealth() + 30);
                            break;
                        case 2: // Coke Omsim
                            gamePanel.setPlayerCurrentHealth(gamePanel.getPlayerCurrentHealth() + 50);
                            break;
                        // Add cases for other items
                    }
                    pesosLabel.setText("GCash: " + gamePanel.getPesos() + " pesos");
                    gamePanel.updateHealthBars();
                    // buyButton.setEnabled(false);
                });
            }

            setupButtonMouseListener(buyButton);
            itemsPanel.add(itemPanel);
        }

        shopContentPanel.add(itemsPanel, BorderLayout.CENTER);

        backButton = new JButton("Back");
        backButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        backButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, backButton.getPreferredSize().height));
        setupButtonMouseListener(backButton);
        shopContentPanel.add(backButton, BorderLayout.SOUTH);
        backButton.addActionListener(e -> {
            stopMusic();
            JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
            Container contentPane = frame.getContentPane();
            contentPane.removeAll();
            contentPane.add(gamePanel);
            frame.pack();
            frame.revalidate();
            frame.repaint();
        });
        backButton.setBackground(Color.WHITE);
        backButton.setForeground(Color.BLACK);

        add(shopContentPanel, BorderLayout.CENTER);

        backButton.addActionListener(e -> {
            JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
            Container contentPane = frame.getContentPane();
            contentPane.removeAll();
            contentPane.add(gamePanel);
            frame.pack();
            frame.revalidate();
            frame.repaint();
        });

        setPreferredSize(new Dimension(600, 400));
        playMusic("media/audio/shop.wav");
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        pesosLabel.setText("GCash: " + gamePanel.getPesos() + " pesos");
    }

    private void playMusic(String musicFilePath) {
        try {
            File musicFile = new File(musicFilePath);
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(musicFile);
            musicClip = AudioSystem.getClip();
            musicClip.open(audioStream);
            musicClip.start();
        } catch (LineUnavailableException | IOException | UnsupportedAudioFileException e) {
            e.printStackTrace();
        }
    }

    private void stopMusic() {
        if (musicClip != null && musicClip.isRunning()) {
            musicClip.stop();
        }
    }

    private void setupButtonMouseListener(JButton button) {
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(new Color(102, 255, 51));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(Color.WHITE);
            }
        });
        button.setBackground(Color.WHITE);
        button.setForeground(Color.BLACK);
    }
}
