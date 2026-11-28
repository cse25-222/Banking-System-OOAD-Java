package bw.ac.bank.view;

import bw.ac.bank.controller.BankingController;
import bw.ac.bank.controller.LoginController;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class LoginView {

    private VBox root;
    private TextField usernameField;
    private PasswordField passwordField;
    private Label messageLabel;
    private LoginController loginController = new LoginController();
    private Stage stage;

    public LoginView(Stage stage) {
        this.stage = stage;
        root = new VBox(15);
        root.setPadding(new Insets(20));

        Label title = new Label("Banking System - Login");
        title.setStyle("-fx-font-size: 22px; -fx-font-weight: bold;");
        Label subtitle = new Label("CSE202 OOAD - Gaborone Branch");
        subtitle.setStyle("-fx-font-size: 12px; -fx-text-fill: grey;");

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(10));

        usernameField = new TextField();
        usernameField.setPromptText("admin");
        usernameField.setText("admin");

        passwordField = new PasswordField();
        passwordField.setPromptText("admin123");
        passwordField.setText("admin123");

        messageLabel = new Label("");
        messageLabel.setStyle("-fx-text-fill: red;");

        Button loginBtn = new Button("Login");
        loginBtn.setDefaultButton(true);
        loginBtn.setOnAction(e -> doLogin());

        Label hint = new Label("Demo: admin / admin123");
        hint.setStyle("-fx-text-fill: grey; -fx-font-size: 11px;");

        grid.add(new Label("Username:"), 0, 0);
        grid.add(usernameField, 1, 0);
        grid.add(new Label("Password:"), 0, 1);
        grid.add(passwordField, 1, 1);
        grid.add(loginBtn, 1, 2);
        grid.add(messageLabel, 1, 3);

        root.getChildren().addAll(title, subtitle, grid, hint);
    }

    private void doLogin() {
        String result = loginController.login(usernameField.getText(), passwordField.getText());
        if (result.startsWith("SUCCESS")) {
            // PDF Flow: Login (GUI) -> Controller -> Open Main Banking View
            BankingController bankingController = new BankingController();
            BankingView bankingView = new BankingView();
            Scene bankingScene = new Scene(bankingView.getView(), 800, 700);
            stage.setTitle("Banking System - Dashboard");
            stage.setScene(bankingScene);
        } else {
            messageLabel.setText(result);
        }
    }

    public VBox getView() {
        return root;
    }
}