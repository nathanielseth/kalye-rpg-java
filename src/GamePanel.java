import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GamePanel extends JPanel {
    private String playerName;
    private String selectedPokeKalye;
    private int playerLevel;
    private int playerHealth;
    private int enemyHealth;

    private JLabel playerLabel;
    private JProgressBar playerHealthBar;
    private JLabel enemyLabel;
    private JProgressBar enemyHealthBar;
    private JTextArea dialogueArea;
    private JButton movesButton;
    private JButton searchButton;
    private JButton healButton;
    private JButton quitButton;

    public GamePanel(String playerName, String selectedPokeKalye) {
        // Initialize player and enemy data
        this.playerName = playerName;
        this.selectedPokeKalye = selectedPokeKalye;
        playerLevel = 1;
        playerHealth = 5;
        enemyHealth = 0;

        // Set layout manager for the panel
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(600, 400));

        // Create and add GUI components
        JPanel battlePanel = new JPanel();
        battlePanel.setLayout(new BorderLayout());

        JPanel playerPanel = new JPanel();
        playerPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        playerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        playerLabel = new JLabel(playerName + "'s " + selectedPokeKalye);
        playerHealthBar = new JProgressBar(0, getMaxHealth(playerLevel));
        playerHealthBar.setValue(playerHealth);

        playerPanel.add(playerLabel);
        playerPanel.add(playerHealthBar);

        JPanel enemyPanel = new JPanel();
        enemyPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        enemyPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        enemyLabel = new JLabel();
        enemyHealthBar = new JProgressBar();

        enemyPanel.add(enemyHealthBar);
        enemyPanel.add(enemyLabel);

        battlePanel.add(playerPanel, BorderLayout.NORTH);
        battlePanel.add(enemyPanel, BorderLayout.SOUTH);

        dialogueArea = new JTextArea("What will " + selectedPokeKalye + " do?");
        dialogueArea.setEditable(false);
        dialogueArea.setFont(new Font("Monospaced", Font.PLAIN, 12));

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new GridLayout(1, 3, 10, 0));
        buttonsPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        movesButton = new JButton("Moves");
        searchButton = new JButton("Search");
        healButton = new JButton("Heal");
        quitButton = new JButton("Quit");

        buttonsPanel.add(movesButton);
        buttonsPanel.add(searchButton);
        buttonsPanel.add(healButton);
        buttonsPanel.add(quitButton); // Add quitButton to the panel

        add(battlePanel, BorderLayout.CENTER);
        add(dialogueArea, BorderLayout.WEST);
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

        quitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
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
}
