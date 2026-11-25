package bw.ac.bank.view;

import bw.ac.bank.controller.BankingController;
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
    private TextField openingAmountField;
    private ComboBox<String> accountTypeBox;
    private Label balanceLabel;

    private BankingController controller = new BankingController();

    public BankingView() {
        root = new VBox(10);
        root.setPadding(new Insets(15));

        Label title = new Label("Banking System");
        title.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        TitledPane customerPane = new TitledPane("Customer", createCustomerForm());
        customerPane.setExpanded(true);
        TitledPane accountPane = new TitledPane("Account", createAccountForm());
        accountPane.setExpanded(true);
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
        customerNameField.setPromptText("e.g. Kagiso Suping");
        emailField = new TextField();
        emailField.setPromptText("e.g. kagiso@test.com");
        TextField addressField = new TextField();
        addressField.setPromptText("e.g. Gaborone");
        CheckBox employedBox = new CheckBox("Employed");
        TextField employerNameField = new TextField();
        employerNameField.setPromptText("e.g. Bank of Botswana");
        TextField employerAddressField = new TextField();
        employerAddressField.setPromptText("e.g. CBD, Gaborone");

        Button registerBtn = new Button("Register Customer");
        registerBtn.setOnAction(e -> {
            String full = customerNameField.getText().trim();
            String first = full.contains(" ") ? full.substring(0, full.indexOf(" ")) : full;
            String surname = full.contains(" ") ? full.substring(full.indexOf(" ") + 1) : "";
            showMessage(controller.registerCustomer(first, surname, addressField.getText(), emailField.getText(), employedBox.isSelected(), employerNameField.getText(), employerAddressField.getText()));
        });
        grid.add(new Label("Full Name:"), 0, 0); grid.add(customerNameField, 1, 0);
        grid.add(new Label("Email:"), 0, 1); grid.add(emailField, 1, 1);
        grid.add(new Label("Address:"), 0, 2); grid.add(addressField, 1, 2);
        grid.add(employedBox, 1, 3);
        grid.add(new Label("Employer Name:"), 0, 4); grid.add(employerNameField, 1, 4);
        grid.add(new Label("Employer Address:"), 0, 5); grid.add(employerAddressField, 1, 5);
        grid.add(registerBtn, 1, 6);
        return grid;
    }

    private GridPane createAccountForm() {
        GridPane grid = new GridPane();
        grid.setHgap(10); grid.setVgap(8); grid.setPadding(new Insets(10));
        accountNumberField = new TextField();
        accountNumberField.setPromptText("e.g. ACC001");
        openingAmountField = new TextField();
        openingAmountField.setPromptText("e.g. 1000.00");
        accountTypeBox = new ComboBox<>();
        accountTypeBox.getItems().addAll("SAVINGS", "INVESTMENT", "CHEQUE");
        accountTypeBox.setValue("SAVINGS");
        Button openBtn = new Button("Open Account");
        openBtn.setOnAction(e -> {
            String result = controller.openAccount(accountTypeBox.getValue(), accountNumberField.getText(), openingAmountField.getText());
            showMessage(result);
            balanceLabel.setText(controller.getBalance());
        });
        grid.add(new Label("Account Number:"), 0, 0); grid.add(accountNumberField, 1, 0);
        grid.add(new Label("Opening Amount:"), 0, 1); grid.add(openingAmountField, 1, 1);
        grid.add(new Label("Account Type:"), 0, 2); grid.add(accountTypeBox, 1, 2);
        grid.add(openBtn, 1, 3);
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
        depositBtn.setOnAction(e -> {
            showMessage(controller.deposit(amountField.getText()));
            balanceLabel.setText(controller.getBalance());
        });
        withdrawBtn.setOnAction(e -> {
            showMessage(controller.withdraw(amountField.getText()));
            balanceLabel.setText(controller.getBalance());
        });
        interestBtn.setOnAction(e -> {
            showMessage(controller.postInterest());
            balanceLabel.setText(controller.getBalance());
        });
        grid.add(new Label("Amount (BWP):"), 0, 0); grid.add(amountField, 1, 0);
        grid.add(depositBtn, 0, 1); grid.add(withdrawBtn, 1, 1);
        grid.add(interestBtn, 0, 2);
        return grid;
    }

    public void showMessage(String msg) {
        feedbackArea.appendText(msg + "\n");
    }

    public VBox getView() {
        return root;
    }
}