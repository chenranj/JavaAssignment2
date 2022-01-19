package cr.jin.java_assignment2.entity;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String first_name;
    private String last_name;
    private String email;
    private Boolean active;
    @Transient
    private Set<Long> courses_enrolled;

    public Student() {
    }

    public Student(String first_name, String last_name, String email, Boolean active) {
        this.first_name = first_name;
        this.last_name = last_name;
        this.email = email;
        this.active = active;
    }

    public Student(String first_name, String last_name, String email, Boolean active, Set<Long> courses_enrolled) {
        this.first_name = first_name;
        this.last_name = last_name;
        this.email = email;
        this.active = active;
        this.courses_enrolled = courses_enrolled;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Set<Long> getCourses_enrolled() {
        return courses_enrolled;
    }

    public void setCourses_enrolled(Set<Long> courses_enrolled) {
        this.courses_enrolled = courses_enrolled;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", first_name='" + first_name + '\'' +
                ", last_name='" + last_name + '\'' +
                ", email='" + email + '\'' +
                ", active=" + active +
                '}';
    }
}
