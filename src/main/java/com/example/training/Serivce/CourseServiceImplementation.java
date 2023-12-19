package com.example.training.Serivce;

import com.example.training.Entity.Department;
import com.example.training.Entity.Teacher;
import com.example.training.Repository.DepartmentRepository;
import com.example.training.Repository.TeacherRepository;
import com.example.training.ResourceNotFoundException;
import com.example.training.Serivce.DTO.CourseDto;
import com.example.training.Serivce.DTO.CourseMapper;
import com.example.training.Entity.Course;
import com.example.training.Repository.CourseRepository;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@NoArgsConstructor
@Service
public class CourseServiceImplementation implements CourseService {
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private TeacherRepository teacherRepository;
    @Autowired
    private DepartmentRepository departmentRepository;
    @Override
    public CourseDto createCourse(CourseDto courseDto) {
        Department department = departmentRepository.findById(courseDto.getDepartmentId()).orElseThrow(() -> new ResourceNotFoundException("Department with {id} not found"+ courseDto.getDepartmentId()));
        Teacher teacher = teacherRepository.findById(courseDto.getTeacherId()).orElseThrow(() -> new ResourceNotFoundException("Teacher with {id} not found"+ courseDto.getTeacherId()));
        Course course = CourseMapper.toCourse(courseDto, department, teacher);
        course = courseRepository.save(course);
        return CourseMapper.toCourseDto(course);
    }
    @Override
    public CourseDto getCourseById(long id) {
        Course course = courseRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Course with {id} not found"+ id));
        return CourseMapper.toCourseDto(course);
    }
    @Override
    public CourseDto updateCourse(long courseId, CourseDto updatedCourse) {
        Course course = courseRepository.findById(courseId).orElseThrow(() -> new ResourceNotFoundException("Course with {id} not found"+ courseId));
        course.setCourseCode(updatedCourse.getCourseCode());
        course.setCourseName(updatedCourse.getCourseName());
        course.setCourseDescription(updatedCourse.getCourseDescription());
        course.setCoursePrerequisite(updatedCourse.getCoursePrerequisite());
        course.setCourseCredit(updatedCourse.getCourseCredit());
        return CourseMapper.toCourseDto(course);
    }
    @Override
    public void deleteCourse(long courseId) {
        courseRepository.findById(courseId).orElseThrow(() -> new ResourceNotFoundException("Course with {id} not found"+ courseId));
        courseRepository.deleteById(courseId);
    }
    @Override
    public List<CourseDto> getAllCourses() {
        List<Course> courses = courseRepository.findAll();
        return courses.stream().map((course) -> CourseMapper.toCourseDto(course))
                .collect(java.util.stream.Collectors.toList());
    }
}
