import javax.swing.*;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class SettingsPanel extends JFrame {
    private JButton backButton;
    private JToggleButton musicToggle;
    private JToggleButton sfxToggle;
    private JTextField cheatCodesTextField;

    public SettingsPanel() {
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
        musicToggle = new JToggleButton("On");
        musicToggle.addActionListener(new ToggleButtonListener(musicToggle));
        musicToggle.setBackground(new Color(102, 255, 51));

        JLabel sfxLabel = new JLabel("SFX");
        sfxToggle = new JToggleButton("On");
        sfxToggle.addActionListener(new ToggleButtonListener(sfxToggle));
        sfxToggle.setBackground(new Color(102, 255, 51));

        JLabel graphicsLabel = new JLabel("Graphics");
        JComboBox<String> graphicsComboBox = new JComboBox<>(new String[] { "Normal", "Sprite" });

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
        backButton.addActionListener(e -> dispose());
        backButton.setBackground(Color.WHITE);
        backButton.addMouseListener(new ButtonHoverAdapter());

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        mainPanel.setBackground(new Color(82, 113, 255));
        mainPanel.add(settingsPanel, BorderLayout.CENTER);
        mainPanel.add(backButton, BorderLayout.SOUTH);
        add(mainPanel, BorderLayout.CENTER);

        pack();
        setLocationRelativeTo(null); // Center the frame on the screen
    }

    private class ToggleButtonListener implements ActionListener {
        private JToggleButton toggleButton;

        public ToggleButtonListener(JToggleButton toggleButton) {
            this.toggleButton = toggleButton;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (toggleButton.isSelected()) {
                toggleButton.setText("Off");
                toggleButton.setBackground(Color.WHITE);
            } else {
                toggleButton.setText("On");
                toggleButton.setBackground(new Color(102, 255, 51));
            }
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

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            SettingsPanel settingsPanel = new SettingsPanel();
            settingsPanel.setVisible(true);
        });
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
}
