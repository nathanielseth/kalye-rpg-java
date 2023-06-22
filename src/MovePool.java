public class MovePool {
    public static final Move KALMOT = new Move("Kalmot", 7, false, 1);
    public static final Move FLEE = new Move("Flee", 0, true, 0.8);
    public static final Move PURR = new Move("Purr", 0, false, 1, Effect.HEAL_HP);
    public static final Move SNEAK_ATTACK = new Move("Sneak Attack", 35, false, 1.0);
    public static final Move BITE = new Move("Bite", 5, false, 1, Effect.RABIES);
    public static final Move BITE_TUTA = new Move("Bite", 4, false, 0.7);
    public static final Move TAHOL = new Move("Tahol", 0, true, 1, Effect.MULTIPLY_DAMAGE);
    public static final Move SPEAR = new Move("Spear", 10, false, 0.9);
    public static final Move OUTRAGE = new Move("Outrage", 45, false, 0.8);
    public static final Move KAGAT = new Move("Kagat", 5, false, 1);
    public static final Move BURROW = new Move("Burrow", 3, true, 0.75, Effect.DODGE_NEXT_MOVE);
    public static final Move BURROW_ANTMAN = new Move("Quantum Burrow", 10, true, 0.8, Effect.DODGE_NEXT_MOVE);
    public static Move GANG_UP = new Move("Gang Up", 1, false, 1, Effect.REPEAT_MOVE);
    public static final Move QUANTUM_BITE = new Move("Quantum Bite", 70, false, 0.95);
    public static final Move SCRATCH = new Move("Scratch", 1, false, 0.9);
    public static final Move DAPO = new Move("Dapo", 2, false, 0.7);
    public static final Move DAPO_FLYING = new Move("Dapo", 4, false, 1);
    public static final Move LIPAD = new Move("Lipad", 5, false, 1);
    public static final Move NGATNGAT = new Move("Ngatngat", 3, false, 0.8);
    public static final Move SQUEAK = new Move("Squeak", 0, false, 1);
    public static final Move DAPO_LAMOK = new Move("Dapo", 1, false, 1);
    public static final Move SUCK = new Move("Suck", 4, false, 1, Effect.DENGUE);
    public static final Move SIT_AND_WAIT = new Move("Stare", 0, false, 1);
    public static final Move LICK = new Move("Lick", 5, false, 0.9);
    public static final Move PECK = new Move("Peck", 4, false, 0.8);
    public static final Move POOP = new Move("Poop", 0, false, 1);
    public static final Move HARDEN = new Move("Harden", 0, false, 1);
    public static final Move KUROT = new Move("Kurot", 1, false, 1);
    public static final Move KAGAT_DAGANG_KANAL = new Move("Kagat", 8, false, 1);
    public static final Move SQUEAK_KANAL = new Move("Squeak", 0, false, 0, Effect.HEAL_HP);
    public static final Move FLEE_LANGAW = new Move("Flee", 0, true, 0.6);
    public static final Move DAPO_LANGAW = new Move("Dapo", 4, false, 1);

    public static final Move RAT_ATTACK = new Move("Rat Attack", 60, false, 1);
    public static final Move SEWER_FOCUS = new Move("Sewer Focus", 20, false, 1, Effect.BLOCK_MOVE);
    public static final Move MEDITATE = new Move("Meditate", 0, false, 0.6, Effect.HEAL_HP);
    public static final Move NGATNGAT_PROFESSOR_SPLINTER = new Move("Ngatngat", 24, false, 1);

    public static final Move CROAK = new Move("Croak", 0, true, 1);
    public static final Move TIKTILAOK = new Move("Tiktilaok", 0, true, 1);
    public static final Move SLITHER = new Move("Slither", 5, true, 0.9);
    public static final Move KAGAT_SNAKE = new Move("Kagat", 9, false, 0.7);
    public static final Move HISS = new Move("Hiss", 0, true, 1);
    public static final Move HISS_PUSPIN = new Move("Hiss", 0, true, 0.8);
    public static final Move DAPO_HIGAD = new Move("Dapo", 8, true, 0.5);
    public static final Move KARATE_CHOP = new Move("Karate Chop", 8, false, 0.4);
    public static final Move TADYAK = new Move("Tadyak", 30, false, 0.5);
    public static final Move STING = new Move("Sting", 9, false, 0.65);
    public static final Move BUZZ = new Move("Buzz", 0, false, 0.8);
    public static final Move DURA = new Move("Dura", 5, false, 0.8);
    public static final Move MIGHTY_PECK = new Move("Mighty Peck", 20, false, 0.7);

    public static class Move {
        private final String name;
        private final int damage;
        private final boolean flee;
        private final double chance;
        private final Effect effect;

        public Move(String name, int damage, boolean flee) {
            this(name, damage, flee, 0, null);
        }

        public Move(String name, int damage, boolean flee, double chance) {
            this(name, damage, flee, chance, null);
        }

        public Move(String name, int damage, boolean flee, double chance, Effect effect) {
            this.name = name;
            this.damage = damage;
            this.flee = flee;
            this.chance = chance;
            this.effect = effect;
        }

        public String getName() {
            return name;
        }

        public int getDamage() {
            return damage;
        }

        public boolean isFlee() {
            return flee;
        }

        public double getChance() {
            return chance;
        }

        public Effect getEffect() {
            return effect;
        }
    }

    public enum Effect {
        RABIES,
        MULTIPLY_DAMAGE,
        DODGE_NEXT_MOVE,
        REPEAT_MOVE,
        DENGUE,
        HEAL_HP,
        BLOCK_MOVE
    }

    public static Move[] getMoves(String pokeKalye) {
        switch (pokeKalye) {
            case "Puspin":
                return new Move[] { KALMOT, FLEE, PURR };
            case "Askal":
                return new Move[] { BITE, TAHOL, SPEAR };
            case "Kuting":
                return new Move[] { KALMOT, FLEE };
            case "Puspin Boots":
                return new Move[] { HISS_PUSPIN, FLEE, PURR, SNEAK_ATTACK };
            case "Tuta":
                return new Move[] { BITE_TUTA, TAHOL };
            case "Big Dog":
                return new Move[] { FLEE, TAHOL, SPEAR, OUTRAGE };
            case "Langgam":
                return new Move[] { KAGAT, BURROW };
            case "Antik":
                return new Move[] { KAGAT, BURROW, GANG_UP };
            case "Ant-Man":
                return new Move[] { FLEE, BURROW_ANTMAN, GANG_UP, QUANTUM_BITE };
            case "Ipis":
                return new Move[] { SCRATCH, DAPO, FLEE };
            case "Flying Ipis":
                return new Move[] { SCRATCH, DAPO_FLYING, LIPAD };
            case "Daga":
                return new Move[] { NGATNGAT, SQUEAK };
            case "Lamok":
                return new Move[] { DAPO_LAMOK, SUCK };
            case "Butiki":
                return new Move[] { LICK, SIT_AND_WAIT };
            case "Ibon":
                return new Move[] { PECK, POOP };
            case "Colored Sisiw":
                return new Move[] { PECK };
            case "Salagubang":
                return new Move[] { HARDEN, KUROT };
            case "Dagang Kanal":
                return new Move[] { KAGAT_DAGANG_KANAL, SQUEAK_KANAL };
            case "Langaw":
                return new Move[] { FLEE, DAPO };
            case "Bangaw":
                return new Move[] { FLEE, DAPO };
            case "Tuko":
                return new Move[] { CROAK, LICK };
            case "Manok":
                return new Move[] { TIKTILAOK, PECK, LIPAD, FLEE };
            case "Gagamba":
                return new Move[] { DAPO, KAGAT };
            case "Paniki":
                return new Move[] { LIPAD, KAGAT };
            case "Tutubi":
                return new Move[] { LIPAD, DAPO };
            case "Ahas":
                return new Move[] { SLITHER, KAGAT_SNAKE, HISS };
            case "Paro-paro":
                return new Move[] { LIPAD, DAPO };
            case "Higad":
                return new Move[] { DAPO_HIGAD };
            case "Tipaklong":
                return new Move[] { KARATE_CHOP, DAPO };
            case "Mandarangkal":
                return new Move[] { KARATE_CHOP, DAPO };
            case "Kabayo":
                return new Move[] { TADYAK };
            case "Professor Splinter":
                return new Move[] { NGATNGAT_PROFESSOR_SPLINTER, MEDITATE, SEWER_FOCUS, RAT_ATTACK };
            case "Palaka":
                return new Move[] { CROAK, LICK };
            case "Butete":
                return new Move[] { DURA };
            case "Uod":
                return new Move[] { DAPO, SLITHER };
            case "Suso":
                return new Move[] { DAPO, SIT_AND_WAIT };
            case "Isda":
                return new Move[] { SIT_AND_WAIT, DURA };
            case "Eagul":
                return new Move[] { MIGHTY_PECK, LIPAD, KALMOT };
            case "Bubuyog":
                return new Move[] { STING, BUZZ };
            case "Kitty Yonarchy":
                return new Move[] { SNEAK_ATTACK, PURR, KAGAT };
            case "Lolong":
                return new Move[] { OUTRAGE, KAGAT, SEWER_FOCUS };
            case "THE GOAT":
                return new Move[] { TADYAK, BURROW, QUANTUM_BITE, SPEAR };
            default:
                System.out.print("Unknown Poke");
                return new Move[0];
        }
    }
}
