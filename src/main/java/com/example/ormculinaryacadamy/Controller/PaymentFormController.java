package com.example.ormculinaryacadamy.Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import com.example.ormculinaryacadamy.Bo.BoFactory;
import com.example.ormculinaryacadamy.Bo.Custom.PaymentBo;
import com.example.ormculinaryacadamy.Bo.Custom.StudentBo;
import com.example.ormculinaryacadamy.Bo.Custom.StudentCourseBo;
import com.example.ormculinaryacadamy.Dao.Custom.PaymentDao;
import com.example.ormculinaryacadamy.Dao.Custom.StudentCourseDao;
import com.example.ormculinaryacadamy.Dao.DaoFactory;
import com.example.ormculinaryacadamy.Dto.PaymentDto;
import com.example.ormculinaryacadamy.Entity.Payment;
import com.example.ormculinaryacadamy.Entity.Student;
import com.example.ormculinaryacadamy.Entity.Student_Course;
import com.example.ormculinaryacadamy.EntityTm.PaymentTm;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PaymentFormController {

    public Label lblBalanceAmount;
    public ComboBox comboPayHistory;
    public Label lblUpfrontAmount;
    public Label lblStatus;
    public Label lblDate;
    @FXML
    private AnchorPane anpPayment;

    @FXML
    private Button btnConfirm;

    @FXML
    private Button btnSave;

    @FXML
    private TableColumn<?, ?> colBalancePay;

    @FXML
    private TableColumn<?, ?> colBtnRemove;

    @FXML
    private TableColumn<?, ?> colCourseId;

    @FXML
    private TableColumn<?, ?> colId;

    @FXML
    private TableColumn<?, ?> colStatus;

    @FXML
    private TableColumn<?, ?> colStudentCourseDetailId;

    @FXML
    private TableColumn<?, ?> colStudentId;

    @FXML
    private TableColumn<?, ?> colUpfrontPay;

    @FXML
    private ComboBox<String> comboCourses;

    @FXML
    private ComboBox<String> comboStudent;

    @FXML
    private TableView<PaymentTm> tblPayment;

    @FXML
    private TextField txtCoursefee;

    @FXML
    private TextField txtDate;

    @FXML
    private TextField txtId;

    @FXML
    private TextField txtPayAmount;

    @FXML
    private TextField txtStatus;

    @FXML
    private TextField txtStuCouDetail;

    StudentCourseDao studentCourseDao = (StudentCourseDao) DaoFactory.getDaoFactory().getDaoType(DaoFactory.DaoType.STUDENT_COURSE);
    StudentBo studentBo = (StudentBo) BoFactory.getBoFactory().getBoType(BoFactory.BoType.STUDENT);
    ArrayList<Student> studentArrayList = new ArrayList<>();
    PaymentDao paymentDao = (PaymentDao) DaoFactory.getDaoFactory().getDaoType(DaoFactory.DaoType.PAYMENT);
    ArrayList<Student_Course> studentCourseArrayList = new ArrayList<>();
    StudentCourseBo studentCourseBo = (StudentCourseBo) BoFactory.getBoFactory().getBoType(BoFactory.BoType.STUDENT_COURSE);
    ObservableList<String> studentObservableList = FXCollections.observableArrayList();
    ObservableList<PaymentTm> paymentTmObservableList = FXCollections.observableArrayList();
    PaymentBo paymentBo = (PaymentBo) BoFactory.getBoFactory().getBoType(BoFactory.BoType.PAYMENT);
    ArrayList<Payment> paymentArrayList = new ArrayList<>();
    ObservableList<String> paymentObservableList = FXCollections.observableArrayList();

    public void initialize() throws IOException {
        generateNewId();
        getAllStudentCourses();
        getAllStudent();
        getAllPayment();
        searchStudent();
        searchPayment();
        setDate();
        setCellValueFactory();
    }

    private void setTable() {

    }
    private void searchPayment() {
        for (Payment payment : paymentArrayList) {
            paymentObservableList.add(String.valueOf(payment.getStudent_course().getStudent_course_id()));
        }
        comboPayHistory.setItems(paymentObservableList);
    }

    private void getAllPayment() throws IOException {
        List<Payment> paymentList = paymentBo.getPaymentList();
        paymentArrayList = (ArrayList<Payment>) paymentList;
    }

    private void setCellValueFactory() {
        colId.setCellValueFactory(new PropertyValueFactory<>("pay_id"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        colStudentId.setCellValueFactory(new PropertyValueFactory<>("stu_id"));
        colCourseId.setCellValueFactory(new PropertyValueFactory<>("cou_id"));
        colUpfrontPay.setCellValueFactory(new PropertyValueFactory<>("upfront_amount"));
        colBalancePay.setCellValueFactory(new PropertyValueFactory<>("balance_amount"));
        colStudentCourseDetailId.setCellValueFactory(new PropertyValueFactory<>("student_course_id"));
        colBtnRemove.setCellValueFactory(new PropertyValueFactory<>("btnRemove"));
    }

    private void getAllStudent() throws IOException {
        List<Student> studentList = studentBo.getStudentList();
        studentArrayList = (ArrayList<Student>) studentList;
    }

    private void searchStudent() {
        for (Student student : studentArrayList) {
            studentObservableList.add(student.getStu_id());
        }
        comboStudent.setItems(studentObservableList);
    }

    private void setDate() {
        //  txtDate.setEditable(true);
        LocalDate now = LocalDate.now();
        txtDate.setText(String.valueOf(now));
    }

    private void getAllStudentCourses() throws IOException {
        List<Student_Course> studentCourseList = studentCourseBo.getStudentCourseList();
        studentCourseArrayList = (ArrayList<Student_Course>) studentCourseList;
    }

    private String generateNewId() throws IOException {
        String nextId = paymentDao.getCurrentId();
        txtId.setText(nextId);
        return nextId;
    }
    @FXML
    void btnConfirmOnAction(ActionEvent event) {
        if (txtId.getText().isEmpty() || comboCourses.getValue() == null || txtPayAmount.getText().isEmpty()) {
            new Alert(Alert.AlertType.WARNING, "Please fill in all required fields").show();
            return;
        }

        // Retrieve and process input data
        String id = txtId.getText();
        String courseId = comboCourses.getValue();
        String studentId = comboStudent.getValue();
        String status = txtStatus.getText();
        Long stu_cou_id = Long.valueOf(txtStuCouDetail.getText());
        double upFront;
        double getFee;

        try {
            upFront = Double.parseDouble(txtPayAmount.getText());
            getFee = Double.parseDouble(txtCoursefee.getText());
        } catch (NumberFormatException e) {
            new Alert(Alert.AlertType.ERROR, "Invalid number format for payment or course fee").show();
            return;
        }

        //  balance payment
        double balancePay = getFee - upFront;

        if (balancePay < 0) {
            new Alert(Alert.AlertType.WARNING, "Payment exceeds the course fee").show();
            return;
        }

        // Create a "Remove" button for the row
        Button btnRemove = new Button("Remove");
        btnRemove.setCursor(Cursor.HAND);

        // Set up the event for the "Remove" button
        btnRemove.setOnAction((e) -> {
            ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
            ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);

            Optional<ButtonType> type = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to remove?", yes, no).showAndWait();
            if (type.orElse(no) == yes) {
                int selectedIndex = tblPayment.getSelectionModel().getSelectedIndex();
                if (selectedIndex >= 0) {
                    paymentTmObservableList.remove(selectedIndex);
                    tblPayment.refresh();
                } else {
                    new Alert(Alert.AlertType.WARNING, "Please select an item to remove").show();
                }
            }
        });

        // Add new payment record to the Payment table
        PaymentTm paymentTm = new PaymentTm(id, status, upFront, balancePay, studentId,courseId,stu_cou_id,btnRemove);
        paymentTmObservableList.add(paymentTm);
        tblPayment.setItems(paymentTmObservableList);
        tblPayment.refresh();

    }

    @FXML
    void btnSaveOnAction(ActionEvent event) throws IOException {
        PaymentTm selectedPayment = tblPayment.getSelectionModel().getSelectedItem();

        if (selectedPayment == null) {
            new Alert(Alert.AlertType.WARNING, "Please select a payment to save").show();
            return;
        }

        Student_Course studentCourse = studentCourseDao.getStudentCourseById(Long.valueOf(txtStuCouDetail.getText()));

        PaymentDto paymentDto = new PaymentDto();
        paymentDto.setPay_id(txtId.getText());
        paymentDto.setStatus(txtStatus.getText());
        paymentDto.setBalance_amount(selectedPayment.getBalance_amount()); // Use value from the selected item
        paymentDto.setPay_amount(Double.parseDouble(txtPayAmount.getText()));
        paymentDto.setPay_date(txtDate.getText());
        paymentDto.setStudent_course(studentCourse);

        paymentBo.savePayment(paymentDto);

        new Alert(Alert.AlertType.INFORMATION, "Payment saved successfully").show();

    }

    @FXML
    void comboCoursesOnAction(ActionEvent event) {
        String selectedCourseName = comboCourses.getValue();
        String selectedStudentId = comboStudent.getValue();

        for (Student_Course studentCourse : studentCourseArrayList) {
            // Check if both the student ID and course name match
            if (selectedStudentId != null && selectedCourseName != null &&
                    selectedStudentId.equals(studentCourse.getStudent().getStu_id()) &&
                    selectedCourseName.equals(studentCourse.getCourse().getCourse_name())) {

                // Display course fee and student_course_id
                txtCoursefee.setText(String.valueOf(studentCourse.getCourse().getCourse_fee()));
                txtStuCouDetail.setText(String.valueOf(studentCourse.getStudent_course_id()));
                break;
            }
        }
    }

    @FXML
    void comboStudentOnAction(ActionEvent event) {
        String studentId = comboStudent.getValue();
        ObservableList<String> studentCourseObservableList = FXCollections.observableArrayList();

        // Loop through student courses and add matching course names to the list
        for (Student_Course studentCourse : studentCourseArrayList) {
            if (studentCourse.getStudent().getStu_id().equals(studentId)) {
                studentCourseObservableList.add(studentCourse.getCourse().getCourse_name());
            }
        }

        // Set the items for the comboCourses ComboBox
        comboCourses.setItems(studentCourseObservableList);

        // Optionally, set the first course as the selected value if the list is not empty
        if (!studentCourseObservableList.isEmpty()) {
            comboCourses.setValue(studentCourseObservableList.get(0));
        }
    }

    @FXML
    void txtDateOnAction(ActionEvent event) {

    }

    @FXML
    void txtIdOnAction(ActionEvent event) {

    }

    @FXML
    void txtPayAmountOnAction(ActionEvent event) {

    }

    @FXML
    void txtStatusOnAction(ActionEvent event) {

    }

    @FXML
    public void comboPayHistoryOnAction(ActionEvent actionEvent) {
        Long stu_cou_id = Long.valueOf(String.valueOf(comboPayHistory.getValue()));

        for (Payment payment : paymentArrayList) {
            if(payment.getStudent_course().getStudent_course_id().equals(stu_cou_id)) {
                lblBalanceAmount.setText(String.valueOf(payment.getBalance_amount()));
                lblDate.setText(payment.getPay_date());
                lblStatus.setText(payment.getStatus());
                lblUpfrontAmount.setText(String.valueOf(payment.getUpfront_amount()));
            }
        }
    }
}
