package cr.jin.java_assignment2.service;

import cr.jin.java_assignment2.entity.Course;
import cr.jin.java_assignment2.entity.Student;
import cr.jin.java_assignment2.entity.Student_course;
import cr.jin.java_assignment2.repository.CourseRepository;
import cr.jin.java_assignment2.repository.StudentRepository;
import cr.jin.java_assignment2.repository.Student_courseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

@Service
public class StudentService {
    private final StudentRepository studentRepository;
    private final Student_courseRepository studentCourseRepository;
    private final CourseRepository courseRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository, Student_courseRepository studentCourseRepository, CourseRepository courseRepository) {
        this.studentRepository = studentRepository;
        this.studentCourseRepository = studentCourseRepository;
        this.courseRepository = courseRepository;
    }

    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    public void addNewStudent(Student student) {
        Optional<Student> studentOptional = studentRepository.findStudentByEmail(student.getEmail());
        if (studentOptional.isPresent()) {
            throw new IllegalStateException("Email taken");
        }
        studentRepository.save(student);
        for (Long courseId: student.getCourses_enrolled()){
            Optional<Course> courseOptional = courseRepository.findById(courseId);
            if (!courseOptional.isPresent()) {
                throw new IllegalStateException("Cannot find course with id " + courseId);
            }
            studentCourseRepository.save(new Student_course(student.getId(),courseId));
        }
    }

    @Transactional
    public void updateStudent(Long id, String first_name, String last_name, String email, Boolean active, Set<Long> courses_enrolled) {
        Student student = studentRepository.findById(id).orElseThrow(
                () -> new IllegalStateException("student with id " + id + " does not exist.")
        );

        if (first_name != null && first_name.length() > 0 && !Objects.equals(student.getFirst_name(), first_name)) {
            student.setFirst_name(first_name);
        }

        if (last_name != null && last_name.length() > 0 && !Objects.equals(student.getLast_name(), last_name)) {
            student.setLast_name(last_name);
        }

        if (email != null && email.length() > 0 && !Objects.equals(student.getEmail(), email)) {
            Optional<Student> studentOptional = studentRepository.findStudentByEmail(email);
            if(studentOptional.isPresent()) {
                throw new IllegalStateException("email taken");
            }
            student.setEmail(email);
        }

        if (active != null) {
            student.setActive(active);
        }

        if (courses_enrolled != null) {
            // Delete all the junction table from this student.
            for (Student_course sc: studentCourseRepository.FindALLBYStudentId(id).orElseThrow(
                    () -> new IllegalStateException("This student enrolled no course.")
            )) {
                studentCourseRepository.delete(sc);
            }
            // Add the new junction table to this student.
            for (long courseId: courses_enrolled) {
                Optional<Course> course = courseRepository.findById(courseId);
                if (course.isPresent()) {
                    studentCourseRepository.save(new Student_course(id, courseId));
                }
            }
        }
    }

    public void deleteStudent(Long id) {
        Boolean exists = studentRepository.existsById(id);
        if (!exists) {
            throw new IllegalStateException("Student with id " + id + " does not exist.");
        }
        for (Student_course sc: studentCourseRepository.FindALLBYStudentId(id).orElseThrow(
                () -> new IllegalStateException("This student enrolled no course.")
        )) {
            studentCourseRepository.delete(sc);
        }
        studentRepository.deleteById(id);
    }
}
