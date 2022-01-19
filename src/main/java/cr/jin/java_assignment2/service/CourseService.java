package cr.jin.java_assignment2.service;

import cr.jin.java_assignment2.entity.Course;
import cr.jin.java_assignment2.entity.Student_course;
import cr.jin.java_assignment2.repository.CourseRepository;
import cr.jin.java_assignment2.repository.Student_courseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class CourseService {
    private final CourseRepository courseRepository;
    private final Student_courseRepository studentCourseRepository;

    @Autowired
    public CourseService(CourseRepository courseRepository, Student_courseRepository studentCourseRepository) {
        this.courseRepository = courseRepository;
        this.studentCourseRepository = studentCourseRepository;
    }

    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    public void addNewCourse(Course course) {
        Optional<Course> courseOptional = courseRepository.findCourseByName(course.getName());
        if (courseOptional.isPresent()) {
            throw new IllegalStateException("The course name has been taken!");
        }
        courseRepository.save(course);
    }

    @Transactional
    public void updateCourse(Long courseId, String name) {
        Course course = courseRepository.findById(courseId).orElseThrow(
                () -> new IllegalStateException("Course with id " + courseId + " does not exist!")
        );

        if (name.length() > 0 && !Objects.equals(course.getName(),name)) {
            course.setName(name);
        }
    }


    public void deleteCourse(Long id) {
        boolean exists = courseRepository.existsById(id);
        if (!exists) {
            throw new IllegalStateException("Course with id " + id + " does not exists.");
        }
        for (Student_course sc: studentCourseRepository.FindAllByCourseId(id).orElseThrow(
                () -> new IllegalStateException("No student enrolled this course")
        )) {
            studentCourseRepository.delete(sc);
        }
        courseRepository.deleteById(id);

    }
}
