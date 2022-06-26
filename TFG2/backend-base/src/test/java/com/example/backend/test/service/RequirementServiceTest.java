package com.example.backend.test.service;

import com.example.backend.model.Category;
import com.example.backend.model.Course;
import com.example.backend.model.Requirement;
import com.example.backend.model.User;
import com.example.backend.repository.RequirementRepository;
import com.example.backend.service.CategoryService;
import com.example.backend.service.CourseService;
import com.example.backend.service.RequirementService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.internal.stubbing.BaseStubbing;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.InstanceOfAssertFactories.OPTIONAL;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RequirementServiceTest {

    @Mock private RequirementRepository requirementRepository;

    @Mock private CourseService courseService;

    private RequirementService underTest;

    @BeforeEach
    private void setUp(){
        underTest = new RequirementService(requirementRepository, courseService);
    }

    @Test
    void createRequirement() {
        //given
        String nameRequirement = "RequirementName";
        Category category = new Category("CategoryName");
        User user = new User("UserName", "UserSurname", "UserEmail", "UserPassword");
        Course course = new Course("CourseName", "CourseDescription", category, "Principiante", 5, user);
        //when
        underTest.createRequirement(nameRequirement, course);
        //then
        ArgumentCaptor<Requirement> requirementArgumentCaptor = ArgumentCaptor.forClass(Requirement.class);
        verify(requirementRepository).save(requirementArgumentCaptor.capture());
        Requirement capturedRequirement = requirementArgumentCaptor.getValue();
        assertEquals(capturedRequirement.getNameRequirement(), nameRequirement);
        ArgumentCaptor<Course> courseArgumentCaptor = ArgumentCaptor.forClass(Course.class);
        verify(courseService).save(courseArgumentCaptor.capture());
        Course capturedCourse = courseArgumentCaptor.getValue();
        assertFalse(capturedCourse.getRequirements().isEmpty());
    }

    @Test
    void deleteRequirement() {
        //given
        Long id = 1L;
        String nameRequirement = "RequirementName";
        Requirement requirement = new Requirement(nameRequirement);
        Category category = new Category("CategoryName");
        User user = new User("UserName", "UserSurname", "UserEmail", "UserPassword");
        Course course = new Course("CourseName", "CourseDescription", category, "Principiante", 5, user);
        course.addRequirement(requirement);
        //when
        Optional<Requirement> optionalRequirement = Optional.of(requirement);
        when(requirementRepository.findById(id)).thenReturn(optionalRequirement);
        underTest.deleteRequirement(id, course);
        //then
        ArgumentCaptor<Requirement> requirementArgumentCaptor = ArgumentCaptor.forClass(Requirement.class);
        verify(requirementRepository).delete(requirementArgumentCaptor.capture());
        Requirement capturedRequirement = requirementArgumentCaptor.getValue();
        ArgumentCaptor<Course> courseArgumentCaptor = ArgumentCaptor.forClass(Course.class);
        verify(courseService).save(courseArgumentCaptor.capture());
        Course capturedCourse = courseArgumentCaptor.getValue();
        assertTrue(capturedCourse.getRequirements().isEmpty());
    }

    @Test
    void updateRequirement() {
        //given
        Long id = 1L;
        String nameRequirement = "RequirementName";
        Requirement requirement = new Requirement(nameRequirement);
        //when
        Optional<Requirement> optionalRequirement = Optional.of(requirement);
        when(requirementRepository.findById(id)).thenReturn(optionalRequirement);
        String newName = "NameModified";
        underTest.updateRequirement(id, newName);
        //then
        ArgumentCaptor<Requirement> requirementArgumentCaptor = ArgumentCaptor.forClass(Requirement.class);
        verify(requirementRepository).save(requirementArgumentCaptor.capture());
        Requirement capturedRequirement = requirementArgumentCaptor.getValue();
        assertEquals(capturedRequirement.getNameRequirement(), "NameModified");
    }
}