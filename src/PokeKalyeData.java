public class PokeKalyeData {
    public static final Pokemon PUSPIN = new Pokemon("Puspin", 10);
    public static final Pokemon ASKAL = new Pokemon("Askal", 10);
    public static final Pokemon LANGGAM = new Pokemon("Langgam", 5);
    public static final Pokemon IPIS = new Pokemon("Ipis", 6);
    public static final Pokemon FLYING_IPIS = new Pokemon("Flying Ipis", 20);
    public static final Pokemon DAGA = new Pokemon("Daga", 10);
    public static final Pokemon LAMOK = new Pokemon("Lamok", 6);
    public static final Pokemon BUTIKI = new Pokemon("Butiki", 12);
    public static final Pokemon IBON = new Pokemon("Ibon", 12);
    public static final Pokemon SALAGUBANG = new Pokemon("Salagubang", 50);
    public static final Pokemon DAGANG_KANAL = new Pokemon("Dagang Kanal", 30);
    public static final Pokemon LANGAW = new Pokemon("Langaw", 1);
    public static final Pokemon BATANG_KALYE = new Pokemon("Batang Kalye", 40);
    public static final Pokemon MASTER_SPLINTER = new Pokemon("Master Splinter", 100);

    public static class Pokemon {
        private final String name;
        private int currentHealth;
        private final int maxHealth;

        public Pokemon(String name, int maxHealth) {
            this.name = name;
            this.maxHealth = maxHealth;
            this.currentHealth = maxHealth;
        }

        public String getName() {
            return name;
        }

        public int getCurrentHealth() {
            return currentHealth;
        }

        public void setCurrentHealth(int currentHealth) {
            this.currentHealth = currentHealth;
        }

        public int getMaxHealth() {
            return maxHealth;
        }
    }
}
