package com.example.ormculinaryacadamy.Controller;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import com.example.ormculinaryacadamy.Dao.Custom.UserDao;
import com.example.ormculinaryacadamy.Dao.DaoFactory;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class MainFormController {

    public Button btnSettings;
    @FXML
    private AnchorPane anpMain;

    @FXML
    private Button btnDashboard;

    @FXML
    private Button btnPayment;

    @FXML
    private Button btnProgram;

    @FXML
    private Button btnStudent;

    @FXML
    private Button btnUser;

    @FXML
    private Label lblCurrentUser;

    @FXML
    private Label lblDate;

    UserDao userDao = (UserDao) DaoFactory.getDaoFactory().getDaoType(DaoFactory.DaoType.USER);
    String username;
    private AnchorPane rootNode;

    public void initialize() throws IOException {
        loadDashboardForm();
    }
    public void loadDashboardForm() throws IOException {
        AnchorPane dashboardPane = FXMLLoader.load(this.getClass().getResource("/view/Dashboard.fxml"));

        anpMain.getChildren().clear();
        anpMain.getChildren().add(dashboardPane);
    }
    @FXML
    void btnDashboardOnAction(ActionEvent event) throws IOException {
        AnchorPane dashboardPane = FXMLLoader.load(this.getClass().getResource("/view/Dashboard.fxml"));

        anpMain.getChildren().clear();
        anpMain.getChildren().add(dashboardPane);
    }

    @FXML
    void btnPaymentOnAction(ActionEvent event) throws IOException {
        AnchorPane paymentPane = FXMLLoader.load(this.getClass().getResource("/view/payment-form.fxml"));

        anpMain.getChildren().clear();
        anpMain.getChildren().add(paymentPane);

    }

    @FXML
    void btnProgramOnAction(ActionEvent event) throws IOException {
        AnchorPane coursePane = FXMLLoader.load(this.getClass().getResource("/view/courses-form.fxml"));

        anpMain.getChildren().clear();
        anpMain.getChildren().add(coursePane);
    }

    @FXML
    void btnStudentOnAction(ActionEvent event) throws IOException {
        AnchorPane studentPane = FXMLLoader.load(this.getClass().getResource("/view/student-form.fxml"));

        anpMain.getChildren().clear();
        anpMain.getChildren().add(studentPane);
    }

    @FXML
    void btnUserOnAction(ActionEvent event) throws IOException {
        AnchorPane userPane = FXMLLoader.load(this.getClass().getResource("/view/user-form.fxml"));

        anpMain.getChildren().clear();
        anpMain.getChildren().add(userPane);
    }

    public void setUsername(String username) {
        this.username = username;
        String userRole = getUserRole(username);
        setUserRole(userRole);
    }

    private String getUserRole(String username) {
        return userDao.getUserRole(username); // Ensure getUserRole(username) exists in UserDao and returns the role as a String
    }

    private void setUserRole(String userRole) {
        if (userRole != null) {
            lblCurrentUser.setText(userRole); // Display the user role on the label
        } else {
            lblCurrentUser.setText("Role not found");
        }
    }

    public void btnLogOutOnAction(ActionEvent actionEvent) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/admin-login.fxml"));
        Stage stage = (Stage) anpMain.getScene().getWindow();

        stage.setScene(new Scene(anchorPane));
        stage.setTitle("login Form");
        stage.centerOnScreen();
    }
}
