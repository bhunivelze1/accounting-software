package trialBalance;

import account.Account;

public class TrialBalanceTableEntry {
    int number;
    String name;
    int debitValue;
    int creditValue;
    String debitString;
    String creditString;
    
    public TrialBalanceTableEntry(Account account, int value) {
        if (account == null) {
            this.name = "TOTAL";
        }
        
        this.number = account.getNumber();
        this.name = account.getName();
        switch (account.getNormal()) {
            case Debit:
                debitValue = value;
                debitString = "Rp" + String.format("%,d", value);
                creditValue = 0;
                break;
            case Credit:
                creditValue = value;
                creditString = "Rp" + String.format("%,d", value);
                debitValue = 0;
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

    public int getDebitValue() {
        return debitValue;
    }

    public void setDebitValue(int debitValue) {
        this.debitValue = debitValue;
    }

    public int getCreditValue() {
        return creditValue;
    }

    public void setCreditValue(int creditValue) {
        this.creditValue = creditValue;
    }

    public String getDebitString() {
        return debitString;
    }

    public void setDebitString(String debitString) {
        this.debitString = debitString;
    }

    public String getCreditString() {
        return creditString;
    }

    public void setCreditString(String creditString) {
        this.creditString = creditString;
    }
    
    
}
