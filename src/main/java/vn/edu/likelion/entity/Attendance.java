package vn.edu.likelion.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Attendance {
    private Integer id;
    private Student student;
    private LocalDate takeDate;
    private boolean status;
    private User user;
}
