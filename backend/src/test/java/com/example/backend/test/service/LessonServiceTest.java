package com.example.backend.test.service;

import com.example.backend.model.*;
import com.example.backend.repository.LessonRepository;
import com.example.backend.repository.ThemeRepository;
import com.example.backend.service.CourseService;
import com.example.backend.service.LessonService;
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
class LessonServiceTest {

    @Mock
    private LessonRepository lessonRepository;

    @Mock private ThemeService themeService;

    private LessonService underTest;

    @BeforeEach
    private void setUp(){
        underTest = new LessonService(lessonRepository, themeService);
    }

    @Test
    void createLesson() {
        //given
        String nameLesson = "LessonName";
        String descriptionLesson = "LessonDescription";
        String iframeLesson = "LessonIframe";
        Theme theme = new Theme("ThemeName", "DescriptionTheme");
        //when
        underTest.createLesson(nameLesson, descriptionLesson, iframeLesson, theme);
        //then
        ArgumentCaptor<Lesson> lessonArgumentCaptor = ArgumentCaptor.forClass(Lesson.class);
        verify(lessonRepository).save(lessonArgumentCaptor.capture());
        Lesson capturedLesson = lessonArgumentCaptor.getValue();
        assertEquals(capturedLesson.getNameLesson(), nameLesson);
        ArgumentCaptor<Theme> themeArgumentCaptor = ArgumentCaptor.forClass(Theme.class);
        verify(themeService).save(themeArgumentCaptor.capture());
        Theme capturedTheme = themeArgumentCaptor.getValue();
        assertFalse(capturedTheme.getLessons().isEmpty());
    }

    @Test
    void deleteLesson() {
        //given
        Long id = 1L;
        String nameLesson = "LessonName";
        String descriptionLesson = "LessonDescription";
        String iframeLesson = "LessonIframe";
        Lesson lesson = new Lesson(nameLesson, descriptionLesson, iframeLesson);
        Theme theme = new Theme("ThemeName", "DescriptionTheme");
        theme.addLesson(lesson);
        //when
        Optional<Lesson> optionalLesson = Optional.of(lesson);
        when(lessonRepository.findById(id)).thenReturn(optionalLesson);
        underTest.deleteLesson(id, theme);
        //then
        ArgumentCaptor<Lesson> lessonArgumentCaptor = ArgumentCaptor.forClass(Lesson.class);
        verify(lessonRepository).delete(lessonArgumentCaptor.capture());
        Lesson capturedLesson = lessonArgumentCaptor.getValue();
        ArgumentCaptor<Theme> themeArgumentCaptor = ArgumentCaptor.forClass(Theme.class);
        verify(themeService).save(themeArgumentCaptor.capture());
        Theme capturedTheme = themeArgumentCaptor.getValue();
        assertTrue(theme.getLessons().isEmpty());
    }

    @Test
    void updateLesson() {
        //given
        Long id = 1L;
        String nameLesson = "LessonName";
        String descriptionLesson = "LessonDescription";
        String iframeLesson = "LessonIframe";
        Lesson lesson = new Lesson(nameLesson, descriptionLesson, iframeLesson);
        //when
        Optional<Lesson> optionalLesson = Optional.of(lesson);
        when(lessonRepository.findById(id)).thenReturn(optionalLesson);
        String newName = "NameModified";
        String newDescription = "DescriptionModified";
        String newIframe = "IframeModified";
        underTest.updateLesson(id, newName, newDescription, newIframe);
        //then
        ArgumentCaptor<Lesson> lessonArgumentCaptor = ArgumentCaptor.forClass(Lesson.class);
        verify(lessonRepository).save(lessonArgumentCaptor.capture());
        Lesson capturedLesson = lessonArgumentCaptor.getValue();
        assertEquals(capturedLesson.getNameLesson(), newName);
    }

    @Test
    void findLessonById() {
        //given
        String nameLesson = "LessonName";
        String descriptionLesson = "LessonDescription";
        String iframeLesson = "LessonIframe";
        Lesson lesson = new Lesson(nameLesson, descriptionLesson, iframeLesson);
        //when
        Optional<Lesson> optionalLesson = Optional.of(lesson);
        when(lessonRepository.findById(lesson.getIdLesson())).thenReturn(optionalLesson);
        Optional<Lesson> foundLesson = underTest.findLessonById(lesson.getIdLesson());
        //then
        assertTrue(foundLesson.isPresent());
        assertEquals(foundLesson.get().getNameLesson(), nameLesson);
    }
}