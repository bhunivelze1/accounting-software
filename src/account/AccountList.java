package account;

import java.util.ArrayList;
import java.util.Arrays;

public class AccountList {
    static ArrayList<Account> accounts = new ArrayList(Arrays.asList(
            new Account(111, "Kas", AccountType.Asset, Polarity.Debit, 0),
            new Account(112, "Piutang", AccountType.Asset, Polarity.Debit, 0),
            new Account(113, "Persediaan", AccountType.Asset, Polarity.Debit, 0),
            new Account(114, "Perlengkapan", AccountType.Asset, Polarity.Debit, 0),
            new Account(121, "Peralatan", AccountType.Asset, Polarity.Debit, 0),
            new Account(122, "Kendaraan", AccountType.Asset, Polarity.Debit, 0),
            new Account(123, "Bangunan", AccountType.Asset, Polarity.Debit, 0),
            new Account(124, "Tanah", AccountType.Asset, Polarity.Debit, 0),
            new Account(211, "Utang Usaha", AccountType.Liability, Polarity.Credit, 0),
            new Account(212, "Utang Wesel", AccountType.Liability, Polarity.Credit, 0),
            new Account(221, "Utang Bank", AccountType.Liability, Polarity.Credit, 0),
            new Account(311, "Modal", AccountType.Equity, Polarity.Credit, 0),
            new Account(411, "Pendapatan Jasa", AccountType.Income, Polarity.Credit, 0),
            new Account(421, "Pendapatan Bunga", AccountType.Income, Polarity.Credit, 0),
            new Account(422, "Pendapatan Lain-Lain", AccountType.Income, Polarity.Credit, 0),
            new Account(511, "Beban Gaji", AccountType.Expense, Polarity.Debit, 0)));
    
    public ArrayList<Account> getAllAccounts() {
        return accounts;
    }
    
    public Account get(int accountNumber) {
        return accounts.get(accountNumber);
    }
}
