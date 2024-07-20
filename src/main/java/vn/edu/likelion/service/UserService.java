package vn.edu.likelion.service;

import vn.edu.likelion.dao.UserDAO;
import vn.edu.likelion.entity.User;

import java.util.Scanner;

public class UserService {
    UserDAO userDAO = new UserDAO();
    Scanner sc = new Scanner(System.in);

    public User login(){
        System.out.println(">> Please login before using app");
        System.out.print("Enter your username: ");
        String username = sc.nextLine();
        System.out.print("Enter your password: ");
        String password = sc.nextLine();

        return userDAO.checkExistUsernameAndPasswordInDB(username, password);
    }
}
