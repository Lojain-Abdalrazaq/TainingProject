package com.example.training.Serivce.Class;

import com.example.training.Entity.Department;
import com.example.training.Handler.ResourceNotFoundException;
import com.example.training.Entity.Teacher;
import com.example.training.Repository.TeacherRepository;
import com.example.training.Repository.DepartmentRepository;
import com.example.training.Serivce.DTO.TeacherDto;
import com.example.training.Serivce.Mapper.TeacherMapper;
import com.example.training.Serivce.Interface.TeacherService;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@NoArgsConstructor
@Service
public class TeacherServiceImplementation implements TeacherService {
    @Autowired
    private TeacherRepository teacherRepository;
    @Autowired
    private DepartmentRepository departmentRepository;
    @Override
    public TeacherDto createTeacher(TeacherDto teacherDto) throws ResourceNotFoundException{
        Department department = departmentRepository.findById(teacherDto.getDepartmentId()).orElseThrow(() -> new ResourceNotFoundException("Department with {"+teacherDto.getDepartmentId()+"}not found"));
        Teacher teacher = TeacherMapper.toTeacher(teacherDto, department);
        teacher = teacherRepository.save(teacher);
        return TeacherMapper.toTeacherDto(teacher);
    }
    @Override
    public TeacherDto getTeacherById(long id) throws ResourceNotFoundException{
        Teacher teacher = teacherRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Teacher with {"+id+"} not found"));
        return TeacherMapper.toTeacherDto(teacher);
    }
    @Override
    public TeacherDto updateTeacher(long teacherId, TeacherDto updatedTeacher) throws ResourceNotFoundException{
        Teacher teacher = teacherRepository.findById(teacherId).orElseThrow(() -> new ResourceNotFoundException("Teacher with {"+teacherId+"} not found"));
        if(updatedTeacher.getTeacherName() !=null){
            teacher.setTeacherName(updatedTeacher.getTeacherName());
        }
        if(updatedTeacher.getTeacherGender() !=null){
            teacher.setTeacherGender(updatedTeacher.getTeacherGender());
        }
        if(updatedTeacher.getTeacherBD() !=null){
            teacher.setTeacherBD(updatedTeacher.getTeacherBD());
        }
        if(updatedTeacher.getTeacherPhone() !=null){
            teacher.setTeacherPhone(updatedTeacher.getTeacherPhone());
        }
        if(updatedTeacher.getTeacherAddress() !=null){
            teacher.setTeacherAddress(updatedTeacher.getTeacherAddress());
        }
        if(updatedTeacher.getTeacherSalary() != 0){
            teacher.setTeacherSalary(updatedTeacher.getTeacherSalary());
        }
        if(updatedTeacher.getTeacherHourlyRate() != 0){
            teacher.setTeacherHourlyRate(updatedTeacher.getTeacherHourlyRate());
        }
        if(updatedTeacher.getTeacherEmail() !=null){
            teacher.setTeacherEmail(updatedTeacher.getTeacherEmail());
        }
        teacher = teacherRepository.save(teacher);
        return TeacherMapper.toTeacherDto(teacher);
    }
    @Override
    public void deleteTeacher(long teacherId) {
        teacherRepository.findById(teacherId).orElseThrow(() -> new ResourceNotFoundException("Teacher with {"+teacherId+"} not found"));
        teacherRepository.deleteById(teacherId);
    }
    @Override
    public List<TeacherDto> getAllTeachers() {
        List<Teacher> teachers = teacherRepository.findAll();
        return teachers.stream().map((teacher) -> TeacherMapper.toTeacherDto(teacher))
                .collect(java.util.stream.Collectors.toList());
    }
}
