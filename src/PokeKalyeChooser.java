import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class PokeKalyeChooser extends JFrame {
    private TooltipLabel puspinLabel;
    private TooltipLabel askalLabel;
    private TooltipLabel langgamLabel;
    private static JRadioButton puspinRadioButton;
    private static JRadioButton askalRadioButton;
    private static JRadioButton langgamRadioButton;
    private JButton startButton;
    private JLabel selectedPokeKalyeLabel;

    public PokeKalyeChooser() {
        setTitle("KalyeRPG");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLayout(null);

        setContentPane(new JLabel(new ImageIcon("images/bg.png")));

        JLabel pokeKalyeChooserLabel = new JLabel(
                "<html><p style='text-align:center; font-family:\"Impact\"; font-size:22px; color:white; letter-spacing:800em;'>CHOOSE YOUR POKEKALYE !</p></html>",
                JLabel.CENTER);

        pokeKalyeChooserLabel.setFont(new Font("Courier New", Font.PLAIN, 24));
        pokeKalyeChooserLabel.setForeground(Color.WHITE);
        pokeKalyeChooserLabel.setHorizontalAlignment(SwingConstants.CENTER);

        puspinLabel = new TooltipLabel("     PUSPIN ");
        askalLabel = new TooltipLabel("     ASKAL ");
        langgamLabel = new TooltipLabel("  LANGGAM ");

        puspinLabel.setOpaque(true);
        askalLabel.setOpaque(true);
        langgamLabel.setOpaque(true);

        puspinRadioButton = new JRadioButton();
        askalRadioButton = new JRadioButton();
        langgamRadioButton = new JRadioButton();

        puspinRadioButton.setOpaque(false);
        askalRadioButton.setOpaque(false);
        langgamRadioButton.setOpaque(false);

        ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(puspinRadioButton);
        buttonGroup.add(askalRadioButton);
        buttonGroup.add(langgamRadioButton);

        startButton = new JButton("START JOURNEY");
        startButton.setEnabled(false);

        startButton.setBackground(Color.WHITE);

        startButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                if (puspinRadioButton.isSelected() || askalRadioButton.isSelected()
                        || langgamRadioButton.isSelected()) {
                    startButton.setBackground(new Color(102, 255, 51));
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                startButton.setBackground(Color.WHITE);
            }
        });

        ActionListener radioButtonListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JRadioButton selectedRadioButton = (JRadioButton) e.getSource();
                updatePokekalyePanel(selectedRadioButton);
                startButton.setEnabled(true);
                updateSelectedPokekalyeLabel(selectedRadioButton);
            }
        };
        puspinRadioButton.addActionListener(radioButtonListener);
        askalRadioButton.addActionListener(radioButtonListener);
        langgamRadioButton.addActionListener(radioButtonListener);

        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int choice = JOptionPane.showConfirmDialog(
                        PokeKalyeChooser.this,
                        "Start your journey with " + getSelectedPokeKalye()
                                + "? You cannot change this later.",
                        "Professor Kalye Asks",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.PLAIN_MESSAGE);

                if (choice == JOptionPane.YES_OPTION) {
                    launchGame();
                    dispose();
                }
            }
        });

        selectedPokeKalyeLabel = new JLabel();
        selectedPokeKalyeLabel.setForeground(Color.WHITE);
        selectedPokeKalyeLabel.setFont(new Font("Courier New", Font.BOLD, 16));
        selectedPokeKalyeLabel.setHorizontalAlignment(SwingConstants.LEFT);
        selectedPokeKalyeLabel.setVerticalAlignment(SwingConstants.BOTTOM);

        // Add components to the content pane
        Container contentPane = getContentPane();
        contentPane.setLayout(null);
        contentPane.add(pokeKalyeChooserLabel);
        contentPane.add(puspinLabel);
        contentPane.add(askalLabel);
        contentPane.add(langgamLabel);
        contentPane.add(puspinRadioButton);
        contentPane.add(askalRadioButton);
        contentPane.add(langgamRadioButton);
        contentPane.add(startButton);
        contentPane.add(selectedPokeKalyeLabel);

        pokeKalyeChooserLabel.setBounds(100, 12, 400, 50);
        puspinLabel.setBounds(100, 90, 80, 30);
        askalLabel.setBounds(270, 90, 80, 30);
        langgamLabel.setBounds(420, 90, 80, 30);
        createImageLabel("images/puspin.png", 140, 140, "Puspin - A fluffy but sneaky little devil.").setBounds(80,
                130, 120, 140);
        createImageLabel("images/askal.png", 140, 140, "Askal - An unhinged creature and a companion for life.")
                .setBounds(240, 130, 140, 140);
        createImageLabel("images/langgam.png", 140, 140, "Langgam - Known to eliminate multiversal threats.")
                .setBounds(390, 160, 140, 140);
        puspinRadioButton.setBounds(120, 280, 20, 20);
        askalRadioButton.setBounds(290, 280, 20, 20);
        langgamRadioButton.setBounds(450, 280, 20, 20);
        startButton.setBounds(200, 322, 200, 30);
        selectedPokeKalyeLabel.setBounds(4, 340, 300, 20);

        setResizable(false);
        setVisible(true);
    }

    private JLabel createImageLabel(String imagePath, int width, int height, String tooltipText) {
        ImageIcon imageIcon = new ImageIcon(imagePath);
        Image image = imageIcon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        JLabel label = new JLabel(new ImageIcon(image));
        label.setToolTipText(tooltipText);
        add(label);
        return label;
    }

    private void updatePokekalyePanel(JRadioButton selectedRadioButton) {
        puspinLabel.setForeground(Color.WHITE);
        askalLabel.setForeground(Color.WHITE);
        langgamLabel.setForeground(Color.WHITE);
        puspinLabel.setOpaque(false);
        askalLabel.setOpaque(false);
        langgamLabel.setOpaque(false);

        puspinLabel.setBackground(null);
        askalLabel.setBackground(null);
        langgamLabel.setBackground(null);

        if (selectedRadioButton == puspinRadioButton) {
            puspinLabel.setOpaque(true);
            puspinLabel.setBackground(new Color(102, 255, 51));
            puspinLabel.setForeground(Color.BLACK);
        } else if (selectedRadioButton == askalRadioButton) {
            askalLabel.setOpaque(true);
            askalLabel.setBackground(new Color(102, 255, 51));
            askalLabel.setForeground(Color.BLACK);
        } else if (selectedRadioButton == langgamRadioButton) {
            langgamLabel.setOpaque(true);
            langgamLabel.setBackground(new Color(102, 255, 51));
            langgamLabel.setForeground(Color.BLACK);
        }
    }

    private void updateSelectedPokekalyeLabel(JRadioButton selectedRadioButton) {
        if (selectedRadioButton == puspinRadioButton) {
            selectedPokeKalyeLabel.setText("THICC AND QUICC");
        } else if (selectedRadioButton == askalRadioButton) {
            selectedPokeKalyeLabel
                    .setText("LOYAL AND UNHINGED");
        } else if (selectedRadioButton == langgamRadioButton) {
            selectedPokeKalyeLabel.setText("SMALL BUT TERRIBLE");
        }
    }

    static String getSelectedPokeKalye() {
        if (puspinRadioButton.isSelected()) {
            return "Puspin";
        } else if (askalRadioButton.isSelected()) {
            return "Askal";
        } else if (langgamRadioButton.isSelected()) {
            return "Langgam";
        } else {
            return "";
        }
    }

    private static void launchGame() {
        Game.main(null);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                PokeKalyeChooser pokeKalyeChooser = new PokeKalyeChooser();
                pokeKalyeChooser.setLocationRelativeTo(null);
            }
        });
    }

    private class TooltipLabel extends JLabel {
        public TooltipLabel(String text) {
            super(text);
            setOpaque(true);
        }
    }
}
