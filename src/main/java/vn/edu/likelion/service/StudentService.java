package vn.edu.likelion.service;

import vn.edu.likelion.dao.StudentDAO;
import vn.edu.likelion.entity.Student;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

public class StudentService {
    Student student = null;
    StudentDAO studentDAO = new StudentDAO();
    List<Student> listStudents = null;
    Scanner sc = new Scanner(System.in);

    public void createListStudent(){
        BufferedReader bufferedReader = null;
        String sourceFile = "StudentsList.txt";

        try {
            bufferedReader = new BufferedReader(new FileReader(sourceFile));
            String line;

            while ((line = bufferedReader.readLine()) != null){
                String[] content = line.split("\t");
                student.setId(Integer.parseInt(content[0]));
                student.setName(content[1]);
                studentDAO.insertOneStudentIntoDB(student);
            }

            System.out.println("Create list of student successfully!");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                bufferedReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void printListOfStudent(String type){
        switch (type) {
            case "ALL":
                listStudents = studentDAO.takeListOfStudent();
                System.out.println(">> List of all student");
                showListOfStudent(listStudents);
                break;
            case "ABSENT":
                Date date1 = enterDate();
                listStudents = studentDAO.takeListOfAbsentOrPresentStudent(date1,false);
                System.out.println(">> List of all absent student");
                showListOfStudent(listStudents);
                break;
            case "PRESENT":
                Date date2 = enterDate();
                listStudents = studentDAO.takeListOfAbsentOrPresentStudent(date2, true);
                System.out.println(">> List of all present student");
                showListOfStudent(listStudents);
                break;
            case "ALL_DATE":
                Date date3 = enterDate();
                listStudents = studentDAO.takeListOfStudentByDate(date3);
                System.out.println(">> List of all student by date");
                showListOfStudentByDate(listStudents);
                break;
            default:
                System.out.println("Print list of student failed!");
        }
    }

    private void showListOfStudent(List<Student> listStudents) {
        System.out.println("ID" + "\t\t" + "   Name");
        listStudents.forEach(
                s -> {
                    System.out.println(s.getId() + "\t\t" + s.getName());
                });
    }

    private void showListOfStudentByDate(List<Student> listStudents) {
        System.out.printf("%-5s %-20s %-15s\n", "ID", "Name", "Status");
        listStudents.forEach(
                s -> {
                    System.out.printf("%-5d %-20s %-15s\n", s.getId(), s.getName(), s.isStatus() ? "Present" : "Absent");
                });
    }

    private Date enterDate() {
        System.out.print("Please, enter the date (DD-MM-YYYY): ");
        String input = sc.nextLine();
        Date date = null;
        boolean valid = false;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        while (!valid) {
            try {
                LocalDate localDate = LocalDate.parse(input, formatter);
                date = Date.valueOf(localDate);
                valid = true;
            } catch (DateTimeParseException e) {
                System.out.print("Invalid date format. Please, enter the date (DD-MM-YYYY): ");
                input = sc.nextLine();
            }
        }

        return date;
    }
}
