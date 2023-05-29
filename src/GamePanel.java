import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GamePanel extends JPanel {
    private int playerLevel;

    private JLabel playerLabel;
    private JProgressBar playerHealthBar;
    private JLabel enemyLabel;
    private JProgressBar enemyHealthBar;
    private JTextArea dialogueArea;
    private JButton movesButton;
    private JButton searchButton;
    private JButton healButton;
    private JButton sariSariButton; // Updated button name
    private String selectedPokeKalye;
    private String playerName;

    public GamePanel(String playerName, String selectedPokeKalye) {
        this.playerName = playerName;
        this.selectedPokeKalye = selectedPokeKalye;
        playerLevel = 1;

        // Set layout manager for the panel
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(600, 400));

        // Create and add GUI components
        JPanel battlePanel = new JPanel();
        battlePanel.setLayout(new BorderLayout());
        battlePanel.setBackground(new Color(82, 113, 255));

        JPanel playerPanel = new JPanel();
        playerPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        playerPanel.setBackground(new Color(82, 113, 255));
        playerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        playerLabel = new JLabel(selectedPokeKalye);
        playerLabel.setForeground(Color.WHITE); // Set label text color to white
        playerHealthBar = new JProgressBar(0, getMaxHealth(playerLevel));
        playerHealthBar.setValue(10);
        playerHealthBar.setForeground(new Color(102, 255, 51)); // Set HP bar color to #66ff33

        playerPanel.add(playerLabel);
        playerPanel.add(playerHealthBar);

        JPanel imagePanel = new JPanel();
        imagePanel.setLayout(null); // Set null layout for absolute positioning
        imagePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Add selectedPokeKalye image in the image panel
        JLabel yourPokeKalyeImage = new JLabel(new ImageIcon("images/" + selectedPokeKalye + "2.png"));
        yourPokeKalyeImage.setBounds(70, 10, 100, 100);

        imagePanel.add(yourPokeKalyeImage);

        JPanel enemyPanel = new JPanel();
        enemyPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        enemyPanel.setBackground(new Color(82, 113, 255));
        enemyPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 20));

        enemyLabel = new JLabel("Enemy");
        enemyLabel.setForeground(Color.WHITE);
        enemyHealthBar = new JProgressBar();
        enemyHealthBar.setForeground(new Color(102, 255, 51)); // Set HP bar color to #66ff33

        // Add placeholder image above enemy health bar
        JLabel enemyImageLabel = new JLabel(new ImageIcon("images/empty.png"));
        enemyImageLabel.setBounds(260, 120, 100, 100);
        imagePanel.add(enemyImageLabel);
        enemyPanel.setBackground(new Color(255, 51, 102));
        enemyPanel.add(enemyHealthBar);
        enemyPanel.add(enemyLabel);

        battlePanel.add(playerPanel, BorderLayout.NORTH);
        battlePanel.add(imagePanel, BorderLayout.CENTER);
        battlePanel.add(enemyPanel, BorderLayout.SOUTH);

        dialogueArea = new JTextArea(" What will " + selectedPokeKalye + " do?");
        dialogueArea.setEditable(false);
        dialogueArea.setFont(new Font("Monospaced", Font.PLAIN, 12));

        JScrollPane dialogueScrollPane = new JScrollPane(dialogueArea);
        dialogueScrollPane.setPreferredSize(new Dimension(170, getHeight()));

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new GridLayout(1, 4, 10, 0)); // Updated grid layout to accommodate the new button
        buttonsPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        buttonsPanel.setBackground(Color.DARK_GRAY);

        movesButton = new JButton("Moves");
        searchButton = new JButton("Search");
        healButton = new JButton("Heal");
        sariSariButton = new JButton("Sari-Sari"); // Updated button name

        // Set button colors
        movesButton.setBackground(Color.WHITE);
        searchButton.setBackground(Color.WHITE);
        healButton.setBackground(Color.WHITE);
        sariSariButton.setBackground(Color.WHITE);

        // Set hover colors
        movesButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                movesButton.setBackground(new Color(102, 255, 51));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                movesButton.setBackground(Color.WHITE);
            }
        });

        searchButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                searchButton.setBackground(new Color(102, 255, 51));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                searchButton.setBackground(Color.WHITE);
            }
        });

        healButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                healButton.setBackground(new Color(102, 255, 51));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                healButton.setBackground(Color.WHITE);
            }
        });

        sariSariButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                sariSariButton.setBackground(new Color(102, 255, 51));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                sariSariButton.setBackground(Color.WHITE);
            }
        });

        buttonsPanel.add(movesButton);
        buttonsPanel.add(searchButton);
        buttonsPanel.add(healButton);
        buttonsPanel.add(sariSariButton); // Add sariSariButton to the panel

        add(battlePanel, BorderLayout.CENTER);
        add(dialogueScrollPane, BorderLayout.WEST);
        add(buttonsPanel, BorderLayout.SOUTH);

        // Add action listeners to buttons
        movesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showMovesDialog();
            }
        });

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchForPokemon();
            }
        });

        healButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                healPokemon();
            }
        });

        sariSariButton.addActionListener(new ActionListener() { // Add ActionListener for sariSariButton
            @Override
            public void actionPerformed(ActionEvent e) {
                openShopPanel(); // Call the method to open the shop panel
            }
        });
    }

    // Helper method to calculate the maximum health based on the player's level
    private int getMaxHealth(int level) {
        if (level >= 5) {
            return 5 + (level - 5) * 20;
        }
        return level * 5;
    }

    // Method to handle the "Moves" button click
    private void showMovesDialog() {
        // Implement the moves dialog and move logic here
    }

    // Method to handle the "Search" button click
    private void searchForPokemon() {
        // Implement the random encounter logic here
    }

    // Method to handle the "Heal" button click
    private void healPokemon() {
        // Implement the heal logic here
    }

    // Method to open the shop panel
    private void openShopPanel() {
        ShopPanel shopPanel = new ShopPanel();
        shopPanel.setVisible(true);
    }

    public void goBackToMainPanel() {
        // Switch back to the main panel
        ((CardLayout) getParent().getLayout()).show(getParent(), "GamePanel");
    }
}
