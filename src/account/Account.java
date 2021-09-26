package account;

import ledger.LedgerEntry;
import javafx.collections.FXCollections;

import javafx.collections.ObservableList;

public class Account {

    int number;
    String name;
    AccountType type;
    AccountPolarity normal;
    String normalString;
    int balance;
    String balanceString;

    ObservableList<LedgerEntry> entries = FXCollections.observableArrayList();

    public Account(int number, String name, AccountType type, AccountPolarity normalValue, int balance) {
        this.number = number;
        this.name = name;
        this.type = type;
        this.normal = normalValue;
        this.balance = balance;

        switch (normalValue) {
            case Debit:
                this.normalString = "Debit";
                break;
            case Kredit:
                this.normalString = "Kredit";
                break;
        }

        this.balanceString = Integer.toString(balance);
    }

    public int getNumber() {
        return number;
    }
    public void setNumber(int number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public AccountType getType() {
        return type;
    }
    public void setType(AccountType type) {
        this.type = type;
    }

    public AccountPolarity getNormal() {
        return normal;
    }
    public void setNormal(AccountPolarity normalValue) {
        this.normal = normalValue;
    }

    public String getNormalString() {
        return normalString;
    }

    public int getBalance() { return balance; }
    public void setBalance(int balance) { this.balance = balance; }

    public String getBalanceString() {
        return this.balanceString;
    }

    public LedgerEntry getEntry(int index) {
        return entries.get(index);
    }

    public ObservableList<LedgerEntry> getAllEntry() {
        return entries;
    }

    public void addEntry(LedgerEntry entry) {
        LedgerEntry tempLedgerEntry = entry;
        if (this.entries.isEmpty()) {
            switch (tempLedgerEntry.getTransactionType()) {
                case Debit:
                    tempLedgerEntry.setTotalValue(tempLedgerEntry.getDebitValue());
                    break;
                case Kredit:
                    tempLedgerEntry.setTotalValue(tempLedgerEntry.getCreditValue());
                    break;
            }

            tempLedgerEntry.setTotalString("Rp" + String.format("%,d", tempLedgerEntry.getTotalValue()));
            this.entries.add(tempLedgerEntry);

        } else if (!this.entries.isEmpty()) {
            balance += tempLedgerEntry.getDebitValue();
            this.balanceString = Integer.toString(balance);
            tempLedgerEntry.setTotalValue(calculateTotal(this.entries.get(this.entries.size()-1), tempLedgerEntry));
            tempLedgerEntry.setTotalString("Rp" + String.format("%,d", tempLedgerEntry.getTotalValue()));
            this.entries.add(tempLedgerEntry);
        }
    }

    public int calculateTotal(LedgerEntry lastEntry, LedgerEntry currentEntry) {
        switch(this.normal) {
            case Debit:
                switch (currentEntry.getTransactionType()) {
                    case Debit:
                        return lastEntry.getTotalValue() + currentEntry.getDebitValue();
                    case Kredit:
                        return lastEntry.getTotalValue() - currentEntry.getCreditValue();
                }
                break;
            case Kredit:
                switch (currentEntry.getTransactionType()) {
                    case Debit:
                        return lastEntry.getTotalValue() - currentEntry.getDebitValue();
                    case Kredit:
                        return lastEntry.getTotalValue() + currentEntry.getCreditValue();
                }
                break;
        }
        return 0;
    }
        @Override
    public String toString() {
        return this.name;
    }

}
