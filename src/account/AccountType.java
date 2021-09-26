package account;

import java.util.ArrayList;
import java.util.Arrays;

public enum AccountType {
    Aset, Liabilitas, Ekuitas, Pendapatan, Beban;

    public static ArrayList<String> getEnumValues() {
        return new ArrayList<>(Arrays.asList("Aset", "Liabilitas", "Ekuitas", "Pendapatan", "Beban"));
    }
}
