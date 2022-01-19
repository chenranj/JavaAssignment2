package cr.jin.java_assignment2.service;

import cr.jin.java_assignment2.entity.Course;
import cr.jin.java_assignment2.entity.Student;
import cr.jin.java_assignment2.entity.Student_course;
import cr.jin.java_assignment2.repository.CourseRepository;
import cr.jin.java_assignment2.repository.StudentRepository;
import cr.jin.java_assignment2.repository.Student_courseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class MainService {
    private final CourseRepository courseRepository;
    private final StudentRepository studentRepository;
    private final Student_courseRepository studentCourseRepository;

    @Autowired
    public MainService(CourseRepository courseRepository, StudentRepository studentRepository, Student_courseRepository studentCourseRepository) {
        this.courseRepository = courseRepository;
        this.studentRepository = studentRepository;
        this.studentCourseRepository = studentCourseRepository;
    }

    public void enrollCrouse(Long studentId, Long courseId) {
        Optional<Student> student = studentRepository.findById(studentId);
        Optional<Course> course = courseRepository.findById(courseId);
        if (!student.isPresent()) {
            throw new IllegalStateException("Student with id " + studentId + " does not exist!");
        }
        if (!course.isPresent()) {
            throw new IllegalStateException("Course with id " + courseId + "does not exist!");
        }
        studentCourseRepository.save(new Student_course(studentId, courseId));
    }

    public void dropCourse(Long studentId, Long courseId) {
        Optional<Student> student = studentRepository.findById(studentId);
        Optional<Course> course = courseRepository.findById(courseId);
        if (!student.isPresent()) {
            throw new IllegalStateException("Student with id " + studentId + " does not exist!");
        }
        if (!course.isPresent()) {
            throw new IllegalStateException("Course with id " + courseId + "does not exist!");
        }
        for (Student_course sc: studentCourseRepository.FindALLBYStudentId(studentId).orElseThrow(
                () -> new IllegalStateException("This student enrolled no course")
        )) {
            if(sc.getCourses_id().equals(courseId)) {
                studentCourseRepository.delete(sc);
            }
        }
    }


    public List<Long> enrolledCourse(Long studentId) {
        List<Long> res = new ArrayList<>();
        Optional<Student> student = studentRepository.findById(studentId);
        if (!student.isPresent()) {
            throw new IllegalStateException("Student with id " + studentId + " does not exist!");
        }

        for (Student_course sc: studentCourseRepository.FindALLBYStudentId(studentId).orElseThrow(
                () -> new IllegalStateException("This student enrolled no class!")
        )) {
            res.add(sc.getId());
        }
        return res;
    }

    public List<Long> rollCall(Long courseId) {
        List<Long> res = new ArrayList<>();
        Optional<Course> course = courseRepository.findById(courseId);
        if (!course.isPresent()) {
            throw new IllegalStateException("Course with id " + courseId + "does not exist!");
        }
        for (Student_course sc: studentCourseRepository.FindAllByCourseId(courseId).orElseThrow(
                () -> new IllegalStateException("This course has no student!")
        )) {
            res.add(sc.getId());
        }
        return res;
    }
}
