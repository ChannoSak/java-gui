package com.gui.gui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class StudentController implements Initializable {

    Connection con = null;
    PreparedStatement st = null;
    ResultSet rs = null;
    @javafx.fxml.FXML
    private TextField tfName;
    @javafx.fxml.FXML
    private TextField tLastName;
    @FXML
    private TableColumn<Student, String> colCourse;

    @FXML
    private TableColumn<Student, String> colfName;

    @FXML
    private TableColumn<Student, Integer> colid;

    @FXML
    private TableColumn<Student, String> collName;
    @javafx.fxml.FXML
    private TextField tCourse;
    @javafx.fxml.FXML
    private Button btnSave;
    @javafx.fxml.FXML
    private Button btnUpdate;
    @javafx.fxml.FXML
    private Button btnDelete;
    @javafx.fxml.FXML
    private Button btnClear;
    @javafx.fxml.FXML
    private TableView<Student> table;
    int id = 0;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        showStudents();
    }
    public ObservableList<Student> getStudents(){
        ObservableList<Student> students = FXCollections.observableArrayList();
        String query = "SELECT * FROM students";
        con = DBConnection.getCon();
        try {
            st = con.prepareStatement(query);
            rs = st.executeQuery();
            while (rs.next()){
                Student st = new Student();
                st.setId(rs.getInt("id"));
                st.setFirstName(rs.getString("FirstName"));
                st.setLastName(rs.getString("LastName"));
                st.setCourse(rs.getString("COURSE"));
                students.add(st);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return students;
    }
    public void showStudents(){
        ObservableList<Student> list = getStudents();
        table.setItems(list);
        colid.setCellValueFactory(new PropertyValueFactory<Student, Integer>("id"));
        colfName.setCellValueFactory(new PropertyValueFactory<Student, String>("firstName"));
        collName.setCellValueFactory(new PropertyValueFactory<Student, String>("lastName"));
        colCourse.setCellValueFactory(new PropertyValueFactory<Student, String>("course"));
    }
    @javafx.fxml.FXML
    public void createStudent(ActionEvent actionEvent) {
        String insert = "INSERT INTO students(firstName, lastName, COURSE) VALUES(?, ?, ?)";
        con = DBConnection.getCon();
        try {
            st = con.prepareStatement(insert);
            st.setString(1, tfName.getText());
            st.setString(2, tLastName.getText());
            st.setString(3, tCourse.getText());
            st.executeUpdate();
            clear();
            showStudents();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML
    void getData(MouseEvent event) {
        Student student = table.getSelectionModel().getSelectedItem();
        id = student.getId();
        tfName.setText(student.getFirstName());
        tLastName.setText(student.getLastName());
        tCourse.setText(student.getCourse());
        btnSave.setDisable(true);
    }
    @javafx.fxml.FXML
    public void updateStudent(ActionEvent actionEvent) {

        String update = "update students set firstName = ?, lastName = ?,  COURSE = ? WHERE id = ?";
        con = DBConnection.getCon();
        try {
            st = con.prepareStatement(update);
            st.setString(1, tfName.getText());
            st.setString(2, tLastName.getText());
            st.setString(3, tCourse.getText());
            st.setInt(4, id);
            st.executeUpdate();
            clear();
            showStudents();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    void clear(){
        tfName.setText(null);
        tLastName.setText(null);
        tCourse.setText(null);
        btnSave.setDisable(false);
    }
    @javafx.fxml.FXML
    public void deleteStudent(ActionEvent actionEvent) {
        String delete = "delete from students where id = ?";
        con = DBConnection.getCon();
        try {
            st = con.prepareStatement(delete);
            st.setInt(1, id);
            st.executeUpdate();
            clear();
            showStudents();
        } catch (SQLException e) {
            throw new RuntimeException(e);

        }
    }

    @javafx.fxml.FXML
    public void clearField(ActionEvent actionEvent) {
        clear();
    }

}
