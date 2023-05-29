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
            // Display a message that the player is already in battle
            JOptionPane.showMessageDialog(gamePanel, "You are already in battle!");
            return;
        }

        // Start the search in a separate thread
        SwingWorker<Void, Void> searchWorker = new SwingWorker<>() {
            @Override
            protected Void doInBackground() throws Exception {
                gamePanel.setInBattle(true);
                gamePanel.enableSearchButton(false);

                // Randomly select an enemy PokeKalye
                String enemyPokeKalye = getRandomEnemyPokeKalye();

                // Set the enemy's image based on the selected PokeKalye
                SwingUtilities.invokeLater(() -> gamePanel.setEnemyImage(enemyPokeKalye));

                // Display the enemy's appearance in the dialogue area
                String dialogue = " " + enemyPokeKalye + " has appeared!\n What will " + gamePanel.getPokeKalyeName()
                        + " do?";
                SwingUtilities.invokeLater(() -> gamePanel.setDialogueText(dialogue));

                // Rest of the battle logic remains the same...

                // Check if the battle is over (enemy defeated or player defeated)
                if (enemyHealthIsZero() || playerHealthIsZero()) {
                    // Enable the search button if the battle is over
                    gamePanel.enableSearchButton(true);
                }

                gamePanel.setInBattle(false); // Set inBattle to false when the battle loop ends
                return null;
            }
        };

        searchWorker.execute();
    }

    public boolean isInBattle() {
        return inBattle;
    }

    private String getRandomEnemyPokeKalye() {
        // Randomly select an enemy PokeKalye based on level
        int level = gamePanel.getLevel();
        if (level >= 1 && level <= 5) {
            String[] enemies = { "Ipis", "Daga", "Askal", "Butiki", "Lamok", "Langaw", "Langgam", "Puspin",
                    "Salagubang" };
            return getRandomArrayElement(enemies);
        } else if (level >= 6 && level <= 10) {
            String[] enemies = { "Flying ipis", "Dagang Kanal", "Tuko", "Batang Kalye", "Salagubang", "Langaw" };
            return getRandomArrayElement(enemies);
        }

        // Default case (should not happen)
        return "ipis";
    }

    private String getRandomArrayElement(String[] array) {
        int index = (int) (Math.random() * array.length);
        return array[index];
    }

    private boolean enemyHealthIsZero() {
        // Check if the enemy's health reaches 0
        int enemyCurrentHealth = gamePanel.getEnemyCurrentHealth();
        return enemyCurrentHealth <= 0;
    }

    private boolean playerHealthIsZero() {
        // Check if the player's health reaches 0
        int playerCurrentHealth = gamePanel.getPlayerCurrentHealth();
        return playerCurrentHealth <= 0;
    }
}
