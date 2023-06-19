import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;

public class PetShopPanel extends JPanel {
    private JButton backButton;
    private GamePanel gamePanel;
    private Clip musicClip;
    private Clip hoverSoundClip;
    private Clip buySoundClip;
    private Clip buttonClickSoundClip;
    private JLabel pesosLabel;
    private JPanel itemsPanel;

    public PetShopPanel(GamePanel gamePanel) {
        preloadSounds();
        this.gamePanel = gamePanel;
        setBackground(new Color(255, 51, 102));
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(600, 400));
        setOpaque(true);

        JPanel shopContentPanel = new JPanel();
        shopContentPanel.setLayout(new BorderLayout());
        shopContentPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        shopContentPanel.setOpaque(false);

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setOpaque(false);

        JLabel shopLabel = new JLabel(" MALU'S PET SHOP");
        shopLabel.setFont(new Font("Impact", Font.PLAIN, 18));
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

        itemsPanel = new JPanel(new GridLayout(3, 3, 10, 10));
        itemsPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        itemsPanel.setOpaque(false);

        refreshShopPanel();

        String[] itemNames = {
                "Sugar Cube",
                "Catnip",
                "Pedigree",
                "Water Bowl",
                "Vitamins",
                "Litter Box"
        };

        String[] itemDescriptions = {
                "Paborito ni Langgam!",
                "Paborito ni Puspin!",
                "Paborito ni Askal!",
                "Keep your pet hydrated!",
                "Increase your pet's IMMUNITY!",
                "The Litter Box that will literally save your life!"
        };

        for (int i = 0; i < itemNames.length; i++) {
            JPanel itemPanel = new JPanel(new BorderLayout());
            itemPanel.setBorder(BorderFactory.createLineBorder(Color.WHITE));
            itemPanel.setOpaque(true);
            itemPanel.setBackground(new Color(240, 221, 226));

            JLabel itemLabel = new JLabel(itemNames[i]);
            itemLabel.setHorizontalAlignment(SwingConstants.CENTER);
            itemPanel.add(itemLabel, BorderLayout.NORTH);

            int price = getItemPrice(i);
            JLabel itemPrice = new JLabel(price + " pesos");
            itemPrice.setHorizontalAlignment(SwingConstants.CENTER);
            itemPanel.add(itemPrice, BorderLayout.SOUTH);

            String imagePath = "media/images/petItem" + (i + 1) + ".png";
            ImageIcon itemIcon = new ImageIcon(imagePath);
            JLabel itemImage = new JLabel(itemIcon);
            itemPanel.add(itemImage, BorderLayout.CENTER);

            JButton buyButton = new JButton("BUY");
            itemPanel.add(buyButton, BorderLayout.EAST);

            String itemDescription = itemDescriptions[i];
            itemPanel.setToolTipText(itemDescription);

            if (gamePanel.getPesos() < price || !canAffordAnyItem(gamePanel.getPesos(), itemNames)) {
                buyButton.setEnabled(false);
            } else {
                int itemIndex = i;

                buyButton.addActionListener(e -> {
                    playBuySound();
                    gamePanel.setPesos(gamePanel.getPesos() - price);

                    switch (itemIndex) {
                        case 0: // Sugar Cube
                            if (gamePanel.getPokeKalyeName().equals("Langgam") ||
                                    gamePanel.getPokeKalyeName().equals("Antik") ||
                                    gamePanel.getPokeKalyeName().equals("Ant-Man")) {
                                gamePanel.incrementMaxHealth(5);
                                gamePanel.updateHealthBars();
                                refreshShopPanel();
                            }
                            break;
                        case 1: // Catnip
                            if (gamePanel.getPokeKalyeName().equals("Puspin") ||
                                    gamePanel.getPokeKalyeName().equals("Puspin Boots")) {
                                gamePanel.incrementMaxHealth(8);
                                gamePanel.updateHealthBars();
                                refreshShopPanel();
                            }
                            break;
                        case 2: // Pedigree
                            if (gamePanel.getPokeKalyeName().equals("Askal") ||
                                    gamePanel.getPokeKalyeName().equals("Big Dog")) {
                                gamePanel.incrementMaxHealth(10);
                                gamePanel.updateHealthBars();
                                refreshShopPanel();
                            }
                            break;
                        case 3: // Water Bowl
                            gamePanel.setBoughtWaterBowl(true);
                            refreshShopPanel();
                            break;
                        case 4: // Vitamins
                            gamePanel.setBoughtVitamins(true);
                            refreshShopPanel();
                            break;
                        case 5: // Litter Box
                            gamePanel.incrementPlayerExtraLives();
                            refreshShopPanel();
                            break;
                    }

                    pesosLabel.setText("GCash: " + gamePanel.getPesos() + " pesos");
                    gamePanel.updateHealthBars();
                    refreshShopPanel();
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
            gamePanel.sariSariButton.setBackground(Color.WHITE);
            playButtonClickSound();
            stopMusic();
            gamePanel.setShopOpen(false);
            gamePanel.resumeMusic();
            JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
            Container contentPane = frame.getContentPane();
            contentPane.removeAll();
            contentPane.add(gamePanel);
            frame.pack();
            frame.revalidate();
            frame.repaint();
            refreshShopPanel();
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
        playMusic("media/audio/petShop.wav");
    }

    public int getItemPrice(int itemIndex) {
        switch (itemIndex) {
            case 0: // Sugar Cube
            case 1: // Catnip
            case 2: // Pedigree
                return 30;
            case 3: // Water Bowl
                return 50;
            case 4: // Vitamins
                return 60;
            case 5: // Litter Box
                return 100;
            default:
                return 0;
        }
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        pesosLabel.setText("GCash: " + gamePanel.getPesos() + " pesos");
    }

    private void preloadSounds() {
        try {
            hoverSoundClip = preloadClip(new File("media/audio/hover.wav"));
            buySoundClip = preloadClip(new File("media/audio/buy.wav"));
            buttonClickSoundClip = preloadClip(new File("media/audio/click.wav"));
        } catch (IOException | LineUnavailableException | UnsupportedAudioFileException e) {
            e.printStackTrace();
        }
    }

    private Clip preloadClip(File audioFile)
            throws IOException, LineUnavailableException, UnsupportedAudioFileException {
        AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
        Clip clip = AudioSystem.getClip();
        clip.open(audioStream);
        return clip;
    }

    private void playMusic(String musicFilePath) {
        try {
            File musicFile = new File(musicFilePath);
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(musicFile);
            musicClip = AudioSystem.getClip();
            musicClip.open(audioStream);
            musicClip.loop(Clip.LOOP_CONTINUOUSLY);
        } catch (LineUnavailableException | IOException | UnsupportedAudioFileException e) {
            e.printStackTrace();
        }
    }

    private void stopMusic() {
        if (musicClip != null && musicClip.isRunning()) {
            musicClip.stop();
        }
    }

    private void playHoverSound() {
        if (hoverSoundClip != null) {
            hoverSoundClip.setFramePosition(0);
            hoverSoundClip.start();
        }
    }

    private void playBuySound() {
        if (buySoundClip != null) {
            buySoundClip.setFramePosition(0);
            buySoundClip.start();
        }
    }

    private void playButtonClickSound() {
        if (buttonClickSoundClip != null) {
            buttonClickSoundClip.setFramePosition(0);
            buttonClickSoundClip.start();
        }
    }

    private void setupButtonMouseListener(JButton button) {
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                if (button.isEnabled()) {
                    button.setBackground(new Color(102, 255, 51));
                    playHoverSound();
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if (button.isEnabled()) {
                    button.setBackground(Color.WHITE);
                }
            }
        });
        button.setBackground(Color.WHITE);
        button.setForeground(Color.BLACK);
    }

    private void refreshShopPanel() {
        for (Component component : itemsPanel.getComponents()) {
            if (component instanceof JPanel) {
                JPanel itemPanel = (JPanel) component;
                JButton buyButton = (JButton) itemPanel.getComponent(3);
                int itemIndex = itemsPanel.getComponentZOrder(itemPanel);

                if ((gamePanel.getPesos() < ((itemIndex + 1) * 10))) {
                    buyButton.setEnabled(false);
                } else {
                    buyButton.setEnabled(true);
                }
            }
        }

        pesosLabel.setText("GCash: " + gamePanel.getPesos() + " pesos");
        repaint();
    }

    private boolean canAffordAnyItem(int currentPesos, String[] itemNames) {
        for (int i = 0; i < itemNames.length; i++) {
            if (currentPesos >= getItemPrice(i)) {
                return true;
            }
        }
        return false;
    }
}
