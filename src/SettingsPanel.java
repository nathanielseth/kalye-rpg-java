import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class SettingsPanel extends JFrame {
    private JButton backButton;

    public SettingsPanel() {
        setTitle("Settings");
        setLocationRelativeTo(null);

        // Set layout manager for the frame
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(400, 300));

        // Set background color
        getContentPane().setBackground(Color.WHITE);

        JLabel settingsLabel = new JLabel("Settings", SwingConstants.CENTER);
        settingsLabel.setFont(new Font("Courier New", Font.BOLD, 24));

        backButton = new JButton("Back");
        backButton.addActionListener(e -> dispose());

        JPanel settingsPanel = new JPanel();
        settingsPanel.setLayout(new BorderLayout());
        settingsPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        settingsPanel.setBackground(Color.WHITE);

        settingsPanel.add(settingsLabel, BorderLayout.NORTH);
        settingsPanel.add(backButton, BorderLayout.SOUTH);

        add(settingsPanel, BorderLayout.CENTER);

        pack();
        setLocationRelativeTo(null); // Center the frame on the screen
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            SettingsPanel settingsPanel = new SettingsPanel();
            settingsPanel.setVisible(true);
        });
    }
}
