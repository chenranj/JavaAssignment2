package cr.jin.java_assignment2.repository;

import cr.jin.java_assignment2.entity.Student_course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public interface Student_courseRepository extends JpaRepository<Student_course, Long> {

    @Query("SELECT r FROM Student_course r WHERE r.courses_id = ?1")
    Optional<List<Student_course>> FindAllByCourseId(Long id);

    @Query("SELECT r FROM Student_course r WHERE r.student_id = ?1")
    Optional<List<Student_course>> FindALLBYStudentId(Long id);
}
