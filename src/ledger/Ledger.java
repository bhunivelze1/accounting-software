package ledger;

import account.Account;
import account.AccountList;
import java.util.ArrayList;
import java.util.Collections;

public class Ledger {
    
    static ArrayList<LedgerTable> ledgerTables = new ArrayList<>();
    
    public Ledger() {
        for (Account e : new AccountList().getAllAccounts()) {
            ledgerTables.add(new LedgerTable(e));
        }
    }
    
    public ArrayList<LedgerTable> getAllLedgerTables() {
        return this.ledgerTables;
    }
    
    public void addLedgerTable(Account account) {
        ledgerTables.add(new LedgerTable(account));
    }
    
    public void sortLedger() {
        Collections.sort(ledgerTables);
    }
}
