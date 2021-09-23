package ui;

import account.Account;
import account.AccountList;
import javafx.geometry.Pos;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class InputBlock extends VBox {
    
    AccountList accounts = new AccountList();
    
    ComboBox<Account> accountComboBox = new ComboBox<>();
    RadioButton autoInputToggle = new RadioButton("Auto");
    TextField valueField = new TextField();
    
    public InputBlock() {
        super();
                
        accountComboBox.getItems().addAll(accounts.getAllAccounts());
        valueField.setPromptText("Nilai");
        
        HBox row = new HBox(5, accountComboBox, autoInputToggle);
        row.setAlignment(Pos.CENTER_LEFT);
        
        super.setAlignment(Pos.CENTER_LEFT);
        super.setSpacing(5);
        super.getChildren().addAll(row, valueField);
    }

    public ComboBox<Account> getAccountComboBox() {
        return accountComboBox;
    }
    
    public Account getSelectedAccount() {
        return accountComboBox.getSelectionModel().getSelectedItem();
    }
    
    public String getSelectedAccountName() {
        return accountComboBox.getSelectionModel().getSelectedItem().getName();
    }
    
    public RadioButton getAutoInputToggle() {
        return autoInputToggle;
    }

    public TextField getInputValueField() {
        return valueField;
    }
    
    public String getInputValue() {
        return valueField.getText();
    }
}
