
public class PokeKalyeData {
        public static final PokeKalye KUTING = new PokeKalye("Kuting", 15, MovePool.KALMOT, MovePool.FLEE);
        public static final PokeKalye PUSPIN = new PokeKalye("Puspin", 25, MovePool.KALMOT, MovePool.FLEE,
                        MovePool.PURR);
        public static final PokeKalye PUSPIN_BOOTS = new PokeKalye("Puspin Boots", 60, MovePool.KALMOT, MovePool.FLEE,
                        MovePool.PURR, MovePool.SNEAK_ATTACK);
        public static final PokeKalye TUTA = new PokeKalye("Tuta", 24, MovePool.BITE_TUTA, MovePool.TAHOL);
        public static final PokeKalye ASKAL = new PokeKalye("Askal", 35, MovePool.BITE, MovePool.TAHOL,
                        MovePool.SPEAR);
        public static final PokeKalye BIG_DOG = new PokeKalye("Big Dog", 70, MovePool.BITE_TUTA, MovePool.TAHOL,
                        MovePool.SPEAR, MovePool.OUTRAGE);
        public static final PokeKalye LANGGAM = new PokeKalye("Langgam", 11, MovePool.KAGAT, MovePool.BURROW);
        public static final PokeKalye ANTIK = new PokeKalye("Antik", 20, MovePool.KAGAT, MovePool.BURROW,
                        MovePool.GANG_UP);
        public static final PokeKalye ANTMAN = new PokeKalye("Ant-Man", 10, MovePool.KAGAT, MovePool.BURROW,
                        MovePool.GANG_UP,
                        MovePool.QUANTUM_BITE);
        public static final PokeKalye IPIS = new PokeKalye("Ipis", 12, MovePool.SCRATCH, MovePool.DAPO, MovePool.FLEE);
        public static final PokeKalye FLYING_IPIS = new PokeKalye("Flying Ipis", 40, MovePool.SCRATCH,
                        MovePool.DAPO_FLYING,
                        MovePool.LIPAD);
        public static final PokeKalye DAGA = new PokeKalye("Daga", 21, MovePool.NGATNGAT, MovePool.SQUEAK);
        public static final PokeKalye LAMOK = new PokeKalye("Lamok", 12, MovePool.DAPO_LAMOK, MovePool.SUCK);
        public static final PokeKalye BUTIKI = new PokeKalye("Butiki", 21, MovePool.LICK, MovePool.SIT_AND_WAIT);
        public static final PokeKalye IBON = new PokeKalye("Ibon", 23, MovePool.PECK, MovePool.POOP);
        public static final PokeKalye COLORED_SISIW = new PokeKalye("Colored Sisiw", 60, MovePool.PECK);
        public static final PokeKalye SALAGUBANG = new PokeKalye("Salagubang", 50, MovePool.HARDEN, MovePool.KUROT);
        public static final PokeKalye DAGANG_KANAL = new PokeKalye("Dagang Kanal", 50, MovePool.KAGAT_DAGANG_KANAL,
                        MovePool.SQUEAK_KANAL);
        public static final PokeKalye LANGAW = new PokeKalye("Langaw", 12, MovePool.FLEE, MovePool.DAPO);
        public static final PokeKalye BANGAW = new PokeKalye("Bangaw", 30, MovePool.FLEE, MovePool.DAPO);
        public static final PokeKalye TUKO = new PokeKalye("Tuko", 40, MovePool.CROAK, MovePool.LICK);
        public static final PokeKalye MANOK = new PokeKalye("Manok", 20, MovePool.TIKTILAOK, MovePool.PECK,
                        MovePool.LIPAD,
                        MovePool.FLEE);
        public static final PokeKalye GAGAMBA = new PokeKalye("Gagamba", 18, MovePool.DAPO, MovePool.KAGAT);
        public static final PokeKalye PANIKI = new PokeKalye("Paniki", 30, MovePool.LIPAD, MovePool.KAGAT);
        public static final PokeKalye TUTUBI = new PokeKalye("Tutubi", 30, MovePool.LIPAD, MovePool.DAPO);
        public static final PokeKalye AHAS = new PokeKalye("Ahas", 24, MovePool.SLITHER, MovePool.KAGAT_SNAKE,
                        MovePool.HISS);
        public static final PokeKalye PARO_PARO = new PokeKalye("Paro-paro", 13, MovePool.LIPAD, MovePool.DAPO,
                        MovePool.FLEE);
        public static final PokeKalye HIGAD = new PokeKalye("Higad", 20, MovePool.DAPO_HIGAD);
        public static final PokeKalye TIPAKLONG = new PokeKalye("Tipaklong", 21, MovePool.DAPO, MovePool.SCRATCH);
        public static final PokeKalye MANDARANGKAL = new PokeKalye("Mandarangkal", 16, MovePool.KARATE_CHOP,
                        MovePool.DAPO);
        public static final PokeKalye KABAYO = new PokeKalye("Kabayo", 100, MovePool.TADYAK);
        public static final PokeKalye MASTER_SPLINTER = new PokeKalye("Master Splinter", 100,
                        MovePool.NGATNGAT_MASTER_SPLINTER,
                        MovePool.MEDITATE, MovePool.SEWER_FOCUS, MovePool.RAT_ATTACK);
        public static final PokeKalye PALAKA = new PokeKalye("Palaka", 35, MovePool.CROAK, MovePool.LICK);
        public static final PokeKalye KUTO = new PokeKalye("Kuto", 10, MovePool.SCRATCH);
        public static final PokeKalye BUBUYOG = new PokeKalye("Bubuyog", 14, MovePool.STING, MovePool.BUZZ);

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
