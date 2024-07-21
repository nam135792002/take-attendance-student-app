package vn.edu.likelion.dao;

import vn.edu.likelion.config.ConnectionDB;
import vn.edu.likelion.entity.Attendance;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class AttendanceDAO {
    ConnectionDB connectionDB = new ConnectionDB();
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;

    public void insertAttendanceIntoDB(Attendance attendance){
        String query = "INSERT INTO ATTENDANCE(DATE_ATTENDANCE,STATUS,STUDENT_ID,USER_ID) VALUES(?,?,?,?)";
        connectionDB.connect();

        try {
            preparedStatement = connectionDB.getConnection().prepareStatement(query);
            preparedStatement.setDate(1, Date.valueOf(attendance.getTakeDate()));
            preparedStatement.setBoolean(2, attendance.isStatus());
            preparedStatement.setInt(3, attendance.getStudent().getId());
            preparedStatement.setInt(4, attendance.getUser().getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectionDB.disconnect();
        }
    }

    public boolean checkAttendanceByDate(LocalDate date){
        connectionDB.connect();
        String query = "SELECT * FROM ATTENDANCE WHERE DATE_ATTENDANCE = TO_DATE(?,'DD-MM-YYYY')";

        try {
            preparedStatement = connectionDB.getConnection().prepareStatement(query);
            preparedStatement.setDate(1, Date.valueOf(Date.valueOf(date).toLocalDate()));
            resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                System.out.println(resultSet.getInt(1));
                return true;
            }
            return false;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            connectionDB.disconnect();
        }
    }
}
