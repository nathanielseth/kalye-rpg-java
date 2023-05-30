import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;

public class Search {
    private GamePanel gamePanel;
    private boolean inBattle;

    public Search(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        inBattle = false;
    }

    public void performSearch() {
        if (gamePanel.isInBattle()) {
            JOptionPane.showMessageDialog(gamePanel, "You are already in battle!");
            return;
        }

        SwingWorker<Void, Void> searchWorker = new SwingWorker<>() {
            @Override
            protected Void doInBackground() throws Exception {
                gamePanel.setInBattle(true);
                gamePanel.enableSearchButton(false);

                String enemyPokeKalye = getRandomEnemyPokeKalye();

                SwingUtilities.invokeLater(() -> gamePanel.setEnemyImage(enemyPokeKalye));

                String dialogue = " " + enemyPokeKalye + " has appeared!\n What will " + gamePanel.getPokeKalyeName()
                        + " do?";
                SwingUtilities.invokeLater(() -> gamePanel.setDialogueText(dialogue));

                // Check if battle is over
                if (enemyHealthIsZero() || playerHealthIsZero()) {
                    gamePanel.enableSearchButton(true);
                }

                gamePanel.setInBattle(false);
                return null;
            }
        };

        searchWorker.execute();
    }

    public boolean isInBattle() {
        return inBattle;
    }

    private String getRandomEnemyPokeKalye() {
        int level = gamePanel.getLevel();
        if (level >= 1 && level <= 5) {
            String[] enemies = { "Ipis", "Daga", "Butiki", "Lamok", "Langaw", "Askal", "Ibon" };
            String[] rareEnemies = { "Langgam", "Puspin", "Salagubang" };

            if (Math.random() < 0.1) {
                return getRandomArrayElement(rareEnemies);
            } else {
                return getRandomArrayElement(enemies);
            }
        } else if (level >= 6 && level <= 10) {
            String[] enemies = { "Flying ipis", "Dagang Kanal", "Tuko", "Batang Kalye", "Salagubang", "Langaw" };
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
}
