package com.example.ormculinaryacadamy.Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import com.example.ormculinaryacadamy.Bo.BoFactory;
import com.example.ormculinaryacadamy.Bo.Custom.CourseBo;
import com.example.ormculinaryacadamy.Dao.Custom.CourseDao;
import com.example.ormculinaryacadamy.Dao.DaoFactory;
import com.example.ormculinaryacadamy.Dto.CourseDto;
import com.example.ormculinaryacadamy.Entity.Course;
import com.example.ormculinaryacadamy.EntityTm.CourseTm;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class CourseFormController {

    @FXML
    private AnchorPane anpCourse;

    @FXML
    private Button btnClear;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnUpdate;

    @FXML
    private TableColumn<?, ?> colDuration;

    @FXML
    private TableColumn<?, ?> colFree;

    @FXML
    private TableColumn<?, ?> colId;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private TableView<CourseTm> tblCourse;

    @FXML
    private TextField txtDuration;

    @FXML
    private TextField txtFree;

    @FXML
    private TextField txtId;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtSearch;

    CourseBo courseBo = (CourseBo) BoFactory.getBoFactory().getBoType(BoFactory.BoType.COURSE);
    ObservableList<CourseTm> courseObservableList = FXCollections.observableArrayList();



    public void initialize() throws IOException {
        setCellValueFactory();
        setTable();
        selectTableRow();
        clearFields();
        generateNewId();
        filterCourse();
    }

    CourseDao courseDao = (CourseDao) DaoFactory.getDaoFactory().getDaoType(DaoFactory.DaoType.COURSE);

    private String generateNewId() throws IOException {
        String nextId = courseDao.getCurrentId();
        txtId.setText(nextId);
        return nextId;
    }

    private void setCellValueFactory() {
        colId.setCellValueFactory(new PropertyValueFactory<>("course_id"));
        colFree.setCellValueFactory(new PropertyValueFactory<>("course_fee"));
        colName.setCellValueFactory(new PropertyValueFactory<>("course_name"));
        colDuration.setCellValueFactory(new PropertyValueFactory<>("duration"));
    }

    private void setTable() throws IOException {
        courseObservableList.clear();
        List<Course> courseList = courseBo.getCourseList();
        for (Course course : courseList) {
            CourseTm courseTm =  new CourseTm(course.getCourse_id(),course.getCourse_name(),course.getDuration(),course.getCourse_fee());
            courseObservableList.add(courseTm);
        }
        tblCourse.setItems(courseObservableList);
    }

    private void clearFields() throws IOException {
        txtId.clear();
        txtName.clear();
        txtFree.clear();
        txtDuration.clear();
    }

    private void selectTableRow() {
        tblCourse.setOnMouseClicked(mouseEvent -> {
            int row = tblCourse.getSelectionModel().getSelectedIndex();
            CourseTm courseTm = tblCourse.getItems().get(row);
            txtId.setText(courseTm.getCourse_id());
            txtName.setText(courseTm.getCourse_name());
            txtFree.setText(String.valueOf(courseTm.getCourse_fee()));
            txtDuration.setText(courseTm.getDuration());
        });
    }

    private void filterCourse() {
        FilteredList<CourseTm> filterData = new FilteredList<>(courseObservableList, e -> true);

        txtSearch.textProperty().addListener((observableValue, oldValue, newValue) -> {
            filterData.setPredicate(course -> {
                if (newValue == null || newValue.isEmpty() || newValue.isBlank()) {
                    return true;
                }

                String searchKeyword = newValue.toLowerCase();
                if (course.getCourse_id().toLowerCase().contains(searchKeyword)) {
                    return true;
                } else if (course.getCourse_name().toLowerCase().contains(searchKeyword)) {
                    return true;
                }
                return false;
            });
        });

        SortedList<CourseTm> courseTmSortedList = new SortedList<>(filterData);
        courseTmSortedList.comparatorProperty().bind(tblCourse.comparatorProperty());
        tblCourse.setItems(courseTmSortedList);
    }
    @FXML
    void btnClearOnAction(ActionEvent event) throws IOException {
        clearFields();
        generateNewId();

    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) throws IOException {
        ButtonType yes = new ButtonType("Yes",ButtonBar.ButtonData.OK_DONE);
        ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);
        Optional<ButtonType> result = new Alert(Alert.AlertType.INFORMATION, "Are you sure to remove?", yes, no).showAndWait();

        if(result.orElse(no) == yes) {
            if (courseBo.delete(txtId.getText())) {
                new Alert(Alert.AlertType.CONFIRMATION, "User Deleted Successfully!").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "SQL Error").show();
            }
        }
        clearFields();
        setTable();
        generateNewId();
    }

    //Save

    @FXML
    void btnSaveOnAction(ActionEvent event) throws IOException {
        String id = txtId.getText();
        String name = txtName.getText();
        String duration = txtDuration.getText();
        double free = Double.parseDouble(txtFree.getText());

        CourseDto courseDto = new CourseDto(id, name, duration, free);
        if (courseBo.save(courseDto)){
            new Alert(Alert.AlertType.CONFIRMATION,"Course Added Successfully").show();
        }else {
            new Alert(Alert.AlertType.ERROR,"Course Not Added Successfully").show();
        }
        clearFields();
        setTable();
        generateNewId();
    }

    //update
    @FXML
    void btnUpdateOnAction(ActionEvent event) throws IOException {
        String id = txtId.getText();
        String name = txtName.getText();
        String duration = txtDuration.getText();
        double free = Double.parseDouble(txtFree.getText());

        CourseDto courseDto = new CourseDto(id, name, duration, free);
        if (courseBo.update(courseDto)){
            new Alert(Alert.AlertType.CONFIRMATION,"Course Updated Successfully").show();
        }else {
            new Alert(Alert.AlertType.ERROR,"Course Not Updated Successfully").show();
        }
        clearFields();
        setTable();
        generateNewId();
    }



    @FXML
    void txtNameOnAction(ActionEvent event) {

    }

    @FXML
    void txtDurationOnAction(ActionEvent event) {

    }

    @FXML
    void txtFreeOnAction(ActionEvent event) {

    }

    @FXML
    void txtIdOnAction(ActionEvent event) {

    }

}
