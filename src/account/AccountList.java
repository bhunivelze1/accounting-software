package account;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import ledger.LedgerEntry;

import java.util.ArrayList;
import java.util.Arrays;

public class AccountList {

    static ObservableList<Account> accounts = FXCollections.observableArrayList(Arrays.asList(
            new Account(111, "Kas", AccountType.Aset, AccountPolarity.Debit, 10000),
            new Account(112, "Piutang", AccountType.Aset, AccountPolarity.Debit, 20000),
            new Account(113, "Persediaan", AccountType.Aset, AccountPolarity.Debit, 0),
            new Account(114, "Perlengkapan", AccountType.Aset, AccountPolarity.Debit, 20000),
            new Account(121, "Peralatan", AccountType.Aset, AccountPolarity.Debit, 0),
            new Account(122, "Kendaraan", AccountType.Aset, AccountPolarity.Debit, 0),
            new Account(123, "Bangunan", AccountType.Aset, AccountPolarity.Debit, 0),
            new Account(124, "Tanah", AccountType.Aset, AccountPolarity.Debit, 0),
            new Account(211, "Utang Usaha", AccountType.Liabilitas, AccountPolarity.Kredit, 0),
            new Account(212, "Utang Wesel", AccountType.Liabilitas, AccountPolarity.Kredit, 0),
            new Account(221, "Utang Bank", AccountType.Liabilitas, AccountPolarity.Kredit, 0),
            new Account(311, "Modal", AccountType.Ekuitas, AccountPolarity.Kredit, 0),
            new Account(411, "Pendapatan Jasa", AccountType.Pendapatan, AccountPolarity.Kredit, 0),
            new Account(421, "Pendapatan Bunga", AccountType.Pendapatan, AccountPolarity.Kredit, 0),
            new Account(422, "Pendapatan Lain-Lain", AccountType.Pendapatan, AccountPolarity.Kredit, 0),
            new Account(511, "Beban Gaji", AccountType.Beban, AccountPolarity.Debit, 0)));
    
    public static ObservableList<Account> getAllAccounts() {
        return accounts;
    }
    
    public Account get(int accountNumber) {
        return accounts.get(accountNumber);
    }
}
