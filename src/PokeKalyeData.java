
public class PokeKalyeData {
        public static final PokeKalye KUTING = new PokeKalye("Kuting", 13, MovePool.KALMOT, MovePool.FLEE);
        public static final PokeKalye PUSPIN = new PokeKalye("Puspin", 25, MovePool.KALMOT, MovePool.FLEE,
                        MovePool.PURR);
        public static final PokeKalye PUSPIN_BOOTS = new PokeKalye("Puspin Boots", 70, MovePool.KALMOT, MovePool.FLEE,
                        MovePool.PURR, MovePool.SNEAK_ATTACK);
        public static final PokeKalye TUTA = new PokeKalye("Tuta", 18, MovePool.BITE_TUTA, MovePool.TAHOL);
        public static final PokeKalye ASKAL = new PokeKalye("Askal", 35, MovePool.BITE, MovePool.TAHOL,
                        MovePool.SPEAR);
        public static final PokeKalye BIG_DOG = new PokeKalye("Big Dog", 120, MovePool.BITE_TUTA, MovePool.TAHOL,
                        MovePool.SPEAR, MovePool.OUTRAGE);
        public static final PokeKalye LANGGAM = new PokeKalye("Langgam", 11, MovePool.KAGAT, MovePool.BURROW);
        public static final PokeKalye ANTIK = new PokeKalye("Antik", 25, MovePool.KAGAT, MovePool.BURROW,
                        MovePool.GANG_UP);
        public static final PokeKalye ANTMAN = new PokeKalye("Ant-Man", 70, MovePool.KAGAT, MovePool.BURROW,
                        MovePool.GANG_UP,
                        MovePool.QUANTUM_BITE);
        public static final PokeKalye IPIS = new PokeKalye("Ipis", 7, MovePool.SCRATCH, MovePool.DAPO, MovePool.FLEE);
        public static final PokeKalye FLYING_IPIS = new PokeKalye("Flying Ipis", 40, MovePool.SCRATCH,
                        MovePool.DAPO_FLYING,
                        MovePool.LIPAD);
        public static final PokeKalye DAGA = new PokeKalye("Daga", 17, MovePool.NGATNGAT, MovePool.SQUEAK);
        public static final PokeKalye LAMOK = new PokeKalye("Lamok", 9, MovePool.DAPO_LAMOK, MovePool.SUCK);
        public static final PokeKalye BUTIKI = new PokeKalye("Butiki", 10, MovePool.LICK, MovePool.SIT_AND_WAIT);
        public static final PokeKalye IBON = new PokeKalye("Ibon", 18, MovePool.PECK, MovePool.POOP);
        public static final PokeKalye COLORED_SISIW = new PokeKalye("Colored\n Sisiw", 60, MovePool.PECK);
        public static final PokeKalye SALAGUBANG = new PokeKalye("Salagubang", 60, MovePool.HARDEN, MovePool.KUROT);
        public static final PokeKalye DAGANG_KANAL = new PokeKalye("Dagang Kanal", 50, MovePool.KAGAT_DAGANG_KANAL,
                        MovePool.SQUEAK_KANAL);
        public static final PokeKalye LANGAW = new PokeKalye("Langaw", 7, MovePool.FLEE, MovePool.DAPO);
        public static final PokeKalye BANGAW = new PokeKalye("Bangaw", 40, MovePool.FLEE, MovePool.DAPO);
        public static final PokeKalye TUKO = new PokeKalye("Tuko", 50, MovePool.CROAK, MovePool.LICK);
        public static final PokeKalye MANOK = new PokeKalye("Manok", 14, MovePool.TIKTILAOK, MovePool.PECK,
                        MovePool.LIPAD,
                        MovePool.FLEE);
        public static final PokeKalye GAGAMBA = new PokeKalye("Gagamba", 18, MovePool.DAPO, MovePool.KAGAT);
        public static final PokeKalye PANIKI = new PokeKalye("Paniki", 30, MovePool.LIPAD, MovePool.KAGAT);
        public static final PokeKalye TUTUBI = new PokeKalye("Tutubi", 30, MovePool.LIPAD, MovePool.DAPO);
        public static final PokeKalye AHAS = new PokeKalye("Ahas", 20, MovePool.SLITHER, MovePool.KAGAT_SNAKE,
                        MovePool.HISS);
        public static final PokeKalye PARO_PARO = new PokeKalye("Paro-paro", 13, MovePool.LIPAD, MovePool.DAPO);
        public static final PokeKalye HIGAD = new PokeKalye("Higad", 20, MovePool.DAPO_HIGAD);
        public static final PokeKalye TIPAKLONG = new PokeKalye("Tipaklong", 19, MovePool.DAPO, MovePool.SCRATCH);
        public static final PokeKalye MANDARANGKAL = new PokeKalye("Mandarangkal", 14, MovePool.KARATE_CHOP,
                        MovePool.DAPO);
        public static final PokeKalye KABAYO = new PokeKalye("Kabayo", 100, MovePool.TADYAK, MovePool.FLEE_LANGAW);
        public static final PokeKalye PROFESSOR_SPLINTER = new PokeKalye("Professor Splinter", 1000,
                        MovePool.NGATNGAT_PROFESSOR_SPLINTER,
                        MovePool.MEDITATE, MovePool.SEWER_FOCUS, MovePool.RAT_ATTACK);
        public static final PokeKalye PALAKA = new PokeKalye("Palaka", 30, MovePool.CROAK, MovePool.LICK);
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
