import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class SettingsPanel extends JFrame {
    private GamePanel gamePanel;
    private JToggleButton musicToggle;
    private JToggleButton sfxToggle;
    private JComboBox<String> graphicsComboBox;
    private JTextField cheatCodesTextField;
    private JButton backButton;

    public SettingsPanel(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        setTitle("Settings");
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(300, 300));
        getContentPane().setBackground(new Color(82, 113, 255));

        JPanel settingsPanel = new JPanel();
        settingsPanel.setLayout(new GridBagLayout());
        settingsPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        settingsPanel.setBackground(new Color(82, 113, 255));

        JLabel settingsLabel = new JLabel("SETTINGS", SwingConstants.CENTER);
        settingsLabel.setFont(new Font("Impact", Font.BOLD, 39));
        settingsLabel.setForeground(Color.WHITE);

        JLabel musicLabel = new JLabel("Music");
        musicToggle = createToggleButton("On");
        musicToggle.setBackground(new Color(102, 255, 51));

        JLabel sfxLabel = new JLabel("SFX");
        sfxToggle = createToggleButton("On");
        sfxToggle.setBackground(new Color(102, 255, 51));

        JLabel graphicsLabel = new JLabel("Graphics");
        graphicsComboBox = new JComboBox<>(new String[] { "Normal", "Sprite" });

        JLabel cheatCodesLabel = new JLabel("Cheat Codes");
        cheatCodesTextField = new JTextField();
        cheatCodesTextField.setDocument(new JTextFieldLimit(10));
        Font labelFont = new Font("Courier New", Font.PLAIN, 16);
        Color labelColor = Color.WHITE;

        settingsLabel.setFont(new Font("Impact", Font.PLAIN, 41));
        settingsLabel.setForeground(labelColor);
        musicLabel.setFont(labelFont);
        musicLabel.setForeground(labelColor);
        sfxLabel.setFont(labelFont);
        sfxLabel.setForeground(labelColor);
        graphicsLabel.setFont(labelFont);
        graphicsLabel.setForeground(labelColor);
        cheatCodesLabel.setFont(labelFont);
        cheatCodesLabel.setForeground(labelColor);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(0, 0, 10, 0);
        settingsPanel.add(settingsLabel, gbc);

        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(5, 0, 5, 10);
        settingsPanel.add(musicLabel, gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.EAST;
        settingsPanel.add(musicToggle, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.WEST;
        settingsPanel.add(sfxLabel, gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.EAST;
        settingsPanel.add(sfxToggle, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.WEST;
        settingsPanel.add(graphicsLabel, gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.EAST;
        settingsPanel.add(graphicsComboBox, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.anchor = GridBagConstraints.WEST;
        settingsPanel.add(cheatCodesLabel, gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        settingsPanel.add(cheatCodesTextField, gbc);

        backButton = new JButton("OK");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                restoreButton();
                dispose();
            }
        });
        backButton.setBackground(Color.WHITE);
        backButton.addMouseListener(new ButtonHoverAdapter());

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        mainPanel.setBackground(new Color(82, 113, 255));
        mainPanel.add(settingsPanel, BorderLayout.CENTER);
        mainPanel.add(backButton, BorderLayout.SOUTH);
        add(mainPanel, BorderLayout.CENTER);

        pack();
        setLocationRelativeTo(null);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                restoreButton();
            }
        });

        loadStateFromFile();
    }

    private JToggleButton createToggleButton(String text) {
        JToggleButton toggleButton = new JToggleButton(text);
        toggleButton.addActionListener(new ToggleButtonListener(toggleButton));
        toggleButton.setBackground(Color.WHITE);
        return toggleButton;
    }

    private void restoreButton() {
        gamePanel.enableButtons();
    }

    private class ToggleButtonListener implements ActionListener {
        private JToggleButton toggleButton;

        public ToggleButtonListener(JToggleButton toggleButton) {
            this.toggleButton = toggleButton;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            toggleButton.setText(toggleButton.isSelected() ? "On" : "Off");
            toggleButton.setBackground(toggleButton.isSelected() ? new Color(102, 255, 51) : Color.WHITE);
            saveStateToFile();
        }
    }

    private class ButtonHoverAdapter extends MouseAdapter {
        @Override
        public void mouseEntered(MouseEvent e) {
            JButton button = (JButton) e.getSource();
            button.setBackground(new Color(102, 255, 51));
        }

        @Override
        public void mouseExited(MouseEvent e) {
            JButton button = (JButton) e.getSource();
            button.setBackground(Color.WHITE);
        }
    }

    private static class JTextFieldLimit extends PlainDocument {
        private final int limit;

        JTextFieldLimit(int limit) {
            super();
            this.limit = limit;
        }

        public void insertString(int offset, String str, AttributeSet attr) throws BadLocationException {
            if (str == null)
                return;

            if ((getLength() + str.length()) <= limit) {
                super.insertString(offset, str, attr);
            }
        }
    }

    private void saveStateToFile() {
        try {
            File file = new File("settings.txt");
            FileWriter writer = new FileWriter(file);

            writer.write("Music:" + (musicToggle.isSelected() ? "On" : "Off") + "\n");
            writer.write("SFX:" + (sfxToggle.isSelected() ? "On" : "Off") + "\n");
            writer.write("Graphics:" + graphicsComboBox.getSelectedItem() + "\n");
            // Add code to save other toggle button states if needed

            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadStateFromFile() {
        try {
            File file = new File("settings.txt");
            if (file.exists()) {
                BufferedReader reader = new BufferedReader(new FileReader(file));
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split(":");
                    if (parts.length == 2) {
                        String toggleName = parts[0];
                        String toggleState = parts[1];
                        if (toggleName.equals("Music")) {
                            boolean musicEnabled = toggleState.equals("On");
                            musicToggle.setSelected(musicEnabled);
                            musicToggle.setText(musicEnabled ? "On" : "Off");
                            musicToggle.setBackground(musicEnabled ? new Color(102, 255, 51) : Color.WHITE);
                            gamePanel.setMusicEnabled(musicEnabled);
                        } else if (toggleName.equals("SFX")) {
                            boolean sfxEnabled = toggleState.equals("On");
                            sfxToggle.setSelected(sfxEnabled);
                            sfxToggle.setText(sfxEnabled ? "On" : "Off");
                            sfxToggle.setBackground(sfxEnabled ? new Color(102, 255, 51) : Color.WHITE);
                            // Add code to handle other toggle buttons if needed
                        } else if (toggleName.equals("Graphics")) {
                            graphicsComboBox.setSelectedItem(toggleState);
                        }
                    }
                }

                reader.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
