package com.example.ormculinaryacadamy.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import com.example.ormculinaryacadamy.Dao.Custom.UserDao;
import com.example.ormculinaryacadamy.Dao.DaoFactory;

import java.io.IOException;

public class LoginFormController {

    public Button btnForgotPassword;
    public PasswordField pssword;
    @FXML
    private AnchorPane anpDashboard;

    @FXML
    private Button btnLogin;

    @FXML
    private TextField txtPassword;

    @FXML
    private TextField txtUsername;

    UserDao userDao = (UserDao) DaoFactory.getDaoFactory().getDaoType(DaoFactory.DaoType.USER);
    String username;

    @FXML
    void btnLoginOnAction(ActionEvent event) throws IOException {
        username = txtUsername.getText();
        String password = txtPassword.getText();

        boolean isAuthenticated = userDao.checkCredential(username, password);

        if (isAuthenticated) {
            navigateToTheDashboard();
        } else {
            new Alert(Alert.AlertType.ERROR, "Invalid username or password!").show();
        }
    }

    private void navigateToTheDashboard() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/MenuForm.fxml"));
        AnchorPane anchorPane = loader.load();

        MainFormController mainFormController = loader.getController();
        mainFormController.setUsername(username);

        Scene scene = new Scene(anchorPane);
        Stage stage = new Stage();

        stage.setScene(scene);
        stage.centerOnScreen();
        stage.setTitle("Dashboard Form");
        stage.show();

        anpDashboard.getScene().getWindow().hide();
    }

    public void btnRegisterOnAction(ActionEvent actionEvent) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/user-form.fxml"));
        Stage stage = new Stage();

        stage.setScene(new Scene(anchorPane));
        stage.setTitle("Register Form");
        stage.centerOnScreen();
        stage.show();

    }


    }

