package journal;

import account.AccountPolarity;
import java.time.LocalDate;

public class JournalEntry {
    
    LocalDate date;
    String account;
    AccountPolarity transactionType;
    String debitValue;
    String creditValue;
    
    public JournalEntry(LocalDate date, String account, AccountPolarity transactionType, String value) {
        this.date = date;
        this.account = account;
        this.transactionType = transactionType;

        switch (transactionType) {
            case Debit:
                this.debitValue = value;
                this.creditValue = null;
                break;
            case Kredit:
                this.creditValue = value;
                this.debitValue = null;
                break;
        }
    }

    public JournalEntry() {
        date = null;
        account = null;
        transactionType = null;
        debitValue = null;
        creditValue = null;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public AccountPolarity getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(AccountPolarity transactionType) {
        this.transactionType = transactionType;
    }

    public String getDebitValue() {
        return debitValue;
    }

    public void setDebitValue(String debitValue) {
        this.debitValue = debitValue;
    }

    public String getCreditValue() {
        return creditValue;
    }

    public void setCreditValue(String creditValue) {
        this.creditValue = creditValue;
    }
    
    
}
