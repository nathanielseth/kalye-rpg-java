import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Search {
    private GamePanel gamePanel;
    boolean currentlySearching;
    private Timer animationTimer;
    private int dotsCount = 0;
    private String searchingText = "Searching";

    public Search(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        currentlySearching = false;
    }

    public void performSearch() {
        if (gamePanel.isInBattle()) {
            JOptionPane.showMessageDialog(gamePanel, "You are already in battle!");
            return;
        }

        SwingWorker<Void, Void> searchWorker = new SwingWorker<>() {
            @Override
            protected Void doInBackground() throws Exception {
                gamePanel.enableSearchButton(false);
                currentlySearching = true;

                gamePanel.setSearchLabelText("SEARCHING...");
                gamePanel.showSearchingLabel(true);

                animationTimer = new Timer(500, e -> {
                    animateDots();
                    gamePanel.setSearchLabelText(searchingText);
                });
                animationTimer.setInitialDelay(0);
                animationTimer.start();

                JLabel loadingLabel = new JLabel(new ImageIcon("images/searching.gif"));
                loadingLabel.setBounds(70, 10, 100, 100);
                gamePanel.add(loadingLabel);
                gamePanel.revalidate();
                gamePanel.repaint();

                int searchTime = (int) (Math.random() * 6000) + 1000;

                Timer timer = new Timer(searchTime, new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        gamePanel.remove(loadingLabel);
                        gamePanel.revalidate();
                        gamePanel.repaint();
                        gamePanel.showSearchingLabel(false);
                        animationTimer.stop();

                        String enemyPokeKalye = getRandomEnemyPokeKalye();
                        gamePanel.setEnemyImage(enemyPokeKalye);

                        String dialogue = " " + enemyPokeKalye + " has appeared!\n What will "
                                + gamePanel.getPokeKalyeName()
                                + " do?";
                        gamePanel.setDialogueText(dialogue);

                        // Check if battle is over
                        if (enemyHealthIsZero() || playerHealthIsZero()) {
                            gamePanel.enableSearchButton(true);
                        }

                        currentlySearching = false;
                        gamePanel.setInBattle(true);
                    }
                });
                timer.setRepeats(false);
                timer.start();

                return null;
            }
        };

        searchWorker.execute();
    }

    public boolean isInBattle() {
        return gamePanel.isInBattle() || currentlySearching;
    }

    private String getRandomEnemyPokeKalye() {
        int level = gamePanel.getLevel();
        if (level >= 1 && level <= 5) {
            String[] enemies = { "Ipis", "Daga", "Butiki", "Lamok", "Langaw", "Askal", "Ibon" };
            String[] rareEnemies = { "Langgam", "Puspin", "Salagubs" };

            if (Math.random() < 0.1) {
                return getRandomArrayElement(rareEnemies);
            } else {
                return getRandomArrayElement(enemies);
            }
        } else if (level >= 6 && level <= 10) {
            String[] enemies = { "Flying ipis", "Dagang Kanal", "Tuko", "Batang Kalye", "Salagubs", "Langaw" };
            String[] rareShiny = { "Shiny Daga", "Shiny Ipis", "Shiny Langaw" };

            if (Math.random() < 0.05) {
                return getRandomArrayElement(rareShiny);
            } else {
                return getRandomArrayElement(enemies);
            }
        }

        // (should not happen)
        return "ipis";
    }

    private String getRandomArrayElement(String[] array) {
        int index = (int) (Math.random() * array.length);
        return array[index];
    }

    private boolean enemyHealthIsZero() {
        int enemyCurrentHealth = gamePanel.getEnemyCurrentHealth();
        return enemyCurrentHealth <= 0;
    }

    private boolean playerHealthIsZero() {
        int playerCurrentHealth = gamePanel.getPlayerCurrentHealth();
        return playerCurrentHealth <= 0;
    }

    private void animateDots() {
        dotsCount++;
        if (dotsCount > 3) {
            dotsCount = 0;
        }

        StringBuilder dots = new StringBuilder();
        for (int i = 0; i < dotsCount; i++) {
            dots.append(".");
        }
        searchingText = "SEARCHING" + dots;
    }
}
