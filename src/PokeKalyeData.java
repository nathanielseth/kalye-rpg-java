
public class PokeKalyeData {
    public static final PokeKalye KUTING = new PokeKalye("Kuting", 10, MovePool.KALMOT, MovePool.FLEE);
    public static final PokeKalye PUSPIN = new PokeKalye("Puspin", 20, MovePool.KALMOT, MovePool.FLEE, MovePool.PURR);
    public static final PokeKalye PUSPIN_BOOTS = new PokeKalye("Puspin Boots", 40, MovePool.KALMOT, MovePool.FLEE,
            MovePool.PURR, MovePool.SNEAK_ATTACK);
    public static final PokeKalye TUTA = new PokeKalye("Tuta", 10, MovePool.BITE_TUTA, MovePool.TAHOL);
    public static final PokeKalye ASKAL = new PokeKalye("Askal", 30, MovePool.BITE, MovePool.TAHOL);
    public static final PokeKalye BIG_DOG = new PokeKalye("Big Dog", 10, MovePool.BITE_TUTA, MovePool.TAHOL,
            MovePool.SPEAR, MovePool.OUTRAGE);
    public static final PokeKalye LANGGAM = new PokeKalye("Langgam", 10, MovePool.KAGAT, MovePool.BURROW);
    public static final PokeKalye ANTIK = new PokeKalye("Antik", 10, MovePool.KAGAT, MovePool.BURROW, MovePool.GANG_UP);
    public static final PokeKalye ANTMAN = new PokeKalye("Ant-Man", 10, MovePool.KAGAT, MovePool.BURROW,
            MovePool.GANG_UP,
            MovePool.QUANTUM_BITE);
    public static final PokeKalye IPIS = new PokeKalye("Ipis", 6, MovePool.SCRATCH, MovePool.DAPO);
    public static final PokeKalye FLYING_IPIS = new PokeKalye("Flying Ipis", 20, MovePool.SCRATCH, MovePool.DAPO_FLYING,
            MovePool.LIPAD);
    public static final PokeKalye DAGA = new PokeKalye("Daga", 10, MovePool.NGATNGAT, MovePool.SQUEAK);
    public static final PokeKalye LAMOK = new PokeKalye("Lamok", 6, MovePool.DAPO_LAMOK, MovePool.SUCK);
    public static final PokeKalye BUTIKI = new PokeKalye("Butiki", 10, MovePool.LICK, MovePool.SIT_AND_WAIT);
    public static final PokeKalye IBON = new PokeKalye("Ibon", 10, MovePool.PECK, MovePool.POOP);
    public static final PokeKalye COLORED_SISIW = new PokeKalye("Colored Sisiw", 20, MovePool.PECK);
    public static final PokeKalye SALAGUBANG = new PokeKalye("Salagubang", 10, MovePool.HARDEN, MovePool.KUROT);
    public static final PokeKalye DAGANG_KANAL = new PokeKalye("Dagang Kanal", 10, MovePool.KAGAT_DAGANG_KANAL,
            MovePool.SQUEAK_KANAL);
    public static final PokeKalye LANGAW = new PokeKalye("Langaw", 10, MovePool.FLEE, MovePool.DAPO);
    public static final PokeKalye BANGAW = new PokeKalye("Bangaw", 10, MovePool.FLEE, MovePool.DAPO);
    public static final PokeKalye TUKO = new PokeKalye("Tuko", 10, MovePool.CROAK, MovePool.LICK);
    public static final PokeKalye MANOK = new PokeKalye("Manok", 10, MovePool.TIKTILAOK, MovePool.PECK, MovePool.LIPAD,
            MovePool.FLEE);
    public static final PokeKalye GAGAMBA = new PokeKalye("Gagamba", 10, MovePool.DAPO, MovePool.KAGAT);
    public static final PokeKalye PANIKI = new PokeKalye("Paniki", 10, MovePool.LIPAD, MovePool.KAGAT);
    public static final PokeKalye TUTUBI = new PokeKalye("Tutubi", 10, MovePool.LIPAD, MovePool.DAPO);
    public static final PokeKalye AHAS = new PokeKalye("Ahas", 10, MovePool.SLITHER, MovePool.KAGAT_SNAKE,
            MovePool.HISS);
    public static final PokeKalye PARO_PARO = new PokeKalye("Paro-paro", 10, MovePool.LIPAD, MovePool.DAPO);
    public static final PokeKalye HIGAD = new PokeKalye("Higad", 10, MovePool.DAPO_HIGAD);
    public static final PokeKalye TIPAKLONG = new PokeKalye("Tipaklong", 10, MovePool.KARATE_CHOP, MovePool.DAPO);
    public static final PokeKalye KABAYO = new PokeKalye("Kabayo", 10, MovePool.TADYAK);
    public static final PokeKalye MASTER_SPLINTER = new PokeKalye("Master Splinter", 100,
            MovePool.NGATNGAT_MASTER_SPLINTER,
            MovePool.MEDITATE, MovePool.SEWER_FOCUS, MovePool.RAT_ATTACK);
    public static final PokeKalye PALAKA = new PokeKalye("Palaka", 10, MovePool.CROAK);
    public static final PokeKalye KUTO = new PokeKalye("Kuto", 10, MovePool.SCRATCH);

    public static class PokeKalye {
        private final String name;
        private int currentHealth;
        private final int maxHealth;
        private final MovePool.Move[] moves;

        public PokeKalye(String name, int maxHealth, MovePool.Move... moves) {
            this.name = name;
            this.maxHealth = maxHealth;
            this.currentHealth = maxHealth;
            this.moves = moves;
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

        public MovePool.Move[] getMoves() {
            return moves;
        }
    }
}
