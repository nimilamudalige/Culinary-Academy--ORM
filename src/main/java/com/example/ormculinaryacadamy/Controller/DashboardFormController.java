package com.example.ormculinaryacadamy.Controller;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import com.example.ormculinaryacadamy.Dao.Custom.CourseDao;
import com.example.ormculinaryacadamy.Dao.Custom.StudentDao;
import com.example.ormculinaryacadamy.Dao.Custom.UserDao;
import com.example.ormculinaryacadamy.Dao.DaoFactory;
import javafx.util.Duration;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DashboardFormController {

    public Label lblDate;
    @FXML
    private AnchorPane anpDash;

    @FXML
    private Label lblStudent;

    @FXML
    private Label lblUser;

    @FXML
    private Label program;
    private int programCount;

    private int studentCount;

    private int userCount;

    CourseDao courseDao = (CourseDao) DaoFactory.getDaoFactory().getDaoType(DaoFactory.DaoType.COURSE);
    StudentDao studentDao = (StudentDao) DaoFactory.getDaoFactory().getDaoType(DaoFactory.DaoType.STUDENT);
    UserDao userDao = (UserDao) DaoFactory.getDaoFactory().getDaoType(DaoFactory.DaoType.USER);

    public void initialize () throws IOException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd           HH:mm:ss");

        // Create a timeline that updates the label every second
        Timeline clock = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            String formattedDateTime = LocalDateTime.now().format(formatter);
            lblDate.setText(formattedDateTime);
        }));

        clock.setCycleCount(Timeline.INDEFINITE); // Run indefinitely
        clock.play(); // Start the clock
        programCountCal();
        studentCountCal();
        userCountCal();
    }

    private void userCountCal() {
        userCount = userDao.getUserCount();
        setUserCount(userCount);
    }

    private void setUserCount(int userCount) {
        lblUser.setText(String.valueOf(userCount));
    }

    private void studentCountCal() {
        studentCount = studentDao.getStudentCount();
        setStudentCount(studentCount);
    }

    private void setStudentCount(int studentCount) {
        lblStudent.setText(String.valueOf(studentCount));
    }

    public void programCountCal(){
        programCount = courseDao.getProgramCount();
        setProgramCount(programCount);
    }

    public void setProgramCount(int programCount) {
        program.setText(String.valueOf(programCount));
    }
}
