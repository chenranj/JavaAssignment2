package cr.jin.java_assignment2;

import cr.jin.java_assignment2.entity.Course;
import cr.jin.java_assignment2.entity.Student;
import cr.jin.java_assignment2.entity.Student_course;
import cr.jin.java_assignment2.repository.CourseRepository;
import cr.jin.java_assignment2.repository.StudentRepository;
import cr.jin.java_assignment2.repository.Student_courseRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Set;

@Configuration
public class ApplicationConfig {
    @Bean
    CommandLineRunner commandLineRunner(StudentRepository studentRepository,
                                        CourseRepository courseRepository,
                                        Student_courseRepository studentCourseRepository) {
        return args -> {
            Student chenran = new Student(
                    "Chenran",
                    "Jin",
                    "chenranj@buffalo.edu",
                    true
            );
            Course cse331 = new Course("CSE331");
            Student_course sc = new Student_course(1L,1L);

            studentRepository.save(chenran);
            courseRepository.save(cse331);
            studentCourseRepository.save(sc);
        };
    }
}
