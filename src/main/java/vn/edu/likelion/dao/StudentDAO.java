package vn.edu.likelion.dao;

import vn.edu.likelion.config.ConnectionDB;
import vn.edu.likelion.entity.Attendance;
import vn.edu.likelion.entity.Student;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class StudentDAO {
    ConnectionDB connectionDB = new ConnectionDB();
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    Student student = null;
    List<Student> listStudent = null;

    public void insertOneStudentIntoDB(Student student){
        connectionDB.connect();

        String queryTruncate = "TRUNCATE TABLE STUDENT";

        try {
            preparedStatement = connectionDB.getConnection().prepareStatement(queryTruncate);
            preparedStatement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        String query = "INSERT INTO STUDENT(ID,NAME) VALUES(?,?)";
        try {
            preparedStatement = connectionDB.getConnection().prepareStatement(query);

            preparedStatement.setInt(1, student.getId());
            preparedStatement.setString(2, student.getName());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectionDB.disconnect();
        }
    }

    public List<Student> takeListOfStudent(){
        String query = "SELECT * FROM STUDENT";
        connectionDB.connect();
        listStudent = new ArrayList<>();
        executeQuery(query, listStudent);
        connectionDB.disconnect();
        return listStudent;
    }

    public List<Student> takeListOfAbsentOrPresentStudent(Date date, boolean status){
        listStudent = new ArrayList<>();
        connectionDB.connect();
        executeQueryAbsentOrPresent(status, date, listStudent);
        connectionDB.disconnect();
        return listStudent;
    }

    public List<Student> takeListOfStudentByDate(Date date){
        listStudent = new ArrayList<>();
        connectionDB.connect();
        String query = "SELECT A.ID, A.NAME, B.STATUS " +
                "FROM STUDENT A " +
                "INNER JOIN ATTENDANCE B ON A.ID = B.STUDENT_ID " +
                "WHERE B.DATE_ATTENDANCE = TO_DATE(?,'DD-MM-YYYY')";
        try {
            preparedStatement = connectionDB.getConnection().prepareStatement(query);
            preparedStatement.setDate(1, date);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                student = new Student(resultSet.getInt(1), resultSet.getString(2), resultSet.getBoolean(3));
                listStudent.add(student);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        connectionDB.disconnect();
        return listStudent;
    }

    private void executeQuery(String query, List<Student> listStudent){
        try {
            preparedStatement = connectionDB.getConnection().prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                student = new Student(resultSet.getInt(1), resultSet.getString(2));
                listStudent.add(student);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void executeQueryAbsentOrPresent(boolean status, Date date, List<Student> listStudent){
        String query = "SELECT A.ID, A.NAME " +
                "FROM STUDENT A " +
                "INNER JOIN ATTENDANCE B ON A.ID = B.STUDENT_ID " +
                "WHERE B.STATUS = ? AND B.DATE_ATTENDANCE = TO_DATE(?,'DD-MM-YYYY')";
        try {
            preparedStatement = connectionDB.getConnection().prepareStatement(query);
            preparedStatement.setBoolean(1, status);
            preparedStatement.setDate(2, date);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                student = new Student(resultSet.getInt(1), resultSet.getString(2));
                listStudent.add(student);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
