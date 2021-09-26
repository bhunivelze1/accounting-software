package account;

import java.util.ArrayList;
import java.util.Arrays;

public enum AccountPolarity {
    Debit, Kredit;

    public static ArrayList<String> getEnumValues() {
        return new ArrayList<>(Arrays.asList("Debit", "Kredit"));
    }
}