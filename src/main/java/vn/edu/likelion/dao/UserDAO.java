package vn.edu.likelion.dao;

import vn.edu.likelion.config.ConnectionDB;
import vn.edu.likelion.entity.Permission;
import vn.edu.likelion.entity.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

public class UserDAO {

    User user = null;
    Permission permission = null;

    public User checkExistUsernameAndPasswordInDB(String username, String password){
        ConnectionDB connectionDB = new ConnectionDB();
        PreparedStatement preparedStatement;
        ResultSet resultSet;

        connectionDB.connect();
        String query = "SELECT * FROM USERS WHERE USERNAME = ?";

        try {
            preparedStatement = connectionDB.getConnection().prepareStatement(query);
            preparedStatement.setString(1, username);
            resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                String passwordInDB = resultSet.getString(3);
                String passwordEncode = Base64.getEncoder().encodeToString(password.getBytes());
                if(passwordInDB.equals(passwordEncode)){
                    List<Permission> listPermission = takePermissionOfUser(resultSet.getInt(4));
                    user = new User(resultSet.getInt(1), resultSet.getString(2), listPermission);
                    return user;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectionDB.disconnect();
        }
        return null;
    }

    private List<Permission> takePermissionOfUser(Integer roleId){
        ConnectionDB connectionDB = new ConnectionDB();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        connectionDB.connect();
        List<Permission> listPermission = new ArrayList<>();
        String content = null;
        String query = "SELECT C.ID, C.NAME FROM ROLE A " +
                "INNER JOIN ROLE_PERMISSION B ON A.ID = B.ROLE_ID " +
                "INNER JOIN PERMISSION C ON B.PERMISSION_ID = C.ID " +
                "WHERE A.ID = ?";

        try {
            preparedStatement = connectionDB.getConnection().prepareStatement(query);
            preparedStatement.setInt(1, roleId);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                permission = new Permission(resultSet.getInt(1), resultSet.getString(2));
                listPermission.add(permission);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            connectionDB.disconnect();
        }

        return listPermission;
    }
}
