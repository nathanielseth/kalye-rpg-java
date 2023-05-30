import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ShopPanel extends JFrame {
    private JButton backButton;

    public ShopPanel() {
        setTitle("Shop");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Set layout manager for the frame
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(600, 400));

        // Set background color
        getContentPane().setBackground(new Color(82, 113, 255));

        // Create and add GUI components for the shop panel
        JPanel shopContentPanel = new JPanel();
        shopContentPanel.setLayout(new BorderLayout());
        shopContentPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        shopContentPanel.setOpaque(false); // Make the panel transparent

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setOpaque(false); // Make the panel transparent

        JLabel shopLabel = new JLabel("            Manggo Lloyd's Sari-Sari Store");
        shopLabel.setFont(new Font("Courier New", Font.BOLD, 17));
        shopLabel.setForeground(Color.WHITE);
        topPanel.add(shopLabel, BorderLayout.WEST);

        ImageIcon shopIcon = new ImageIcon("images/gcash.png");
        JLabel shopImageLabel = new JLabel(shopIcon);
        topPanel.add(shopImageLabel);

        JPanel imagePanel = new JPanel();
        imagePanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
        imagePanel.setOpaque(false); // Make the panel transparent

        topPanel.add(imagePanel, BorderLayout.CENTER);
        shopContentPanel.add(topPanel, BorderLayout.NORTH);

        // Add items to the shop
        JPanel itemsPanel = new JPanel(new GridLayout(3, 3, 10, 10));
        itemsPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        itemsPanel.setOpaque(false); // Make the panel transparent
        itemsPanel.setBackground(Color.WHITE); // Set the background color of the itemsPanel to white

        String[] itemNames = {
                "Ice-y Tubig",
                "Shtick-O",
                "Coke Omsim",
                "Anti-Rabies",
                "Rugby",
                "Dengue Vaccine",
                "Bye-gon",
                "Mouse Trap",
                "Infinity Edge"
        };

        for (int i = 0; i < itemNames.length; i++) {
            JPanel itemPanel = new JPanel(new BorderLayout());
            itemPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            itemPanel.setOpaque(true); // Set the panel to opaque

            // Add item label
            JLabel itemLabel = new JLabel(itemNames[i]);
            itemLabel.setHorizontalAlignment(SwingConstants.CENTER);
            itemPanel.add(itemLabel, BorderLayout.NORTH);

            // Add item price
            JLabel itemPrice = new JLabel(((i + 1) * 10) + " pesos");
            itemPrice.setHorizontalAlignment(SwingConstants.CENTER);
            itemPanel.add(itemPrice, BorderLayout.SOUTH);

            // Add item image
            String imagePath = "images/item" + (i + 1) + ".png";
            ImageIcon itemIcon = new ImageIcon(imagePath);
            JLabel itemImage = new JLabel(itemIcon);
            itemPanel.add(itemImage, BorderLayout.CENTER);

            // Add buy button
            JButton buyButton = new JButton("Buy");
            itemPanel.add(buyButton, BorderLayout.EAST);

            // Change button colors on hover
            buyButton.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    buyButton.setBackground(new Color(102, 255, 51));
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    buyButton.setBackground(Color.WHITE);
                }
            });

            // Set button colors
            buyButton.setBackground(Color.WHITE);
            buyButton.setForeground(Color.BLACK);

            itemsPanel.add(itemPanel);
        }

        shopContentPanel.add(itemsPanel, BorderLayout.CENTER);

        backButton = new JButton("Back");
        backButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        backButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, backButton.getPreferredSize().height));
        shopContentPanel.add(backButton, BorderLayout.SOUTH);

        // Change button colors on hover
        backButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                backButton.setBackground(new Color(102, 255, 51));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                backButton.setBackground(Color.WHITE);
            }
        });

        // Set button colors
        backButton.setBackground(Color.WHITE);
        backButton.setForeground(Color.BLACK);

        add(shopContentPanel, BorderLayout.CENTER);

        // Add action listener to the back button
        backButton.addActionListener(e -> dispose()); // Close the shop frame when the "Back" button is pressed

        pack();
        setLocationRelativeTo(null); // Center the frame on the screen
    }
}
