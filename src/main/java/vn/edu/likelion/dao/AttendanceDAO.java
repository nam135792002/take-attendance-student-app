package vn.edu.likelion.dao;

import vn.edu.likelion.config.ConnectionDB;
import vn.edu.likelion.entity.Attendance;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AttendanceDAO {
    ConnectionDB connectionDB = new ConnectionDB();
    PreparedStatement preparedStatement = null;

    public void insertAttendanceIntoDB(Attendance attendance){
        String query = "INSERT INTO ATTENDANCE(DATE_ATTENDANCE,STATUS,STUDENT_ID) VALUES(?,?,?)";
        connectionDB.connect();

        try {
            preparedStatement = connectionDB.getConnection().prepareStatement(query);
            preparedStatement.setDate(1, Date.valueOf(attendance.getTakeDate()));
            preparedStatement.setBoolean(2, attendance.isStatus());
            preparedStatement.setInt(3, attendance.getStudent().getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        connectionDB.disconnect();
    }
}
