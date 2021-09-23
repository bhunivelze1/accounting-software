package account;

import ledger.LedgerEntry;
import javafx.collections.FXCollections;

import javafx.collections.ObservableList;

public class Account {

    int number;
    String name;
    AccountType type;
    Polarity normal;
    String normalString;
    ObservableList<LedgerEntry> entries = FXCollections.observableArrayList();

    public Account(int number, String name, AccountType type, Polarity normalValue) {
        this.number = number;
        this.name = name;
        this.type = type;
        this.normal = normalValue;

        switch (normalValue) {
            case Debit:
                this.normalString = "Debit";
                break;
            case Credit:
                this.normalString = "Credit";
                break;
        }
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

    public Polarity getNormal() {
        return normal;
    }

    public void setNormal(Polarity normalValue) {
        this.normal = normalValue;
    }

    public String getNormalString() {
        return normalString;
    }

    public LedgerEntry getEntry(int index) {
        return entries.get(index);
    }

    public ObservableList<LedgerEntry> getAllEntry() {
        return entries;
    }

    public void addEntry(LedgerEntry entry) {
        LedgerEntry tempEntry = entry;
        if (this.entries.isEmpty()) {
            switch (tempEntry.getTransactionType()) {
                case Debit:
                    tempEntry.setTotalValue(tempEntry.getDebitValue());
                    break;
                case Credit:
                    tempEntry.setTotalValue(tempEntry.getCreditValue());
                    break;
            }
            
            tempEntry.setTotalString("Rp" + String.format("%,d", tempEntry.getTotalValue()));
            this.entries.add(tempEntry);
            
        } else if (!this.entries.isEmpty()) {
            tempEntry.setTotalValue(calculateTotal(this.entries.get(this.entries.size()-1), tempEntry));
            tempEntry.setTotalString("Rp" + String.format("%,d", tempEntry.getTotalValue()));
            this.entries.add(tempEntry);
        } 
    }
    
    public int calculateTotal(LedgerEntry lastEntry, LedgerEntry currentEntry) {
        switch(this.normal) {
            case Debit:
                switch (currentEntry.getTransactionType()) {
                    case Debit:
                        return lastEntry.getTotalValue() + currentEntry.getDebitValue();
                    case Credit:
                        return lastEntry.getTotalValue() - currentEntry.getCreditValue();
                }
                break;
            case Credit:
                switch (currentEntry.getTransactionType()) {
                    case Debit:
                        return lastEntry.getTotalValue() - currentEntry.getDebitValue();
                    case Credit:
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
