package bw.ac.bank.view;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;

public class BankingView {

    private VBox root;
    private TextArea feedbackArea;
    private TextField customerNameField;
    private TextField emailField;
    private TextField accountNumberField;
    private TextField amountField;
    private ComboBox<String> accountTypeBox;
    private Label balanceLabel;

    public BankingView() {
        root = new VBox(10);
        root.setPadding(new Insets(15));

        Label title = new Label("Banking System");
        title.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        // Customer section
        TitledPane customerPane = new TitledPane("Customer", createCustomerForm());
        customerPane.setExpanded(true);

        // Account section
        TitledPane accountPane = new TitledPane("Account", createAccountForm());
        accountPane.setExpanded(true);

        // Transaction section
        TitledPane transactionPane = new TitledPane("Transaction", createTransactionForm());
        transactionPane.setExpanded(true);

        balanceLabel = new Label("Balance: BWP 0.00");
        feedbackArea = new TextArea();
        feedbackArea.setEditable(false);
        feedbackArea.setPromptText("Messages will appear here...");
        feedbackArea.setPrefHeight(120);

        root.getChildren().addAll(title, customerPane, accountPane, transactionPane, balanceLabel, feedbackArea);
    }

    private GridPane createCustomerForm() {
        GridPane grid = new GridPane();
        grid.setHgap(10); grid.setVgap(8); grid.setPadding(new Insets(10));
        customerNameField = new TextField();
        emailField = new TextField();
        Button registerBtn = new Button("Register Customer");
        registerBtn.setOnAction(e -> showMessage("Customer registration will be connected in Step 5 - Controller"));
        grid.add(new Label("Full Name:"), 0, 0); grid.add(customerNameField, 1, 0);
        grid.add(new Label("Email:"), 0, 1); grid.add(emailField, 1, 1);
        grid.add(registerBtn, 1, 2);
        return grid;
    }

    private GridPane createAccountForm() {
        GridPane grid = new GridPane();
        grid.setHgap(10); grid.setVgap(8); grid.setPadding(new Insets(10));
        accountNumberField = new TextField();
        accountTypeBox = new ComboBox<>();
        accountTypeBox.getItems().addAll("SAVINGS", "INVESTMENT", "CHEQUE");
        accountTypeBox.setValue("SAVINGS");
        Button openBtn = new Button("Open Account");
        openBtn.setOnAction(e -> showMessage("Account opening will be connected in Step 5 - Controller"));
        grid.add(new Label("Account Number:"), 0, 0); grid.add(accountNumberField, 1, 0);
        grid.add(new Label("Account Type:"), 0, 1); grid.add(accountTypeBox, 1, 1);
        grid.add(openBtn, 1, 2);
        return grid;
    }

    private GridPane createTransactionForm() {
        GridPane grid = new GridPane();
        grid.setHgap(10); grid.setVgap(8); grid.setPadding(new Insets(10));
        amountField = new TextField();
        amountField.setPromptText("e.g. 1000.00");
        Button depositBtn = new Button("Deposit");
        Button withdrawBtn = new Button("Withdraw");
        Button interestBtn = new Button("Post Monthly Interest");
        Button historyBtn = new Button("View History");
        depositBtn.setOnAction(e -> showMessage("Deposit will be connected in Step 5 - Controller"));
        withdrawBtn.setOnAction(e -> showMessage("Withdraw will be connected in Step 5 - Controller"));
        interestBtn.setOnAction(e -> showMessage("Interest: Savings 0.05% | Investment 5% - will be connected in Step 5"));
        historyBtn.setOnAction(e -> showMessage("History will be connected in Step 6 - DAO"));
        grid.add(new Label("Amount (BWP):"), 0, 0); grid.add(amountField, 1, 0);
        grid.add(depositBtn, 0, 1); grid.add(withdrawBtn, 1, 1);
        grid.add(interestBtn, 0, 2); grid.add(historyBtn, 1, 2);
        return grid;
    }

    public void showMessage(String msg) {
        feedbackArea.appendText(msg + "\n");
    }

    public VBox getView() {
        return root;
    }
}