package com.example.backend.test.service;

import com.example.backend.model.*;
import com.example.backend.repository.RequirementRepository;
import com.example.backend.repository.ThemeRepository;
import com.example.backend.service.CourseService;
import com.example.backend.service.RequirementService;
import com.example.backend.service.ThemeService;
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
class ThemeServiceTest {

    @Mock
    private ThemeRepository themeRepository;

    @Mock private CourseService courseService;

    private ThemeService underTest;

    @BeforeEach
    private void setUp(){
        underTest = new ThemeService(themeRepository, courseService);
    }

    @Test
    void createTheme() {
        //given
        String nameTheme = "ThemeName";
        String descriptionTheme = "ThemeDescription";
        Category category = new Category("CategoryName");
        User user = new User("UserName", "UserSurname", "UserEmail", "UserPassword");
        Course course = new Course("CourseName", "CourseDescription", category, "Principiante", 5, user);
        //when
        underTest.createTheme(nameTheme, descriptionTheme, course);
        //then
        ArgumentCaptor<Theme> themeArgumentCaptor = ArgumentCaptor.forClass(Theme.class);
        verify(themeRepository).save(themeArgumentCaptor.capture());
        Theme capturedTheme = themeArgumentCaptor.getValue();
        assertEquals(capturedTheme.getNameTheme(), nameTheme);
        ArgumentCaptor<Course> courseArgumentCaptor = ArgumentCaptor.forClass(Course.class);
        verify(courseService).save(courseArgumentCaptor.capture());
        Course capturedCourse = courseArgumentCaptor.getValue();
        assertFalse(capturedCourse.getThemes().isEmpty());
    }

    @Test
    void deleteTheme() {
        //given
        Long id = 1L;
        String nameTheme = "ThemeName";
        String descriptionTheme = "ThemeDescription";
        Theme theme = new Theme(nameTheme, descriptionTheme);
        Category category = new Category("CategoryName");
        User user = new User("UserName", "UserSurname", "UserEmail", "UserPassword");
        Course course = new Course("CourseName", "CourseDescription", category, "Principiante", 5, user);
        course.addTheme(theme);
        //when
        Optional<Theme> optionalTheme = Optional.of(theme);
        when(themeRepository.findById(id)).thenReturn(optionalTheme);
        underTest.deleteTheme(id, course);
        //then
        ArgumentCaptor<Theme> themeArgumentCaptor = ArgumentCaptor.forClass(Theme.class);
        verify(themeRepository).delete(themeArgumentCaptor.capture());
        Theme capturedTheme = themeArgumentCaptor.getValue();
        ArgumentCaptor<Course> courseArgumentCaptor = ArgumentCaptor.forClass(Course.class);
        verify(courseService).save(courseArgumentCaptor.capture());
        Course capturedCourse = courseArgumentCaptor.getValue();
        assertTrue(capturedCourse.getThemes().isEmpty());
    }

    @Test
    void updateTheme() {
        //given
        Long id = 1L;
        String nameTheme = "ThemeName";
        String descriptionTheme = "ThemeDescription";
        Theme theme = new Theme(nameTheme, descriptionTheme);
        //when
        Optional<Theme> optionalTheme = Optional.of(theme);
        when(themeRepository.findById(id)).thenReturn(optionalTheme);
        String newName = "NameModified";
        String newDescription = "DescriptionModified";
        underTest.updateTheme(id, newName, newDescription);
        //then
        ArgumentCaptor<Theme> themeArgumentCaptor = ArgumentCaptor.forClass(Theme.class);
        verify(themeRepository).save(themeArgumentCaptor.capture());
        Theme capturedTheme = themeArgumentCaptor.getValue();
        assertEquals(capturedTheme.getNameTheme(), newName);
    }

    @Test
    void findThemeById() {
        //given
        String nameTheme = "ThemeName";
        String descriptionTheme = "ThemeDescription";
        Theme theme = new Theme(nameTheme, descriptionTheme);
        //when
        Optional<Theme> optionalTheme = Optional.of(theme);
        when(themeRepository.findById(theme.getIdTheme())).thenReturn(optionalTheme);
        Optional<Theme> foundTheme = underTest.findThemeById(theme.getIdTheme());
        //then
        assertTrue(foundTheme.isPresent());
        assertEquals(foundTheme.get().getNameTheme(), nameTheme);
    }

    @Test
    void save() {
        //given
        String nameTheme = "ThemeName";
        String descriptionTheme = "ThemeDescription";
        Theme theme = new Theme(nameTheme, descriptionTheme);
        //when
        underTest.save(theme);
        //then
        ArgumentCaptor<Theme> themeArgumentCaptor = ArgumentCaptor.forClass(Theme.class);
        verify(themeRepository).save(themeArgumentCaptor.capture());
        Theme capturedTheme = themeArgumentCaptor.getValue();
        assertEquals(capturedTheme.getNameTheme(), nameTheme);
    }
}