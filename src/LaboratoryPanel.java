import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;

public class LaboratoryPanel extends JPanel {
    private JButton backButton;
    private GamePanel gamePanel;
    private Clip musicClip;
    private Clip hoverSoundClip;
    private Clip buttonClickSoundClip;
    private JLabel pesosLabel;
    private JPanel itemsPanel;
    private JScrollPane scrollPane;

    public LaboratoryPanel(GamePanel gamePanel) {
        preloadSounds();
        this.gamePanel = gamePanel;
        setBackground(new Color(255, 51, 102));
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(600, 400));
        setOpaque(true);

        JPanel labContentPanel = new JPanel();
        labContentPanel.setLayout(new BorderLayout());
        labContentPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        labContentPanel.setOpaque(false);

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setOpaque(false);

        JLabel labLabel = new JLabel("Laboratory");
        labLabel.setFont(new Font("Courier New", Font.BOLD, 17));
        labLabel.setForeground(Color.WHITE);
        topPanel.add(labLabel, BorderLayout.WEST);

        pesosLabel = new JLabel("GCash:" + gamePanel.getPesos());
        pesosLabel.setFont(new Font("Impact", Font.PLAIN, 17));
        pesosLabel.setForeground(Color.WHITE);
        topPanel.add(pesosLabel, BorderLayout.EAST);

        ImageIcon labIcon = new ImageIcon("images/pokedex.png");
        JLabel labImageLabel = new JLabel(labIcon);
        topPanel.add(labImageLabel);

        JPanel imagePanel = new JPanel();
        imagePanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
        imagePanel.setOpaque(false);

        topPanel.add(imagePanel, BorderLayout.CENTER);
        labContentPanel.add(topPanel, BorderLayout.NORTH);

        itemsPanel = new JPanel(new GridLayout(0, 3, 10, 10));
        itemsPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        itemsPanel.setOpaque(false);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setPreferredSize(new Dimension(600, 300));
        scrollPane.setViewportView(createPokeKalyePanel());

        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BorderLayout());
        contentPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        contentPanel.setOpaque(false);

        JLabel shopLabel = new JLabel(" Professor Kalye's Laboratory");
        shopLabel.setFont(new Font("Courier New", Font.BOLD, 17));
        shopLabel.setForeground(Color.WHITE);
        topPanel.add(shopLabel, BorderLayout.WEST);

        pesosLabel = new JLabel("PokeKalye Caught: " + gamePanel.getPesos());
        pesosLabel.setFont(new Font("Impact", Font.PLAIN, 17));
        pesosLabel.setForeground(Color.WHITE);
        topPanel.add(pesosLabel, BorderLayout.EAST);

        ImageIcon shopIcon = new ImageIcon("images/gcash.png");
        JLabel shopImageLabel = new JLabel(shopIcon);
        topPanel.add(shopImageLabel);

        contentPanel.add(topPanel, BorderLayout.NORTH);
        contentPanel.add(scrollPane, BorderLayout.CENTER);

        backButton = new JButton("Back");
        backButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        backButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, backButton.getPreferredSize().height));
        setupButtonMouseListener(backButton);
        contentPanel.add(backButton, BorderLayout.SOUTH);
        backButton.setBackground(Color.WHITE);
        backButton.setForeground(Color.BLACK);

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

        add(contentPanel, BorderLayout.CENTER);
        setPreferredSize(new Dimension(600, 400));
        playMusic("media/audio/professorTheme.wav");
    }

    private JPanel createPokeKalyePanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(0, 3, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        panel.setOpaque(false);

        String[] pokekalyes = { "Kuting", "Puspin", "Puspin Boots", "Tuta", "Askal", "Big Dog", "Langgam", "Antik",
                "Ant-Man",
                "Ipis", "Flying Ipis", "Daga", "Dagang Kanal", "Lamok", "Butiki", "Ibon", "Colored Sisiw", "Salagubang",
                "Langaw", "Bangaw", "Tuko", "Manok", "Gagamba", "Paniki", "Tutubi", "Ahas", "Paro-paro", "Higad",
                "Tipaklong", "Mandarangkal", "Kabayo", "Palaka", "Kuto", "Bubuyog", "Tribal Kip",
                "Lolong", "THE GOAT" };

        for (String pokeKalye : pokekalyes) {
            JPanel itemPanel = new JPanel(new BorderLayout());
            itemPanel.setBorder(BorderFactory.createLineBorder(Color.WHITE));
            itemPanel.setOpaque(true);
            itemPanel.setBackground(new Color(240, 221, 226));

            JLabel itemLabel = new JLabel(pokeKalye);
            itemLabel.setHorizontalAlignment(SwingConstants.CENTER);
            itemPanel.add(itemLabel, BorderLayout.NORTH);

            ImageIcon itemIcon = new ImageIcon("media/images/" + pokeKalye + "2.png");
            JLabel itemImage = new JLabel(itemIcon);
            itemImage.setHorizontalAlignment(SwingConstants.CENTER);
            itemPanel.add(itemImage, BorderLayout.CENTER);

            JButton detailsButton = new JButton("Details");
            detailsButton.setHorizontalAlignment(SwingConstants.CENTER);
            detailsButton.setForeground(Color.BLACK);
            detailsButton.setBackground(Color.WHITE);
            detailsButton.setBorder(BorderFactory.createLineBorder(Color.WHITE));
            detailsButton.setFocusPainted(false);
            detailsButton.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseEntered(java.awt.event.MouseEvent evt) {
                    playHoverSound();
                    detailsButton.setBackground(new Color(102, 255, 51));
                }

                public void mouseExited(java.awt.event.MouseEvent evt) {
                    detailsButton.setBackground(Color.WHITE);
                }
            });
            detailsButton.setOpaque(true);
            itemPanel.add(detailsButton, BorderLayout.SOUTH);

            String itemDescription = "Lore of " + pokeKalye;
            itemPanel.setToolTipText(itemDescription);

            detailsButton.addActionListener((ActionEvent e) -> {
                playButtonClickSound();
                JOptionPane.showMessageDialog(this, itemDescription, pokeKalye + " Details",
                        JOptionPane.INFORMATION_MESSAGE);
            });
            panel.add(itemPanel);
        }
        return panel;
    }

    private void preloadSounds() {
        try {
            AudioInputStream audioInputStream = AudioSystem
                    .getAudioInputStream(new File("media/audio/hover.wav"));
            hoverSoundClip = AudioSystem.getClip();
            hoverSoundClip.open(audioInputStream);

            audioInputStream = AudioSystem.getAudioInputStream(new File("media/audio/click.wav"));
            buttonClickSoundClip = AudioSystem.getClip();
            buttonClickSoundClip.open(audioInputStream);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    private void playMusic(String filepath) {
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(filepath));
            musicClip = AudioSystem.getClip();
            musicClip.open(audioInputStream);
            musicClip.loop(Clip.LOOP_CONTINUOUSLY);
            musicClip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    private void stopMusic() {
        if (musicClip != null && musicClip.isRunning()) {
            musicClip.stop();
            musicClip.close();
        }
    }

    private void playHoverSound() {
        if (hoverSoundClip != null && !hoverSoundClip.isRunning()) {
            hoverSoundClip.setFramePosition(0);
            hoverSoundClip.start();
        }
    }

    private void playButtonClickSound() {
        if (buttonClickSoundClip != null && !buttonClickSoundClip.isRunning()) {
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
    }

    public void refreshShopPanel() {
        pesosLabel.setText("PokeKalye Caught: " + gamePanel.getPesos());
    }
}
