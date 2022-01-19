package cr.jin.java_assignment2.entity;

import javax.persistence.*;

@Entity
@Table
public class Student_course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long student_id;
    private Long courses_id;

    public Student_course(Long student_id, Long courses_id) {
        this.student_id = student_id;
        this.courses_id = courses_id;
    }

    public Student_course() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getStudent_id() {
        return student_id;
    }

    public void setStudent_id(Long student_id) {
        this.student_id = student_id;
    }

    public Long getCourses_id() {
        return courses_id;
    }

    public void setCourses_id(Long courses_id) {
        this.courses_id = courses_id;
    }
}
