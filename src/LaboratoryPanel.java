import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class LaboratoryPanel extends JPanel {
        private JButton backButton;
        private GamePanel gamePanel;
        private Clip musicClip;
        private Clip hoverSoundClip;
        private Clip buttonClickSoundClip;
        private JLabel pesosLabel;
        private JPanel itemsPanel;

        public LaboratoryPanel(GamePanel gamePanel) {
                preloadSounds();
                this.gamePanel = gamePanel;
                setBackground(new Color(255, 51, 102));
                setLayout(new BorderLayout());
                setPreferredSize(new Dimension(600, 400));
                setOpaque(true);

                JPanel labContentPanel = new JPanel();
                labContentPanel.setLayout(new BorderLayout());
                labContentPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
                labContentPanel.setOpaque(false);

                JPanel topPanel = new JPanel(new BorderLayout());
                topPanel.setOpaque(false);

                // JLabel labLabel = new JLabel("Laboratory");
                // labLabel.setFont(new Font("Courier New", Font.BOLD, 17));
                // labLabel.setForeground(Color.WHITE);
                // topPanel.add(labLabel, BorderLayout.WEST);

                // ImageIcon labIcon = new ImageIcon("images/pokedex.png");
                // JLabel labImageLabel = new JLabel(labIcon);
                // topPanel.add(labImageLabel);

                JPanel imagePanel = new JPanel();
                imagePanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
                imagePanel.setOpaque(false);

                topPanel.add(imagePanel, BorderLayout.CENTER);
                labContentPanel.add(topPanel, BorderLayout.NORTH);

                itemsPanel = new JPanel(new GridLayout(0, 3, 10, 10));
                itemsPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
                itemsPanel.setOpaque(false);

                JScrollPane scrollPane = new JScrollPane();
                scrollPane.setPreferredSize(new Dimension(600, 300));
                scrollPane.setViewportView(createPokeKalyePanel());

                JScrollBar verticalScrollBar = scrollPane.getVerticalScrollBar();
                verticalScrollBar.setUnitIncrement(45);

                JPanel contentPanel = new JPanel();
                contentPanel.setLayout(new BorderLayout());
                contentPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
                contentPanel.setOpaque(false);

                JLabel shopLabel = new JLabel(" Professor Kalye's Laboratory");
                shopLabel.setFont(new Font("Impact", Font.PLAIN, 18));
                shopLabel.setForeground(Color.WHITE);
                topPanel.add(shopLabel, BorderLayout.WEST);

                pesosLabel = new JLabel("PokeKalye Caught: " + gamePanel.getDefeatedEnemiesCount());
                pesosLabel.setFont(new Font("Impact", Font.PLAIN, 17));
                pesosLabel.setForeground(Color.WHITE);
                topPanel.add(pesosLabel, BorderLayout.EAST);

                int playerLevel = gamePanel.getLevel();
                if (playerLevel >= 24) {
                        pesosLabel.setText("get out.");
                        shopLabel.setText("^@*#$(*#@*%%???");
                        shopLabel.setFont(new Font("Courier New", Font.PLAIN, 18));
                        pesosLabel.setFont(new Font("Courier New", Font.PLAIN, 18));

                }

                ImageIcon shopIcon = new ImageIcon("images/gcash.png");
                JLabel shopImageLabel = new JLabel(shopIcon);
                topPanel.add(shopImageLabel);

                contentPanel.add(topPanel, BorderLayout.NORTH);
                contentPanel.add(scrollPane, BorderLayout.CENTER);

                backButton = new JButton("Back");
                backButton.setAlignmentX(Component.CENTER_ALIGNMENT);
                backButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, backButton.getPreferredSize().height));
                setupButtonMouseListener(backButton);
                contentPanel.add(backButton, BorderLayout.SOUTH);
                backButton.setBackground(Color.WHITE);
                backButton.setForeground(Color.BLACK);

                backButton.addActionListener(e -> {
                        gamePanel.sariSariButton.setBackground(Color.WHITE);
                        playButtonClickSound();
                        stopMusic();
                        gamePanel.setShopOpen(false);
                        gamePanel.resumeMusic();
                        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
                        Container contentPane = frame.getContentPane();
                        contentPane.removeAll();
                        contentPane.add(gamePanel);
                        frame.pack();
                        frame.revalidate();
                        frame.repaint();
                });

                add(contentPanel, BorderLayout.CENTER);
                setPreferredSize(new Dimension(600, 400));
                playMusic("media/audio/professorTheme.wav");
        }

        private JPanel createPokeKalyePanel() {
                JPanel panel = new JPanel();
                panel.setLayout(new GridLayout(0, 3, 10, 10));
                panel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
                panel.setOpaque(false);

                HashMap<String, String> pokeKalyeDescriptions = new HashMap<>();
                pokeKalyeDescriptions.put("Kuting", "KUTING #001\r\n" + "Nature: Playful\r\n" +
                                "Moveset: Kalmot, Flee\r\n" +
                                "\r\n" +
                                "The sharp claw on Kuting acts as its main defense mechanism. This\r\n" +
                                "adorable PokeKalye possesses a keen sense for identifying dangerous\r\n" +
                                "threats and demonstrates remarkable tactics in choosing its battles wisely.");

                pokeKalyeDescriptions.put("Puspin", "PUSPIN #002\r\n" +
                                "Nature: Playful\r\n" +
                                "Moveset: Kalmot, Flee, Purr\r\n" +
                                "\r\n" +
                                "Typically minding its own business, Puspin may seem docile and\r\n" +
                                "nonchalant, but do not be fooled. This PokeKalye demands respect.\r\n\r\n" +
                                "Due to the abundance of Daga in Queensrow, Puspins have grown plump\r\n" +
                                "and chonky, making them irritable when provoked.");

                pokeKalyeDescriptions.put("Puspin Boots", "PUSPIN BOOTS #003\r\n" +
                                "Nature: Playful Fighter\r\n" +
                                "Moveset: Hiss, Flee, Purr, Sneak Attack\r\n" +
                                "\r\n" +
                                "Only a select few Puspins reach their potential evolution,\r\n" +
                                "which proves Puspin Boots is among the top PokeKalyes.\r\n" +
                                "Fueled by an insatiable love for combat, this PokeKalye thrives\r\n" +
                                "on the thrill of battle. With its cunning intelligence, Puspin\r\n" +
                                "Boots carefully selects its opponents, ensuring each confrontation\r\n" +
                                "is a calculated victory.");

                pokeKalyeDescriptions.put("Tuta", "TUTA #004\r\n" +
                                "Nature: Viral\r\n" +
                                "Moveset: Bite, Tahol\r\n" +
                                "\r\n" +
                                "A lively PokeKalye, Tuta possesses an infectious energy that spreads\r\n" +
                                "like wildfire. With its moveset of sharp bites and enthusiastic tahols,\r\n" +
                                "Tuta is always ready for action. When it bites its foes, it is known\r\n" +
                                "that there is a 30% chance that Tuta may inflict them with the dreaded \r\n" +
                                "status effect called Rabies.\r\n");

                pokeKalyeDescriptions.put("Askal", "ASKAL #005\r\n" +
                                "Nature: Viral\r\n" +
                                "Moveset: Bite, Tahol, Spear\r\n" +
                                "\r\n" +
                                "The infamous canine of the Kalye, Askal dominates the kalye with\r\n" +
                                "sheer numbers. This creature's non-stop barking, especially at\r\n" +
                                "night, strikes fear into the hearts of many.");

                pokeKalyeDescriptions.put("Big Dog", "BIG DOG #006\r\n" +
                                "Nature: Tenacious Fighter\r\n" +
                                "Moveset: Flee, Tahol, Spear, Outrage\r\n" +
                                "\r\n" +
                                "The revered tribal chief of the PokeKalye, the Big Dog stands tall\r\n" +
                                "and powerful. With its imposing presence and intimidating howls and\r\n" +
                                "powerful strikes, this beefy creature commands respect and \r\n" +
                                "acknowledgement from its fellow Kalye inhabitants.");

                pokeKalyeDescriptions.put("Langgam", "LANGGAM #007\r\n" +
                                "Nature: Survivor\r\n" +
                                "Moveset: Kagat, Burrow\r\n" +
                                "\r\n" +
                                "A small yet impeccable PokeKalye, cunningly navigates the Kalye\r\n" +
                                "with the sole purpose of survival. Although initially frail, Langgam\r\n" +
                                "relies on its burrowing tactics to grow into a formidable force.\r\n\r\n" +
                                "Legend has it that Langgam played a pivotal role in safeguarding\r\n" +
                                "the multiverse from an immense multiversal threat.");

                pokeKalyeDescriptions.put("Antik", "ANTIK #008\r\n" +
                                "Nature: Survivor\r\n" +
                                "Moveset: Kagat, Burrow, Gang Up\r\n" +
                                "\r\n" +
                                "Antik, the evolved form of Langgam, stands as a testament to its resilience\r\n" +
                                "in the Kalye. With its sharp bites, burrowing skills, and the ability\r\n" +
                                "to rally its kind, Antik is the embodiment of a survivor.\r\n");

                pokeKalyeDescriptions.put("Ant-Man", "ANT-MAN #009\r\n" +
                                "Nature: Fighter\r\n" +
                                "Moveset: Flee, Burrow, Ant-Man, Gang Up, Quantum Bite\r\n" +
                                "\r\n" +
                                "It is said when an Antik ventured into the mysterious Kalye Realm,\r\n" +
                                "it underwent a transformative experience, turning itself into Ant-Man.\r\n" +
                                "Its powerful attacks, including the enigmatic Quantum Bite,\r\n" +
                                "make Ant-Man one of the strongest PokeKalyes ever.");

                pokeKalyeDescriptions.put("Ipis", "IPIS #010\r\n" +
                                "Nature: Playful\r\n" +
                                "Moveset: Scratch, Dapo, Flee\r\n" +
                                "\r\n" +
                                "A deplorable yet adaptable PokeKalye, Ipis is the Kalye terrorist.\r\n" +
                                "For every Ipis caught, two replaces its place, making it unstoppable.\r\n" +
                                "This persistent creature strives in fear of the Queensrow people.");

                pokeKalyeDescriptions.put("Flying Ipis", "FLYING IPIS #011\r\n" +
                                "Nature: Spooky\r\n" +
                                "Moveset: Scratch, Dapo, Lipad\r\n" +
                                "\r\n" +
                                "Flying Ipis, the evolved form of Ipis, invokes terror with its presence.\r\n" +
                                "It soars through the skies, striking fear into the people of Queensrow.\r\n"
                                + "With its swift movements, it leaves a trail of chaos and destruction\r\n" +
                                "in its wake, solidifying its reputation as one of the most fearsome\r\n" +
                                "creatures in the Kalye.");

                pokeKalyeDescriptions.put("Daga", "DAGA #012\r\n" +
                                "Nature: Pesky\r\n" +
                                "Moveset: Ngatngat, Squeak\r\n" +
                                "\r\n" +
                                "This PokeKalye holds a special place in the heart of Professor Kalye.\r\n" +
                                "With its quick bites and high-pitched squeaks, Daga captures the attention\r\n" +
                                "of the Queensrow tambays. This feeble creature is one of the most abundant\r\n" +
                                "PokeKalyes in Queensrow.");

                pokeKalyeDescriptions.put("Dagang Kanal", "DAGANG KANAL #013\r\n" +
                                "Nature: Pesky\r\n" +
                                "Moveset: Kagat, Squeak\r\n" +
                                "\r\n" +
                                "Dagang Kanal, a nimble PokeKalye, scales trees in a mere millisecond.\r\n" +
                                "Native to the Queensrow sewers, it navigates its murky habitat with ease.\r\n" +
                                "It seems to like it here in the Laboratory.");

                pokeKalyeDescriptions.put("Lamok", "LAMOK #014\r\n" +
                                "Nature: Viral\r\n" +
                                "Moveset: Suck, Dapo\r\n" +
                                "\r\n" +
                                "A notorious PokeKalye, it thrives on spreading the deadly status effect\r\n" +
                                "known as Dengue. With its ability to suck, Lamok poses a significant threat\r\n" +
                                "to the Kalye community.");

                pokeKalyeDescriptions.put("Butiki", "BUTIKI #015\r\n" +
                                "Nature: Splash\r\n" +
                                "Moveset: Lick, Stare\r\n" +
                                "\r\n" +
                                "Butiki, the beloved PokeKalye of Queensrow, is embraced by all.\r\n" +
                                "With its friendly nature, Butiki is the least hated PokeKalye according to a\r\n" +
                                "recent Fouls Asia Survey.");

                pokeKalyeDescriptions.put("Ibon", "IBON #016\r\n" +
                                "Nature: Splash\r\n" +
                                "Moveset: Peck, Poop\r\n" +
                                "\r\n" +
                                "Ibon, a Splash PokeKalye, loves excreting white stuff from above.\r\n" +
                                "Its unique ability to Poop brings both surprise and laughter.\r\n" +
                                "With its precise Peck, Ibon mesmerizes the tambays with its grace.");

                pokeKalyeDescriptions.put("Colored Sisiw", "COLORED SISIW #017\r\n" +
                                "Nature: Harmless\r\n" +
                                "Moveset: Peck\r\n" +
                                "\r\n" +
                                "A cherished PokeKalye, commonly seen in the vibrant Kalye West.\r\n" +
                                "The batang kalyes adore its colorful feathers, making it a favorite.");

                pokeKalyeDescriptions.put("Salagubang", "SALAGUBANG #018\r\n" +
                                "Nature: Tenacious\r\n" +
                                "Moveset: Harden, Kurot\r\n" +
                                "\r\n" +
                                "Salagubang, \"The Iron-Back\", boasts a back 10 times stronger\r\n" +
                                "than Bakal. Its sturdy armor endows it with unparalleled resilience.");

                pokeKalyeDescriptions.put("Langaw", "LANGAW #019\r\n" +
                                "Nature: Harmless\r\n" +
                                "Moveset: Dapo, Flee\r\n" +
                                "\r\n" +
                                "Legend has it that the notorious Queensrow syndicate group, The Netizens,\r\n" +
                                "released a swarm of Langaw into the kalyes. These tiny and harmless\r\n" +
                                "PokeKalyes are known for their overwhelming numbers and the constant\r\n" +
                                "Dapo that accompanies their presence.");

                pokeKalyeDescriptions.put("Bangaw", "BANGAW #020\r\n" +
                                "Nature: Pesky\r\n" +
                                "Moveset: Flee, Dapo\r\n" +
                                "\r\n" +
                                "When a swarm of Langaw merges, they form the gigantic PokeKalye known\r\n" +
                                "as Bangaw. These swarming creatures love empty spaces and garbages.\r\n" +
                                "Bangaw typically ignores and avoids PokeKalyes unless provoked.");

                pokeKalyeDescriptions.put("Tuko", "TUKO #021\r\n" +
                                "Nature: Tenacious\r\n" +
                                "Moveset: Croak, Lick\r\n" +
                                "\r\n" +
                                "This PokeKalye is known as the main adversary of the Langaw.\r\n" +
                                "Known for its Lick and its iconic \"tu-koh\" Croak, it is also known\r\n" +
                                "to be overly attached and often refuses to let go of its prey.");

                pokeKalyeDescriptions.put("Manok", "MANOK #022\r\n" +
                                "Nature: Playful Fighter\r\n" +
                                "Moveset: Tiktilaok, Lipad, Peck, Flee\r\n" +
                                "\r\n" +
                                "With Manok's tiktilaok in the early morning, Manok awakens all the\r\n" +
                                "varied PokeKalyes from their slumber. These lively creatures have\r\n" +
                                "earned their stripes through the weekly sabong battles in Queensrow,\r\n" +
                                "making them battle-tested and ready for action.");

                pokeKalyeDescriptions.put("Gagamba", "GAGAMBA #023\r\n" +
                                "Nature: Spooky\r\n" +
                                "Moveset: Dapo, Kagat\r\n" +
                                "\r\n" +
                                "A chilling presence in the kalyes, emerges as one of the most terrifying\r\n" +
                                "PokeKalyes with its hairy, long legs and ominous demeanor. This enigmatic\r\n" +
                                "PokeKalye seems to spawn in the most unexpected corners, sending shivers\r\n" +
                                "down the spines of even the most daring Batang Pasaways. With its stealthy\r\n" +
                                "tactics, Gagamba strikes fear into the people of Queensrow.");

                pokeKalyeDescriptions.put("Paniki", "PANIKI #024\r\n" +
                                "Nature: Spooky\r\n" +
                                "Moveset: Lipad, Kagat\r\n" +
                                "\r\n" +
                                "The PokeKalye that has penchant for lurking in the shadows, it carries a\r\n" +
                                "dark history that echoes throughout the kalyes.\r\n" +
                                "Legend has it that this creature was once responsible for\r\n" +
                                "a massive global pandemic.");

                pokeKalyeDescriptions.put("Tutubi", "TUTUBI #025\r\n" +
                                "Nature: Pesky\r\n" +
                                "Moveset: Lipad, Dapo\r\n" +
                                "\r\n" +
                                "An agile PokeKalye, it is a formidable hunter of the kalyes. It can\r\n" +
                                "move and rotate each of its four wings independently.\r\n" +
                                "Tutubi is well-known for hunting down other smaller PokeKalyes.\r\n");

                pokeKalyeDescriptions.put("Ahas", "AHAS #026\r\n" +
                                "Nature: Spooky Strong\r\n" +
                                "Moveset: Slither, Kagat, Hiss\r\n" +
                                "\r\n" +
                                "Ahas, the apex predator of the kalyes, strikes fear into the hearts of all\r\n" +
                                "who dare to cross its path. With its powerful moveset, this formidable\r\n" +
                                "PokeKalye dominates its territory. Ahas's ferocity and cunning nature make\r\n" +
                                "it a force to be reckoned with, and its presence alone commands respect\r\n" +
                                "among the other PokeKalyes.");

                pokeKalyeDescriptions.put("Higad", "HIGAD #027\r\n" +
                                "Nature: Spooky\r\n" +
                                "Moveset: Dapo\r\n" +
                                "\r\n" +
                                "Higad, a PokeKalye of eerie nature, possesses a unique and powerful Dapo\r\n" +
                                "that sets it apart from others. Legends and folklore suggest that Higad was\r\n" +
                                "spawned by Satan himself, leading to various theories about its name. Some\r\n" +
                                "claim that \"Higad\" means \"he hates God\", emphasizing its malevolent origin.\r\n" +
                                "With its sinister presence, Higad strikes fear into the hearts of onlookers.\r\n" +
                                "Its Dapo is renowned as the strongest, capable of inflicting devastating\r\n" +
                                "effects on its foes.");

                pokeKalyeDescriptions.put("Paro-paro", "PARO-PARO #028\r\n" +
                                "Nature: Pesky\r\n" +
                                "Moveset: Lipad, Dapo\r\n" +
                                "\r\n" +
                                "Paro-Paro is one of the most collected PokeKalyes in Queensrow.\r\n" +
                                "Often underestimated in battle, it thrives in adversity and only grows\r\n" +
                                "stronger with each challenge.");

                pokeKalyeDescriptions.put("Tipaklong", "TIPAKLONG #029\r\n" +
                                "Nature: Pesky\r\n" +
                                "Moveset: Scratch, Dapo\r\n" +
                                "\r\n" +
                                "Notoriously known for its incredible agility, it possesses one of the\r\n" +
                                "strongest legs in the game. Despite this remarkable attribute, Tipaklong's\r\n" +
                                "intellectual capabilities seem to hinder its utilization of such power. Its\r\n" +
                                "low IQ often leads to amusing situations where Tipaklong rarely harnesses the\r\n" +
                                "full potential of its potent leg game. Nonetheless, with its agility and\r\n" +
                                "precise strikes, Tipaklong remains a formidable force to reckon with\r\n" +
                                "in the kalyes.");

                pokeKalyeDescriptions.put("Mandarangkal", "MANDARANGKAL #030\r\n" +
                                "Nature: Strong\r\n" +
                                "Moveset: Karate Chop, Dapo\r\n" +
                                "\r\n" +
                                "The Karate PokeKalye, Mandarangkal strikes fear into the hearts of many\r\n" +
                                "with its signature move, Karate Chop. Many PokeKalyes have been left\r\n" +
                                "fainted in the kalyes after encountering the relentless onslaught of\r\n" +
                                "Mandarangkal's powerful strikes.");

                pokeKalyeDescriptions.put("Kabayo", "KABAYO #031\r\n" +
                                "Nature: Playful Strong\r\n" +
                                "Moveset: Tadyak, Flee\r\n" +
                                "\r\n" +
                                "Kabayo, a PokeKalye hailing all the way from Intramuros, Manila, is known for its\r\n"
                                +
                                "incredible strength and power. Its signature move, Tadyak, is nothing short of\r\n" +
                                "devastating. If a successful hit is delivered, it is widely regarded as the\r\n" +
                                "undisputed strongest attack ever recorded among PokeKalyes. With each\r\n" +
                                "powerful Tadyak and its thunderous Gallop, Kabayo leaves a lasting\r\n" +
                                "impression as a true force of might in the kalyes.");

                pokeKalyeDescriptions.put("Kuto", "KUTO #032\r\n" +
                                "Nature: Pesky\r\n" +
                                "Moveset: Scratch\r\n" +
                                "\r\n" +
                                "Kuto's natural habitat is found in the hair of batang kalyes and computer shop tambays.\r\n"
                                +
                                "Over time, it has developed an immunity to popular shampoos like Head & Shoulders,\r\n"
                                +
                                "allowing it to roam freely and thrive in the kalyes.");

                pokeKalyeDescriptions.put("Bubuyog", "BUBUYOG #033\r\n" +
                                "Nature: Strong\r\n" +
                                "Moveset: Sting, Buzz\r\n" +
                                "\r\n" +
                                "Bubuyog, the Thick PokeKalye, is notorious for its swift stinging attacks and the\r\n"
                                +
                                "incessant buzzing sound it emits.\r\n" +
                                "When the sudden emergence of PokeKalyes occurred,\r\n" +
                                "Bubuyogs exhibited heightened aggression, especially towards humans. Its\r\n" +
                                "venomous Sting strike is considered one of the most oppressive attacks in the \r\n" +
                                "meta of PokeKalyes, capable of paralyzing opponents with a potent stinger.");

                pokeKalyeDescriptions.put("Butete", "BUTETE #034\r\n" +
                                "Nature: Harmless\r\n" +
                                "Moveset: Dura\r\n" +
                                "\r\n" +
                                "This playful PokeKalye enjoys nothing more than surprising unsuspecting\r\nnetizens in the kalye with its sneaky Dura move.");

                pokeKalyeDescriptions.put("Palaka", "PALAKA #035\r\n" +
                                "Nature: Tenacious Pesky\r\n" +
                                "Moveset: Lick, Croak, Dura\r\n" +
                                "\r\n" +
                                "Legend has it that when rainfall blesses the kalyes, Palaka undergoes a remarkable\r\n"
                                +
                                "transformation, growing stronger with each droplet. Its haunting Croak echoes\r\n"
                                +
                                " through the wet streets, a sound both mesmerizing and foreboding." +
                                "\r\nCuriously, other PokeKalyes seem to instinctively avoid Palaka,\r\n" +
                                "for reasons unknown. This enigmatic behavior has sparked speculation among\r\n" +
                                "renowned kalye theorists, who ponder if Palaka's very skin harbors\r\n" +
                                "a natural PokeKalye repellent.");

                pokeKalyeDescriptions.put("Uod", "UOD #036\r\n" +
                                "Nature: Harmless\r\n" +
                                "Moveset: Slither, Dapo\r\n" +
                                "\r\n" +
                                "Its dark and sinister nature hides beneath its harmless exterior.\r\n" +
                                "Legends speak of the when the surgence of PokeKalyes occured,\r\n" +
                                "Uod thrived in the shadows as more and more of their kind emerged.\r\n" +
                                "They feasted on the lifeless corpses of fallen PokeKalyes,\r\n" +
                                "fueling their growth and insatiable hunger.\r\n\r\n" +
                                "Uod's true intentions remain a mystery, leaving many to wonder\r\n" +
                                "what lies beneath its seemingly innocent facade.");

                pokeKalyeDescriptions.put("Suso", "SUSO #037\r\n" +
                                "Nature: Pesky\r\n" +
                                "Moveset: Dapo, Stare\r\n" +
                                "\r\n" +
                                "Suso is the epitome of mediocrity in the PokeKalye landscape.\n" +
                                "It is a miracle that this species has managed to survive,\n" +
                                "given its apparent worthlessness. Suso's harder-than-bakal shell\n" +
                                "might give the impression of being formidable, but in reality,\n" +
                                "it is nothing more than a mid-tier PokeKalye, forever stuck in\n\n" +
                                "the realm of insignificance.");

                pokeKalyeDescriptions.put("Isda", "ISDA #038\r\n" +
                                "Nature: Splash\r\n" +
                                "Moveset: Dura, Stare\r\n" +
                                "\r\n" +
                                "Isda, definitely one of the PokeKalyes of Queensrow.\r\n" +
                                "But have you wondered, how did this majestic creature end up here?\r\n" +
                                "Did it take a wrong turn at the Pasig River? Was it bored of its waterlogged\r\n" +
                                "life and decided to seek adventure in the Kalyes? We may never know.");

                pokeKalyeDescriptions.put("Eagul", "EAGUL #039\r\n" +
                                "Nature: Strong Fighter\r\n" +
                                "Moveset: Mighty Peck, Lipad, Kalmot\r\n" +
                                "\r\n" +
                                "Eagul possesses the most majestic wings to soar high above the kalyes.\r\n" +
                                "So majestic, in fact, its presumed majesticalness supposedly led the\r\n" +
                                "gullible kalye people to form ridiculous cults and religions centered around Eagul.\r\n"
                                +
                                "When the kalye folks were in dire need, they turned to Eagul as their hope.\r\n\r\n" +
                                "The Professor noted and wisely warns,\n" +
                                "'Stay away from Eagul worshippers' or risk being dragged into their laughable fanaticism");

                pokeKalyeDescriptions.put("Kitty Yonarchy", "KITTY YONARCHY #040\r\n" +
                                "Nature: Legendary\r\n" +
                                "Moveset: Kalmot, Kagat, Sneak Attack, Purr\r\n" +
                                "\r\n" +
                                "Is this seemingly adorable PokeKalye truly as harmless as she appears?\r\n" +
                                "Kitty Yonarchy, the Legendary Tribal Kitty and the sister of Puspin Boots.\r\n" +
                                "Ruling with an iron paw, she reigns supreme as the head of the table in Kalsada Central,\r\n"
                                +
                                "exuding authority and power. Trained by the same master as Puspin Boots, she possesses\r\n"
                                +
                                "formidable skills and is one of the most feared PokeKalyes in all of Queensrow.");

                pokeKalyeDescriptions.put("Lolong", "LOLONG #041\r\n" +
                                "Nature: Legendary\r\n" +
                                "Moveset: Kagat, Outrage, Sewer Focus\r\n" +
                                "\r\n" +
                                "The mighty and the undisputed ruler of the Kalye West, Lolong.\r\n" +
                                "Deep within the dark recesses of the Kalye West sewers, he claims his throne.\r\n" +
                                "Legend has it that Lolong feasts upon six Batang Pasaways every day, devouring\r\n" +
                                "their mischievous souls to fuel his own immense power.\r\n" +
                                "When he unleashes his fearsome attack, 'Sewer Focus,' the ground trembles\r\n" +
                                "as if a magnitude six earthquake has just struck, leaving the kalye people\r\n" +
                                "trembling in their slippers.");

                pokeKalyeDescriptions.put("THE GOAT", "THE GOAT #042\r\n" +
                                "Nature: Legendary\r\n" +
                                "Moveset: Tadyak, Spear, Quantum Bite, Burrow\r\n" +
                                "\r\n" +
                                "Its presence is shrouded in mystery, visible only to those who have transcended \r\n" +
                                "the ordinary perception of the kalye realm.\r\n" +
                                "Until recently, no one had ever seen, until you.\r\n\r\n" +
                                "Rumors abound of an unknown animal that underwent a miraculous transformation\r\n" +
                                "upon exposure to a mysterious virus lurking within the kalye's depths.\r\n" +
                                "This convergence gave birth to this very creature.\r\n"
                                +
                                "Speculations arise that THE GOAT may have been the catalyst behind the surge of\r\n" +
                                "PokeKalyes around the globe, the architect of their very existence.");

                String[] pokekalyes = { "Kuting", "Puspin", "Puspin Boots", "Tuta", "Askal", "Big Dog", "Langgam",
                                "Antik",
                                "Ant-Man",
                                "Ipis", "Flying Ipis", "Daga", "Dagang Kanal", "Lamok", "Butiki", "Ibon",
                                "Colored Sisiw", "Salagubang",
                                "Langaw", "Bangaw", "Tuko", "Manok", "Gagamba", "Paniki", "Tutubi", "Ahas", "Higad",
                                "Paro-paro",
                                "Tipaklong", "Mandarangkal", "Kabayo",
                                "Kuto", "Bubuyog", "Butete", "Palaka", "Uod", "Suso", "Isda",
                                "Eagul", "Kitty Yonarchy",
                                "Lolong", "THE GOAT" };

                for (String pokeKalye : pokekalyes) {
                        if (gamePanel.hasCaughtPokeKalye(pokeKalye) && gamePanel.getLevel() < 24) {
                                JPanel itemPanel = new JPanel(new BorderLayout());
                                itemPanel.setBorder(BorderFactory.createLineBorder(Color.WHITE));
                                itemPanel.setOpaque(true);
                                itemPanel.setBackground(new Color(240, 221, 226));

                                JLabel itemLabel = new JLabel(pokeKalye);
                                itemLabel.setHorizontalAlignment(SwingConstants.CENTER);
                                itemPanel.add(itemLabel, BorderLayout.NORTH);

                                String imagePath;
                                if (pokeKalye.equals("Kitty Yonarchy")) {
                                        imagePath = "media/images/yona.png";
                                } else if (pokeKalye.equals("Lolong")) {
                                        imagePath = "media/images/lolong.png";
                                } else if (pokeKalye.equals("THE GOAT")) {
                                        imagePath = "media/images/Goat.png";
                                } else if (pokeKalye.equals("Langgam") || pokeKalye.equals("Puspin")
                                                || pokeKalye.equals("Askal")) {
                                        imagePath = "media/images/" + pokeKalye + "User.png";
                                } else {
                                        imagePath = "media/images/" + pokeKalye + "2.png";
                                }
                                ImageIcon itemIcon = new ImageIcon(imagePath);
                                JLabel itemImage = new JLabel(itemIcon);
                                itemImage.setHorizontalAlignment(SwingConstants.CENTER);
                                itemPanel.add(itemImage, BorderLayout.CENTER);

                                JButton detailsButton = new JButton("Details");
                                detailsButton.setHorizontalAlignment(SwingConstants.CENTER);
                                detailsButton.setForeground(Color.BLACK);
                                detailsButton.setBackground(Color.WHITE);
                                detailsButton.setBorder(BorderFactory.createLineBorder(Color.WHITE));
                                detailsButton.setFocusPainted(false);
                                detailsButton.addMouseListener(new java.awt.event.MouseAdapter() {
                                        public void mouseEntered(java.awt.event.MouseEvent evt) {
                                                playHoverSound();
                                                detailsButton.setBackground(new Color(102, 255, 51));
                                        }

                                        public void mouseExited(java.awt.event.MouseEvent evt) {
                                                detailsButton.setBackground(Color.WHITE);
                                        }
                                });
                                detailsButton.setOpaque(true);
                                itemPanel.add(detailsButton, BorderLayout.SOUTH);

                                String itemDescription = pokeKalyeDescriptions.get(pokeKalye);

                                detailsButton.addActionListener((ActionEvent e) -> {
                                        playButtonClickSound();
                                        String cryPath = "media/audio/Cries/" + pokeKalye + "Cry.wav";
                                        playCrySound(cryPath);
                                        JOptionPane.showMessageDialog(this, itemDescription, pokeKalye + " Details",
                                                        JOptionPane.INFORMATION_MESSAGE);
                                });
                                panel.add(itemPanel);
                        }
                }
                return panel;
        }

        private void preloadSounds() {
                try {
                        AudioInputStream audioInputStream = AudioSystem
                                        .getAudioInputStream(new File("media/audio/hover.wav"));
                        hoverSoundClip = AudioSystem.getClip();
                        hoverSoundClip.open(audioInputStream);

                        audioInputStream = AudioSystem.getAudioInputStream(new File("media/audio/click.wav"));
                        buttonClickSoundClip = AudioSystem.getClip();
                        buttonClickSoundClip.open(audioInputStream);
                } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
                        e.printStackTrace();
                }
        }

        private void playMusic(String filepath) {
                Thread musicThread = new Thread(() -> {
                        try {

                                int playerLevel = gamePanel.getLevel();
                                if (playerLevel >= 24) {
                                        AudioInputStream audioInputStream = AudioSystem
                                                        .getAudioInputStream(new File("media/audio/evil.wav"));
                                        musicClip = AudioSystem.getClip();
                                        musicClip.open(audioInputStream);
                                } else {
                                        AudioInputStream audioInputStream = AudioSystem
                                                        .getAudioInputStream(new File(filepath));
                                        musicClip = AudioSystem.getClip();
                                        musicClip.open(audioInputStream);
                                }

                                musicClip.loop(Clip.LOOP_CONTINUOUSLY);
                                musicClip.start();
                        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
                                e.printStackTrace();
                        }
                });

                musicThread.start();
        }

        private void stopMusic() {
                if (musicClip != null && musicClip.isRunning()) {
                        musicClip.stop();
                        musicClip.close();
                }
        }

        private void playHoverSound() {
                if (hoverSoundClip != null && !hoverSoundClip.isRunning()) {
                        hoverSoundClip.setFramePosition(0);
                        hoverSoundClip.start();
                }
        }

        private void playButtonClickSound() {
                if (buttonClickSoundClip != null && !buttonClickSoundClip.isRunning()) {
                        buttonClickSoundClip.setFramePosition(0);
                        buttonClickSoundClip.start();
                }
        }

        private void setupButtonMouseListener(JButton button) {
                button.addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseEntered(MouseEvent e) {
                                if (button.isEnabled()) {
                                        button.setBackground(new Color(102, 255, 51));
                                        playHoverSound();
                                }
                        }

                        @Override
                        public void mouseExited(MouseEvent e) {
                                if (button.isEnabled()) {
                                        button.setBackground(Color.WHITE);
                                }
                        }
                });
        }

        private void playCrySound(String soundPath) {
                Thread crySoundThread = new Thread(() -> {
                        try {
                                AudioInputStream audioInputStream = AudioSystem
                                                .getAudioInputStream(new File(soundPath));
                                Clip clip = AudioSystem.getClip();
                                clip.open(audioInputStream);
                                clip.start();
                        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
                                e.printStackTrace();
                        }
                });

                crySoundThread.start();
        }
}
