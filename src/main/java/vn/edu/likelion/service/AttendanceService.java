package vn.edu.likelion.service;

import vn.edu.likelion.dao.AttendanceDAO;
import vn.edu.likelion.dao.StudentDAO;
import vn.edu.likelion.entity.Attendance;
import vn.edu.likelion.entity.Student;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class AttendanceService {
    Attendance attendance = null;
    StudentDAO studentDAO = new StudentDAO();
    AttendanceDAO attendanceDAO = new AttendanceDAO();
    Scanner sc = new Scanner(System.in);


    public void takeAttendance(){
        List<Student> listStudent = studentDAO.takeListOfStudent();
        attendance = new Attendance();
        System.out.println("Please, take attendance student " + LocalDate.now());
        for(Student s : listStudent){
            System.out.print("Take attendance " + s.getName() + "(A/P): ");
            String a = sc.nextLine();
            attendance.setStatus((!a.equals("A")));
            attendance.setStudent(s);
            attendance.setTakeDate(LocalDate.now());
            attendanceDAO.insertAttendanceIntoDB(attendance);
        }
    }
}
