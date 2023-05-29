import javax.swing.*;
import java.awt.*;

public class ShopPanel extends JFrame {
    private JButton backButton;

    public ShopPanel() {
        setTitle("Shop");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Set layout manager for the frame
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(600, 400));

        // Create and add GUI components for the shop panel
        JPanel shopContentPanel = new JPanel();
        shopContentPanel.setLayout(new BoxLayout(shopContentPanel, BoxLayout.Y_AXIS));
        shopContentPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel shopLabel = new JLabel("                       Sari-Sari Store");
        shopLabel.setFont(new Font("Courier New", Font.BOLD, 17));
        shopContentPanel.add(shopLabel);

        // Add items to the shop
        JPanel itemsPanel = new JPanel(new GridLayout(3, 3, 10, 10));
        itemsPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        String[] itemNames = {
                "Ice-y Tubig",
                "Shtick-O",
                "Coke Omsim",
                "Anti-Rabies",
                "Rugby",
                "Dengue Vaccine",
                "Baygon",
                "Mouse Trap",
                "Infinity Edge"
        };

        for (int i = 0; i < itemNames.length; i++) {
            JPanel itemPanel = new JPanel(new BorderLayout());
            itemPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

            // Add item image
            String imagePath = "images/item" + (i + 1) + ".png";
            ImageIcon itemIcon = new ImageIcon(imagePath);
            JLabel itemImage = new JLabel(itemIcon);
            itemPanel.add(itemImage, BorderLayout.CENTER);

            // Add item label
            JLabel itemLabel = new JLabel(itemNames[i]);
            itemLabel.setHorizontalAlignment(SwingConstants.CENTER);
            itemPanel.add(itemLabel, BorderLayout.NORTH);

            // Add item price
            JLabel itemPrice = new JLabel("$" + ((i + 1) * 10));
            itemPrice.setHorizontalAlignment(SwingConstants.CENTER);
            itemPanel.add(itemPrice, BorderLayout.SOUTH);

            // Add buy button
            JButton buyButton = new JButton("Buy");
            itemPanel.add(buyButton, BorderLayout.EAST);

            itemsPanel.add(itemPanel);
        }

        shopContentPanel.add(itemsPanel);

        backButton = new JButton("Back");
        backButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        backButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, backButton.getPreferredSize().height));
        shopContentPanel.add(backButton);

        add(shopContentPanel, BorderLayout.CENTER);

        // Add action listener to the back button
        backButton.addActionListener(e -> dispose()); // Close the shop frame when the "Back" button is pressed

        pack();
        setLocationRelativeTo(null); // Center the frame on the screen
    }
}
