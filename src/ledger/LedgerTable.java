package ledger;

import account.Account;
import account.AccountType;
import java.time.LocalDate;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class LedgerTable extends VBox implements Comparable<LedgerTable> {
    Account account;
    TableView<LedgerEntry> table;
    
    public LedgerTable(Account account) {
        super();
        
        this.account = account;
        Label accountLabel= new Label(String.valueOf(account.getNumber()) + " " + account.getName());
        accountLabel.setFont(new Font(20));
        
        TableColumn<LedgerEntry, LocalDate> dateCol = new TableColumn<>("Tanggal");
        dateCol.setSortable(false);
        dateCol.setCellValueFactory(new PropertyValueFactory<>("date"));
        
        TableColumn<LedgerEntry, String> accountCol = new TableColumn<>("Keterangan");
        accountCol.setPrefWidth(200);
        accountCol.setSortable(false);
        accountCol.setCellValueFactory(new PropertyValueFactory<>("account"));
        
        TableColumn<LedgerEntry, String> debitCol = new TableColumn<>("Debit");
        debitCol.setPrefWidth(100);
        debitCol.setSortable(false);
        debitCol.setCellValueFactory(new PropertyValueFactory<>("debitString"));
        
        TableColumn<LedgerEntry, String> creditCol = new TableColumn<>("Kredit");
        creditCol.setPrefWidth(100);
        creditCol.setSortable(false);
        creditCol.setCellValueFactory(new PropertyValueFactory<>("creditString"));
        
        TableColumn<LedgerEntry, String> balanceCol = new TableColumn<>("Saldo");
        balanceCol.setPrefWidth(100);
        balanceCol.setSortable(false);
        balanceCol.setCellValueFactory(new PropertyValueFactory<>("totalString"));
        
        table = new TableView<>();
        table.setMaxHeight(150);
        table.getColumns().addAll(dateCol, accountCol, debitCol, creditCol, balanceCol);
        table.setItems(account.getAllEntry());
        
        super.getChildren().addAll(accountLabel, table);
    }
    
    public String getAccountName() {
        return this.account.getName();
    }
    
    public AccountType getAccountType() {
        return this.account.getType();
    }
    
    public ObservableList<LedgerEntry> getTableItems() {
        return table.getItems();
    }

    @Override
    public int compareTo(LedgerTable ledgerTable) {
        return this.account.getNumber() - ledgerTable.account.getNumber();
    }
}