package cr.jin.java_assignment2.controller;

import cr.jin.java_assignment2.entity.Course;
import cr.jin.java_assignment2.service.MainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1")
public class MainController {
    private final MainService mainService;

    @Autowired
    public MainController(MainService mainService) {
        this.mainService = mainService;
    }

    @PostMapping(path = "enroll/{studentId}")
    public void enrollCourse(@PathVariable("studentId") Long studentId, @RequestParam(required = true) Long courseId) {
        mainService.enrollCrouse(studentId, courseId);
    }

    @PostMapping(path = "drop/{studentId}")
    public void dropCourse(@PathVariable("studentId") Long studentId, @RequestParam(required = true) Long courseId) {
        mainService.dropCourse(studentId, courseId);
    }

    @GetMapping(path = "enrolled/{studentId}")
    public List<Long> enrolledCourses(@PathVariable("studentId") Long studentId) {
        return mainService.enrolledCourse(studentId);
    }

    @GetMapping(path = "roll_call/{courseId}")
    public List<Long> rollCall(@PathVariable("courseId") Long courseId) {
        return mainService.rollCall(courseId);
    }
}
