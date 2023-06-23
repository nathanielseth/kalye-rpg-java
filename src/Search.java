import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.sound.sampled.*;
import java.io.File;

public class Search {
    private GamePanel gamePanel;
    private boolean currentlySearching;
    private Timer animationTimer;
    private int dotsCount = 0;
    private String searchingText = "Searching";
    private Clip searchClip;
    private Clip bossMusicClip;
    private Clip bossMusic2Clip;

    public Search(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        currentlySearching = false;
        loadSearchSound();
        loadBossMusic();
        loadBossMusic2();
    }

    private void loadSearchSound() {
        try {
            File soundFile = new File("media/audio/search.wav");
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(soundFile);
            searchClip = AudioSystem.getClip();
            searchClip.open(audioInputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void performSearch() {
        if (gamePanel.isInBattle()) {
            gamePanel.enableSearchButton(false);
            JOptionPane.showMessageDialog(gamePanel, "You are already in battle!");
            return;
        }
        SwingWorker<Void, Void> searchWorker = new SwingWorker<>() {
            @Override
            protected Void doInBackground() throws Exception {
                gamePanel.enableSearchButton(false);
                currentlySearching = true;
                gamePanel.setSearchLabelText("SEARCHING...");
                gamePanel.setEnemyLabelText("");
                gamePanel.showSearchingLabel(true);

                animationTimer = new Timer(500, e -> {
                    animateDots();
                    gamePanel.setSearchLabelText(searchingText);
                });
                animationTimer.setInitialDelay(0);
                animationTimer.start();
                gamePanel.revalidate();
                gamePanel.repaint();
                int searchTime;
                if (gamePanel.getLevel() >= 24) {
                    searchTime = 13000;
                    gamePanel.sariSariButton.setEnabled(false);
                } else if (gamePanel.boughtBike()) {
                    searchTime = (int) (Math.random() * 1000) + 100;
                } else {
                    searchTime = (int) (Math.random() * 5000) + 900;
                }
                Timer timer = new Timer(searchTime, new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        gamePanel.revalidate();
                        gamePanel.repaint();
                        gamePanel.showSearchingLabel(false);
                        animationTimer.stop();

                        String enemyPokeKalye = getRandomEnemyPokeKalye(gamePanel.getArea(), gamePanel.getLevel());
                        if (enemyPokeKalye == null) {
                            gamePanel.setEnemyLabelText("but nobody came.");
                            currentlySearching = false;
                            gamePanel.enableSearchButton(true);
                            gamePanel.setAreasButtonEnabled(true);
                            return;
                        }

                        gamePanel.setEnemyImage(enemyPokeKalye);
                        String dialogue;
                        if (enemyPokeKalye.length() > 8) {
                            dialogue = " A " + enemyPokeKalye + "!\n What will " + gamePanel.getPokeKalyeCustomName()
                                    + " do?";
                        } else {
                            dialogue = " " + enemyPokeKalye + " has appeared!\n What will "
                                    + gamePanel.getPokeKalyeCustomName() + " do?";
                        }
                        gamePanel.setDialogueText(dialogue);

                        currentlySearching = false;
                        gamePanel.setInBattle(true);
                    }
                });
                timer.setRepeats(false);
                timer.start();
                playSearchSound();
                return null;
            }

            @Override
            protected void done() {
                // gamePanel.enableSearchButton(true);
            }
        };
        searchWorker.execute();
    }

    private void playSearchSound() {
        try {
            if (gamePanel.getLevel() >= 24 && gamePanel.getArea().equals("Professor's Lab")) {
                playBossMusic();
            } else {
                searchClip.setFramePosition(0);
                searchClip.loop(Clip.LOOP_CONTINUOUSLY);
                while (currentlySearching && !gamePanel.isInBattle()) {
                    Thread.sleep(100);
                }
                searchClip.stop();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean isInBattle() {
        return gamePanel.isInBattle() || currentlySearching;
    }

    private String getRandomEnemyPokeKalye(String area, int level) {
        if (area.equals("Kalsada Central")) {
            if (level >= 1 && level <= 10) {
                String[] veryCommonEnemies = { "Ipis", "Daga" };
                String[] commonEnemies = { "Lamok", "Langaw", "Tuta", "Ibon" };
                String[] moderateEnemies = { "Kuting", "Manok", "Gagamba", "Butiki", "Kuto",
                        "Paro-paro", "Bubuyog",
                        "Uod", "Suso", "Butete" };
                String[] rareEnemies = { "Langgam", "Isda", "Ahas", "Higad", "Tipaklong", "Askal" };
                double random = Math.random();
                if (random < 0.08) {
                    return getRandomArrayElement(rareEnemies);
                } else if (random < 0.4) {
                    return getRandomArrayElement(moderateEnemies);
                } else if (random < 0.7) {
                    return getRandomArrayElement(commonEnemies);
                } else {
                    return getRandomArrayElement(veryCommonEnemies);
                }
            } else if (level == 11) {
                playBossMusic2();
                return "Kitty Yonarchy";
            } else {
                return null;
            }
        } else if (area.equals("Kalye West")) {
            if (level >= 1 && level <= 15) {
                String[] veryCommonEnemies = { "Askal", "Palaka" };
                String[] commonEnemies = { "Lamok", "Langaw", "Tuta", "Ibon", "Colored Sisiw" };
                String[] moderateEnemies = { "Kuting", "Manok", "Gagamba", "Tuko", "Daga", "Dagang Kanal", "Bubuyog" };
                String[] rareEnemies = { "Salagubang", "Langgam", "Palaka", "Ahas", "Higad",
                        "Tipaklong", "Ipis",
                        "Mandarangkal", "Isda", "Eagul", "Antik" };
                double random = Math.random();
                if (random < 0.05) {
                    return getRandomArrayElement(rareEnemies);
                } else if (random < 0.5) {
                    return getRandomArrayElement(moderateEnemies);
                } else if (random < 0.8) {
                    return getRandomArrayElement(commonEnemies);
                } else {
                    return getRandomArrayElement(veryCommonEnemies);
                }
            } else if (level == 16) {
                playBossMusic2();
                return "Lolong";
            } else {
                return null;
            }
        } else if (area.equals("Gedli East")) {
            if (level >= 10 && level <= 19 || level >= 21 && level <= 23) {
                String[] veryCommonEnemies = { "Askal", "Puspin" };
                String[] commonEnemies = { "Flying Ipis", "Dagang Kanal", "Bangaw",
                        "Palaka", "Tutubi", "Paro-paro" };
                String[] moderateEnemies = { "Paniki", "Higad", "Salagubang", "Manok", "Ahas", "Tipaklong",
                        "Tuko", "Bubuyog", "Mandarangkal", "Eagul" };
                String[] rareEnemies = { "Kabayo", "Daga", "Colored Sisiw" };
                String[] ultraRareEnemies = { "Ant-Man", "Big Dog", "Puspin Boots" };
                double random = Math.random();
                if (random < 0.01) {
                    return getRandomArrayElement(ultraRareEnemies);
                } else if (random < 0.05) {
                    return getRandomArrayElement(rareEnemies);
                } else if (random < 0.5) {
                    return getRandomArrayElement(moderateEnemies);
                } else if (random < 0.8) {
                    return getRandomArrayElement(commonEnemies);
                } else {
                    return getRandomArrayElement(veryCommonEnemies);
                }
            } else if (level == 20) {
                playBossMusic2();
                return "THE GOAT";
            } else {
                return null;
            }
        } else if (area.equals("Professor's Lab")) {
            if (level >= 24) {
                return "Professor Splinter";
            } else {
                return null;
            }
        }
        return null;
    }

    private String getRandomArrayElement(String[] array) {
        int index = (int) (Math.random() * array.length);
        return array[index];
    }

    private void animateDots() {
        dotsCount = (dotsCount + 1) % 5;

        StringBuilder dots = new StringBuilder();
        for (int i = 0; i < dotsCount; i++) {
            dots.append(".");
        }
        searchingText = "SEARCHING" + dots;
        int delay = 152;
        animationTimer.setDelay(delay);
    }

    private void loadBossMusic() {
        try {
            File soundFile = new File("media/audio/bossMusic.wav");
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(soundFile);
            bossMusicClip = AudioSystem.getClip();
            bossMusicClip.open(audioInputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void playBossMusic() {
        try {
            bossMusicClip.setFramePosition(0);
            Thread bossMusicThread = new Thread(() -> bossMusicClip.loop(Clip.LOOP_CONTINUOUSLY));
            bossMusicThread.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void stopBossMusic() {
        if (bossMusicClip != null && bossMusicClip.isRunning()) {
            bossMusicClip.stop();
        }
    }

    private void loadBossMusic2() {
        try {
            File soundFile = new File("media/audio/battle2.wav");
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(soundFile);
            bossMusic2Clip = AudioSystem.getClip();
            bossMusic2Clip.open(audioInputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void playBossMusic2() {
        try {
            bossMusic2Clip.setFramePosition(0);
            Thread bossMusic2Thread = new Thread(() -> bossMusic2Clip.loop(Clip.LOOP_CONTINUOUSLY));
            bossMusic2Thread.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void stopBossMusic2() {
        if (bossMusic2Clip != null && bossMusic2Clip.isRunning()) {
            bossMusic2Clip.stop();
        }
    }

    public void setBoughtBike(boolean bought) {
        setBoughtBike(bought);
    }

}