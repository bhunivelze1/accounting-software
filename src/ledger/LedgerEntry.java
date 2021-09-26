package ledger;

import account.AccountPolarity;
import java.time.LocalDate;

//TODO: Add account balance update in accout manager when a new entry submitted to ledger
public class LedgerEntry {
    
    LocalDate date;
    String description;
    AccountPolarity transactionType;
    int debitValue;
    int creditValue;
    int totalValue;
    String debitString;
    String creditString;
    String totalString;

    public LedgerEntry(LocalDate date, String description, AccountPolarity transactionType, String value) {
        this.date = date;
        this.description = description;
        this.transactionType = transactionType;
        
        switch(transactionType) {
            case Debit:
                this.debitString = "Rp" + String.format("%,d", Integer.parseInt(value));
                this.debitValue = Integer.valueOf(value);
                this.creditString = null;
                this.creditValue = 0;
                break;
            case Kredit:
                this.creditString = "Rp" + String.format("%,d", Integer.parseInt(value));
                this.creditValue = Integer.valueOf(value);
                this.debitString = null;
                this.debitValue = 0;
                break;
        }
        
    }

    public LocalDate getDate() {
        return this.date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public AccountPolarity getTransactionType() {
        return this.transactionType;
    }

    public void setTransactionType(AccountPolarity transactionType) {
        this.transactionType = transactionType;
    }

    public int getDebitValue() {
        return this.debitValue;
    }

    public void setDebitValue(int debitValue) {
        this.debitValue = debitValue;
    }

    public int getCreditValue() {
        return this.creditValue;
    }

    public void setCreditValue(int creditValue) {
        this.creditValue = creditValue;
    }

    public int getTotalValue() {
        return this.totalValue;
    }

    public void setTotalValue(int totalValue) {
        this.totalValue = totalValue;
    }

    public String getDebitString() {
        return this.debitString;
    }

    public void setDebitString(String debitValue) {
        this.debitString = debitValue;
    }

    public String getCreditString() {
        return this.creditString;
    }

    public void setCreditString(String creditValue) {
        this.creditString = creditValue;
    }

    public String getTotalString() {
        return this.totalString;
    }

    public void setTotalString(String totalString) {
        this.totalString = totalString;
    }
}
