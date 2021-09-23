package ui;

import account.Account;
import account.AccountList;
import static account.AccountType.Asset;
import static account.AccountType.Equity;
import static account.AccountType.Expense;
import static account.AccountType.Income;
import static account.AccountType.Liability;
import account.Polarity;
import journal.JournalEntry;
import ledger.Ledger;
import ledger.LedgerEntry;
import ledger.LedgerTable;
import trialBalance.TrialBalanceTableEntry;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class AccountingSoftware extends Application {

    MenuItem newFile, loadFile, saveFile, exitApp, companyDetails, companyAccounts, aboutDeveloper;

    DatePicker datePicker;
    TextField descriptionField;
    ToggleButton toggleAllAutoInput;
    Button debitBlockAdder, creditBlockAdder, debitBlockRemover, creditBlockRemover, trialBalanceButton;

    ListView debitList, creditList;
    ArrayList<InputBlock> debitInputBlocks = new ArrayList<>();
    ArrayList<InputBlock> creditInputBlocks = new ArrayList<>();
    HBox debitBlockAdderRemoverRow, creditBlockAdderRemoverRow;
    TextField debitTotalField, creditTotalField;

    AccountList accountList = new AccountList();
    Ledger ledger = new Ledger();

    TableView<JournalEntry> journal;

    VBox assetScrollPaneContent, liabilityScrollPaneContent, equityScrollPaneContent, incomeScrollPaneContent,                                        
            expenseScrollPaneContent, trialBalanceContent;

    public void addNewDebitBlock() {
        int adderRemoverPosition = debitList.getItems().indexOf(debitBlockAdderRemoverRow);
        debitInputBlocks.add(new InputBlock());
        debitList.getItems().remove(adderRemoverPosition);
        debitList.getItems().add(adderRemoverPosition,
                debitInputBlocks.get(debitInputBlocks.size() - 1));
        debitList.getItems().add(adderRemoverPosition + 1, debitBlockAdderRemoverRow);
    }

    @Override
    public void start(Stage primaryStage) {

        newFile = new MenuItem("New");
        loadFile = new MenuItem("Load");
        loadFile.setOnAction(e -> new FileChooser().showOpenDialog(primaryStage));
        saveFile = new MenuItem("Save");
        saveFile.setOnAction(e -> saveData());
        exitApp = new MenuItem("Exit");
        exitApp.setOnAction(e -> {
            primaryStage.close();
        });
        companyDetails = new MenuItem("Details");
        companyAccounts = new MenuItem("Accounts");
        companyAccounts.setOnAction(e -> openAccountManager());
        aboutDeveloper = new MenuItem("About Developer");
        aboutDeveloper.setOnAction(e -> openAboutDeveloper());

        Menu fileMenu = new Menu("File");
        fileMenu.getItems().addAll(newFile, loadFile, saveFile, new SeparatorMenuItem(), exitApp);
        Menu companyMenu = new Menu("Company");
        companyMenu.getItems().addAll(companyDetails, companyAccounts);
        Menu helpMenu = new Menu("Help");
        helpMenu.getItems().add(aboutDeveloper);

        //Date picker
        datePicker = new DatePicker();
        datePicker.setValue(LocalDate.now());
        //Description field
        descriptionField = new TextField();
        descriptionField.setPromptText("Keterangan");
        //Auto Input toggle button
        toggleAllAutoInput = new ToggleButton("Auto-Input");
        toggleAllAutoInput.setOnAction(e -> toggleAllAutoInput());
        //Debit & Credit label
        Label debitLabel = new Label("DEBIT");
        debitLabel.setFont(Font.font("Calibri", 18));
        Label creditLabel = new Label("KREDIT");
        creditLabel.setFont(Font.font("Calibri", 18));

        //Debit & Credit input block
        debitInputBlocks.add(new InputBlock());
        creditInputBlocks.add(new InputBlock());

        //Debit & Credit block remover
        debitBlockAdder = new Button("+");
        debitBlockAdder.setOnAction(e -> addNewDebitBlock());
        debitBlockRemover = new Button("-");
        debitBlockRemover.setOnAction(e -> removeLastDebitBlock());
        creditBlockAdder = new Button("+");
        creditBlockAdder.setOnAction(e -> addNewCreditBlock());
        creditBlockRemover = new Button("-");
        creditBlockRemover.setOnAction(e -> removeLastCreditBlock());

        //Button node
        Button submit = new Button("->");
        submit.setOnAction(e -> submitEntryToJournal());

        //Trial Balance button
        trialBalanceButton = new Button("Tambahkan");
        trialBalanceButton.setPrefWidth(200);
        trialBalanceButton.setOnAction(e -> updateTrialBalance());

        //Journal table date column
        TableColumn<JournalEntry, LocalDate> dateCol = new TableColumn("Tanggal");
        dateCol.setSortable(false);
        dateCol.setCellValueFactory(new PropertyValueFactory<>("date"));
        //Journal table account column
        TableColumn<JournalEntry, String> accountCol = new TableColumn("Akun");
        accountCol.setPrefWidth(250);
        accountCol.setSortable(false);
        accountCol.setCellValueFactory(new PropertyValueFactory<>("account"));
        //Journal table debit column
        TableColumn<JournalEntry, String> debitCol = new TableColumn("Debit");
        debitCol.setPrefWidth(150);
        debitCol.setSortable(false);
        debitCol.setCellValueFactory(new PropertyValueFactory<>("debitValue"));
        //Journal table credit column
        TableColumn<JournalEntry, String> creditCol = new TableColumn("Kredit");
        creditCol.setPrefWidth(150);
        creditCol.setSortable(false);
        creditCol.setCellValueFactory(new PropertyValueFactory<>("creditValue"));

        //Journal table
        journal = new TableView();
        journal.getColumns().addAll(dateCol, accountCol, debitCol, creditCol);

        //Debit block adder and remover button layout
        debitBlockAdderRemoverRow = new HBox(10, debitBlockRemover, debitBlockAdder);
        debitBlockAdderRemoverRow.setAlignment(Pos.CENTER);
        //Credit block adder and remover button layout
        creditBlockAdderRemoverRow = new HBox(10, creditBlockRemover, creditBlockAdder);
        creditBlockAdderRemoverRow.setAlignment(Pos.CENTER);

        //Debit list
        debitList = new ListView();
        debitList.setFixedCellSize(70);
        debitList.getItems().addAll(debitInputBlocks.get(0), debitBlockAdderRemoverRow);
        //Credit list
        creditList = new ListView();
        creditList.setFixedCellSize(70);
        creditList.getItems().addAll(creditInputBlocks.get(0), creditBlockAdderRemoverRow);

        HBox topInputRow = new HBox(5, datePicker, toggleAllAutoInput, descriptionField);
        topInputRow.setAlignment(Pos.CENTER_LEFT);

        //Debit column
        VBox debitColumn = new VBox(debitLabel, debitList);
        //Credit column
        VBox creditColumn = new VBox(creditLabel, creditList);
        //Button column
        VBox buttonColumn = new VBox(5, submit);
        buttonColumn.setAlignment(Pos.CENTER);

        assetScrollPaneContent = new VBox(10);
        liabilityScrollPaneContent = new VBox(10);
        equityScrollPaneContent = new VBox(10);
        incomeScrollPaneContent = new VBox(10);
        expenseScrollPaneContent = new VBox(10);

        trialBalanceContent = new VBox(trialBalanceButton);
        trialBalanceContent.setAlignment(Pos.CENTER);
        trialBalanceContent.setSpacing(5);
        trialBalanceContent.setPadding(new Insets(10, 0, 0, 0));

        //Journal/Ledger tabs
        TabPane ledger = new TabPane(
                new Tab("Aset", new ScrollPane(assetScrollPaneContent)),
                new Tab("Liabilitas", new ScrollPane(liabilityScrollPaneContent)),
                new Tab("Ekuitas", new ScrollPane(equityScrollPaneContent)),
                new Tab("Pendapatan", new ScrollPane(incomeScrollPaneContent)),
                new Tab("Beban", new ScrollPane(expenseScrollPaneContent)));
        ledger.setTabMinWidth(100);
        ledger.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);

        TabPane financialStatement = new TabPane(
                new Tab("Laba Rugi"),
                new Tab("Perubahan Modal"),
                new Tab("Neraca"));
        financialStatement.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);

        TabPane outputTab = new TabPane(new Tab("Jurnal", journal),
                new Tab("Buku Besar", ledger),
                new Tab("Neraca Saldo", trialBalanceContent),
                new Tab("Laporan Keuangan", financialStatement));
        outputTab.setTabMinWidth(150);
        outputTab.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);

        VBox inputColumn = new VBox(5, topInputRow, descriptionField,
                new HBox(10, debitColumn, creditColumn));

        HBox root = new HBox(10, inputColumn, buttonColumn, outputTab);
        root.setPadding(new Insets(10));

        Scene scene = new Scene(new VBox(new MenuBar(fileMenu, companyMenu, helpMenu), root));
        scene.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ENTER) {
                submitEntryToJournal();
            }
        });

        primaryStage.setTitle("Aplikasi Akuntansi");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public void removeLastDebitBlock() {

        if (debitInputBlocks.size() > 1) {
            int adderRemoverPosition = debitList.getItems().indexOf(debitBlockAdderRemoverRow);

            debitList.getItems().remove(adderRemoverPosition);
            debitList.getItems().remove(adderRemoverPosition - 1);
            debitList.getItems().add(adderRemoverPosition - 1, debitBlockAdderRemoverRow);
            debitInputBlocks.remove(debitInputBlocks.size() - 1);
        }
    }

    public void addNewCreditBlock() {
        int adderRemoverPosition = creditList.getItems().indexOf(creditBlockAdderRemoverRow);
        creditInputBlocks.add(new InputBlock());
        creditList.getItems().remove(adderRemoverPosition);
        creditList.getItems().add(adderRemoverPosition,
                creditInputBlocks.get(creditInputBlocks.size() - 1));
        creditList.getItems().add(adderRemoverPosition + 1, creditBlockAdderRemoverRow);
    }

    public void removeLastCreditBlock() {

        if (creditInputBlocks.size() > 1) {
            int adderRemoverPosition = creditList.getItems().indexOf(creditBlockAdderRemoverRow);

            creditList.getItems().remove(adderRemoverPosition);
            creditList.getItems().remove(adderRemoverPosition - 1);
            creditList.getItems().add(adderRemoverPosition - 1, creditBlockAdderRemoverRow);
            creditInputBlocks.remove(creditInputBlocks.size() - 1);
        }
    }

    public void toggleAllAutoInput() {
        if (toggleAllAutoInput.isSelected()) {
            for (InputBlock i : debitInputBlocks) {
                i.getAutoInputToggle().setSelected(true);
            }

            for (InputBlock i : creditInputBlocks) {
                i.getAutoInputToggle().setSelected(true);
            }
        } else {
            for (InputBlock i : debitInputBlocks) {
                i.getAutoInputToggle().setSelected(false);
            }

            for (InputBlock i : creditInputBlocks) {
                i.getAutoInputToggle().setSelected(false);
            }
        }
    }

    public void submitEntryToJournal() {

        //Insert the first debit entry into temporary entry list
        journal.getItems().add(new JournalEntry(
                datePicker.getValue(),
                debitInputBlocks.get(0).getSelectedAccountName(),
                Polarity.Debit,
                "Rp" + String.format("%,d", Integer.parseInt(debitInputBlocks.get(0).getInputValue()))));

        //If there is more than one debit entry, insert them into the journal table
        if (debitInputBlocks.size() > 1) {
            for (int i = 1; i < debitInputBlocks.size(); i++) {
                journal.getItems().add(new JournalEntry(
                        null,
                        debitInputBlocks.get(i).getSelectedAccountName(),
                        Polarity.Debit,
                        "Rp" + String.format(
                                "%,d", Integer.parseInt(debitInputBlocks.get(i).getInputValue()))));
            }
        }

        //Insert the first credit entry into temporary entry list
        journal.getItems().add(new JournalEntry(
                null,
                "    " + creditInputBlocks.get(0).getSelectedAccountName(),
                Polarity.Credit,
                "Rp" + String.format("%,d", Integer.parseInt(creditInputBlocks.get(0).getInputValue()))));

        //If there is more than one credit entry, insert them into the journal table
        if (creditInputBlocks.size() > 1) {
            for (int i = 1; i < creditInputBlocks.size(); i++) {
                journal.getItems().add(new JournalEntry(
                        null,
                        "    " + creditInputBlocks.get(i).getSelectedAccountName(),
                        Polarity.Credit,
                        "Rp" + String.format(
                                "%,d", Integer.parseInt(creditInputBlocks.get(i).getInputValue()))));
            }
        }

        //If description is specified, insert them into the journal table
        if (!descriptionField.getText().isEmpty()) {
            journal.getItems().add(new JournalEntry(
                    null,
                    "    " + "(" +  descriptionField.getText() + ")",
                    Polarity.Debit,
                    ""
            ));
        }

        //Add empty entry to temporary entry list. It act as a space in the journal table
        journal.getItems().add(new JournalEntry());
        submitEntryToAccounts();
        postEntryToLedger();

        //Clear the description field
        descriptionField.clear();
        
        //Clear the debit input block
        for (InputBlock i : debitInputBlocks) {
            i.getAccountComboBox().getSelectionModel().clearSelection();
            i.getInputValueField().clear();
        }

        //Clear the credit input block
        for (InputBlock i : creditInputBlocks) {
            i.getAccountComboBox().getSelectionModel().clearSelection();
            i.getInputValueField().clear();
        }
    }

    private void submitEntryToAccounts() {

        for (int x = 0; x < debitInputBlocks.size(); x++) {
            InputBlock tempInputBlock = debitInputBlocks.get(x);

            for (int i = 0; i < accountList.getAllAccounts().size(); i++) {
                Account tempAccount = accountList.getAllAccounts().get(i);

                if (tempInputBlock.getSelectedAccountName().equals(tempAccount.getName())) {
                    tempAccount.addEntry(new LedgerEntry(datePicker.getValue(), null,
                            Polarity.Debit,
                            tempInputBlock.getInputValue()));
                }
                
            }
        }

        for (int x = 0; x < creditInputBlocks.size(); x++) {
            InputBlock tempInputBlock = creditInputBlocks.get(x);

            for (int i = 0; i < accountList.getAllAccounts().size(); i++) {
                Account tempAccount = accountList.getAllAccounts().get(i);

                if (tempInputBlock.getSelectedAccountName().equals(tempAccount.getName())) {
                    tempAccount.addEntry(new LedgerEntry(datePicker.getValue(), null,
                            Polarity.Credit,
                            tempInputBlock.getInputValue()));
                }
            }
        }
    }

    private void postEntryToLedger() {

        ledger.sortLedger();
        assetScrollPaneContent.getChildren().clear();
        liabilityScrollPaneContent.getChildren().clear();
        equityScrollPaneContent.getChildren().clear();
        incomeScrollPaneContent.getChildren().clear();
        expenseScrollPaneContent.getChildren().clear();

        for (LedgerTable e : ledger.getAllLedgerTables()) {
            if (!e.getTableItems().isEmpty()) {
                switch (e.getAccountType()) {
                    case Asset:
                        assetScrollPaneContent.getChildren().add(e);
                        break;
                    case Liability:
                        liabilityScrollPaneContent.getChildren().add(e);
                        break;
                    case Equity:
                        equityScrollPaneContent.getChildren().add(e);
                        break;
                    case Income:
                        incomeScrollPaneContent.getChildren().add(e);
                        break;
                    case Expense:
                        expenseScrollPaneContent.getChildren().add(e);
                        break;
                }
            }
        }
    }
    
    public void updateTrialBalance() {
        
        trialBalanceContent.getChildren().clear();
        if (!trialBalanceButton.getText().equals("Perbarui")) trialBalanceButton.setText("Perbarui");
        
        TableColumn<TrialBalanceTableEntry, Integer> numberCol = new TableColumn<>("No. Akun");
        numberCol.setCellValueFactory(new PropertyValueFactory<>("number"));
        TableColumn<TrialBalanceTableEntry, String> nameCol = new TableColumn<>("Nama Akun");
        nameCol.setMinWidth(300);
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        TableColumn<TrialBalanceTableEntry, String> debitCol = new TableColumn<>("Debit");
        debitCol.setMinWidth(150);
        debitCol.setCellValueFactory(new PropertyValueFactory<>("debitString"));
        TableColumn<TrialBalanceTableEntry, String> creditCol = new TableColumn<>("Kredit");
        creditCol.setMinWidth(150);
        creditCol.setCellValueFactory(new PropertyValueFactory<>("creditString"));
        
        TableView<TrialBalanceTableEntry> trialBalanceTable = new TableView<>();
        trialBalanceTable.getColumns().addAll(numberCol, nameCol, debitCol, creditCol);
        
        int debitTotal = 0;
        int creditTotal = 0;
        
        for (Account e : accountList.getAllAccounts()) {
            LedgerEntry lastEntry;
            if (!e.getAllEntry().isEmpty()) {
                lastEntry = e.getAllEntry().get(e.getAllEntry().size()-1);
                TrialBalanceTableEntry trialBalanceTableEntry  = new TrialBalanceTableEntry(
                        e, lastEntry.getTotalValue());
                debitTotal += trialBalanceTableEntry.getDebitValue();
                creditTotal += trialBalanceTableEntry.getCreditValue();
                trialBalanceTable.getItems().add(trialBalanceTableEntry);
            }
        }
        
        Text balanceText = new Text();
        if (debitTotal==creditTotal) {
            balanceText.setText("BALANCE!");
            balanceText.setStyle("-fx-color: green");
        } else {
            balanceText.setText("TIDAK BALANCE!");
            balanceText.setStyle("-fx-color: red");
        }
        
        HBox bottomHLayout = new HBox(15, 
                trialBalanceButton,
                new Text("Total Debit: " + "Rp" + String.format("%,d", debitTotal)),
                new Text("Total Kredit: " + "Rp" + String.format("%,d", creditTotal)),
                balanceText);
        bottomHLayout.setAlignment(Pos.BOTTOM_LEFT);
        
        trialBalanceContent.getChildren().addAll(trialBalanceTable, bottomHLayout);
    }

    public void saveData() {

    }

    public void openAccountManager() {

        TableColumn<Account, Integer> numberCol = new TableColumn<>("Number");
        numberCol.setCellValueFactory(new PropertyValueFactory<>("number"));
        TableColumn<Account, String> nameCol = new TableColumn<>("Name");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        TableColumn<Account, String> normalCol = new TableColumn<>("Normal");
        normalCol.setCellValueFactory(new PropertyValueFactory<>("normalString"));
        TableColumn<Account, String> totalCol = new TableColumn<>("Total");

        TableView<Account> accountTableView = new TableView();
        accountTableView.getColumns().addAll(numberCol, nameCol, normalCol, totalCol);
        accountTableView.getItems().addAll(accountList.getAllAccounts());
        
        Button addAccountButton = new Button("Add Account");
        addAccountButton.setOnAction(e -> openAddAccountWindow());
        Button deleteAccountButton = new Button("Delete Account");
        
        HBox buttonRow = new HBox(5, addAccountButton, deleteAccountButton);
        buttonRow.setAlignment(Pos.CENTER);
        buttonRow.setPadding(new Insets(5));

        VBox root = new VBox(accountTableView, buttonRow);

        Stage accountManager = new Stage();
        accountManager.setTitle("Account Manager");
        accountManager.setResizable(false);
        accountManager.initModality(Modality.APPLICATION_MODAL);
        accountManager.setScene(new Scene(root, 400, 400));
        accountManager.show();
    }

    public void openAddAccountWindow() {
        Stage addAccountWindow = new Stage();

        TextField accountNumber = new TextField();
        accountNumber.setPrefWidth(50);
        TextField accountName = new TextField();
        ComboBox<String> accountType = new ComboBox<>();
        accountType.getItems().addAll(Arrays.asList("Aset", "Liabilitas", "Ekuitas", "Pendapatan", "Beban"));
        ComboBox<String> accountNormal = new ComboBox<>();
        accountNormal.getItems().addAll(Arrays.asList("Debit", "Kredit"));
        Button addButton = new Button("Add");
        Button cancelButton = new Button("Cancel");
        cancelButton.setOnAction(e -> addAccountWindow.close());

        GridPane grid = new GridPane();
        grid.setHgap(5);
        grid.setVgap(5);
        grid.setPadding(new Insets(5));
        grid.add(new Label("Nomor"), 0, 0);
        grid.add(accountNumber, 0, 1);
        grid.add(new Label("Nama"), 1, 0);
        grid.add(accountName, 1, 1);
        grid.add(new Label("Jenis"), 2, 0);
        grid.add(accountType, 2, 1);
        grid.add(new Label("Saldo Normal"), 3, 0);
        grid.add(accountNormal, 3, 1);

        HBox buttonRow = new HBox(5, addButton, cancelButton);
        buttonRow.setAlignment(Pos.CENTER);

        VBox root = new VBox(5, grid, buttonRow);

        addAccountWindow.setTitle("Add Account");
        addAccountWindow.setResizable(false);
        addAccountWindow.initModality(Modality.APPLICATION_MODAL);
        addAccountWindow.setScene(new Scene(root));
        addAccountWindow.show();
    }

    public void openDeleteAccountWindow() {

    }

    public void openAboutDeveloper() {
        Stage aboutDeveloperWindow = new Stage();
        aboutDeveloperWindow.setTitle("Account Manager");
        aboutDeveloperWindow.setResizable(false);
        aboutDeveloperWindow.initModality(Modality.APPLICATION_MODAL);
        aboutDeveloperWindow.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
