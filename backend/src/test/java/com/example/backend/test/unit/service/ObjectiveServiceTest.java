package com.example.backend.test.unit.service;

import com.example.backend.model.*;
import com.example.backend.repository.ObjectiveRepository;
import com.example.backend.repository.RequirementRepository;
import com.example.backend.service.CourseService;
import com.example.backend.service.ObjectiveService;
import com.example.backend.service.RequirementService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ObjectiveServiceTest {

    @Mock
    private ObjectiveRepository objectiveRepository;

    @Mock private CourseService courseService;

    private ObjectiveService underTest;

    @BeforeEach
    private void setUp(){
        underTest = new ObjectiveService(objectiveRepository, courseService);
    }

    @Test
    void createObjective() {
        //given
        String nameObjective = "ObjectiveName";
        Category category = new Category("CategoryName");
        User user = new User("UserName", "UserSurname", "UserEmail", "UserPassword");
        Course course = new Course("CourseName", "CourseDescription", category, "Principiante", 5, user);
        //when
        underTest.createObjective(nameObjective, course);
        //then
        ArgumentCaptor<Objective> objectiveArgumentCaptor = ArgumentCaptor.forClass(Objective.class);
        verify(objectiveRepository).save(objectiveArgumentCaptor.capture());
        Objective capturedObjective = objectiveArgumentCaptor.getValue();
        assertEquals(capturedObjective.getNameObjective(), nameObjective);
        ArgumentCaptor<Course> courseArgumentCaptor = ArgumentCaptor.forClass(Course.class);
        verify(courseService).save(courseArgumentCaptor.capture());
        Course capturedCourse = courseArgumentCaptor.getValue();
        assertFalse(capturedCourse.getObjectives().isEmpty());
    }

    @Test
    void updateObjective() {
        //given
        Long id = 1L;
        String nameObjective = "ObjectiveName";
        Objective objective = new Objective(nameObjective);
        //when
        Optional<Objective> optionalObjective = Optional.of(objective);
        when(objectiveRepository.findById(id)).thenReturn(optionalObjective);
        String newName = "NameModified";
        underTest.updateObjective(id, newName);
        //then
        ArgumentCaptor<Objective> objectiveArgumentCaptor = ArgumentCaptor.forClass(Objective.class);
        verify(objectiveRepository).save(objectiveArgumentCaptor.capture());
        Objective capturedObjective = objectiveArgumentCaptor.getValue();
        assertEquals(capturedObjective.getNameObjective(), "NameModified");
    }

    @Test
    void deleteObjective() {
        //given
        Long id = 1L;
        String nameObjective = "ObjectiveName";
        Objective objective = new Objective(nameObjective);
        Category category = new Category("CategoryName");
        User user = new User("UserName", "UserSurname", "UserEmail", "UserPassword");
        Course course = new Course("CourseName", "CourseDescription", category, "Principiante", 5, user);
        course.addObjective(objective);
        //when
        Optional<Objective> optionalObjective = Optional.of(objective);
        when(objectiveRepository.findById(id)).thenReturn(optionalObjective);
        underTest.deleteObjective(id, course);
        //then
        ArgumentCaptor<Objective> objectiveArgumentCaptor = ArgumentCaptor.forClass(Objective.class);
        verify(objectiveRepository).delete(objectiveArgumentCaptor.capture());
        Objective capturedObjective = objectiveArgumentCaptor.getValue();
        ArgumentCaptor<Course> courseArgumentCaptor = ArgumentCaptor.forClass(Course.class);
        verify(courseService).save(courseArgumentCaptor.capture());
        Course capturedCourse = courseArgumentCaptor.getValue();
        assertTrue(capturedCourse.getObjectives().isEmpty());
    }
}