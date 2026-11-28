package bw.ac.bank.view;

import bw.ac.bank.controller.BankingController;
import bw.ac.bank.model.Account;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

public class StatementView {
    private VBox root;
    private TextArea statementArea;
    private ComboBox<String> accountBox;
    private BankingController controller;

    public StatementView(BankingController controller) {
        this.controller = controller;
        root = new VBox(10);
        root.setPadding(new Insets(15));
        Label title = new Label("Account Statement");
        title.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        accountBox = new ComboBox<>();
        accountBox.setPromptText("Select account");
        Button refreshBtn = new Button("Show Statement");
        refreshBtn.setOnAction(e -> showStatement());

        statementArea = new TextArea();
        statementArea.setEditable(false);
        statementArea.setPrefHeight(300);

        root.getChildren().addAll(title, accountBox, refreshBtn, statementArea);
        refreshList();
    }

    public void refreshList() {
        accountBox.getItems().clear();
        for (Account a : controller.getAccounts()) {
            accountBox.getItems().add(a.getAccountNumber());
        }
    }

    private void showStatement() {
        if (accountBox.getValue() == null) {
            statementArea.setText("Select an account first");
            return;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("=== BANK STATEMENT ===\n");
        sb.append("Account: ").append(accountBox.getValue()).append("\n");
        sb.append("Customer: ").append(controller.getCurrentCustomer() != null ? controller.getCurrentCustomer().getFullName() : "").append("\n");
        sb.append("Balance: ").append(controller.getBalance()).append("\n\n");
        sb.append("Transactions:\n");
        sb.append("No transactions recorded yet - add Transaction logging to Account class.\n");
        statementArea.setText(sb.toString());
    }

    public VBox getView() { return root; }
}
