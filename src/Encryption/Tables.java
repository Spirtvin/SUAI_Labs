package Encryption.TruthTables;

public class Tables {
    public static Boolean OR(Boolean a, Boolean b) {
        return a || b;
    }

    public static Boolean AND(Boolean a, Boolean b) {
        return a && b;
    }

    public static Boolean XOR(Boolean a, Boolean b) {
        if (a && b)
            return false;
        return OR(a, b);
    }
}
