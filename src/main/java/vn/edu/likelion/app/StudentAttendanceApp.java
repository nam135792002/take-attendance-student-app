package vn.edu.likelion.app;

import vn.edu.likelion.entity.Permission;
import vn.edu.likelion.entity.User;
import vn.edu.likelion.service.AttendanceService;
import vn.edu.likelion.service.StudentService;
import vn.edu.likelion.service.UserService;

import java.util.List;
import java.util.Scanner;

public class StudentAttendanceApp {
    public static StudentService studentService = new StudentService();
    public static AttendanceService attendanceService = new AttendanceService();
    public static Scanner sc = new Scanner(System.in);
    public static UserService userService = new UserService();
    public static User user = null;

    public static void main(String[] args) {
//        studentService.createListStudent(); // create DB from file txt

        while (true){
            try {
                System.out.println("\t\t>>> TAKE ATTENDANCE SYSTEM <<<");
                System.out.println("\t\t\t\t1. Login");
                System.out.println("\t\t\t\t2. Exit");
                System.out.print("\t--> Enter your choice: ");
                String input = sc.nextLine();
                int select = Integer.parseInt(input);

                if(select == 1){
                    user = userService.login();
                    if(user != null) author(user);
                    else System.out.println("--> Username or password is invalid!");
                } else if (select == 2) {
                    break;
                }else {
                    System.out.println("--> Invalid your choice. Please, choice again!");
                }
            }catch (NumberFormatException ex){
                System.out.println("--> Invalid your choice. Please, choice again!");
            }
        }
    }

    public static void author(User user){
        List<Permission> listPermission = user.getListPermission();
        while (true){
            try {
                System.out.println(">> MENU");
                for (Permission permission : listPermission){
                    System.out.println(permission.getId() + ". " + permission.getName());
                }
                System.out.println("Press 0 to exit program!");
                System.out.print("--> Enter your choice: ");
                String input = sc.nextLine();
                int select = Integer.parseInt(input);

                if(select != 0){
                    Permission permission = getPermissionById(listPermission, select);
                    if(permission != null){
                        choiceMenu(permission.getId(), user);
                    }else{
                        System.out.println("You do not have the permission to perform this action.");
                    }
                }else{
                    break;
                }
            }catch (NumberFormatException ex){
                System.out.println("Invalid your choice. Please, choice again!");
            }
        }
    }

    private static Permission getPermissionById(List<Permission> listPermission, int select){
        return listPermission.stream().filter(permission -> permission.getId() == select).findFirst().orElse(null);
    }

    private static void choiceMenu(int permissionId, User user){
        if(permissionId == 4){
            attendanceService.takeAttendance(user);
        }else if(permissionId == 1){
            studentService.printListOfStudent("ALL");
        } else if (permissionId == 2) {
            studentService.printListOfStudent("ABSENT");
        } else if (permissionId == 3) {
            studentService.printListOfStudent("PRESENT");
        } else{
            studentService.printListOfStudent("ALL_DATE");
        }
    }
}
