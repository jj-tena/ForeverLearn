package com.example.backend.service;

import com.example.backend.model.Course;
import com.example.backend.model.Lesson;
import com.example.backend.model.Theme;
import com.example.backend.repository.LessonRepository;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
public class LessonService {

    private final LessonRepository lessonRepository;

    private final ThemeService themeService;

    public LessonService(LessonRepository lessonRepository, ThemeService themeService) {
        this.lessonRepository = lessonRepository;
        this.themeService = themeService;
    }

    public void createLesson(String nameLesson, String descriptionLesson, Theme theme) {
        Lesson lesson = new Lesson(nameLesson, descriptionLesson);
        Lesson savedLesson = lessonRepository.save(lesson);
        theme.addLesson(savedLesson);
        themeService.save(theme);
    }

    public void deleteLesson(Long lessonId, Theme theme) {
        Optional<Lesson> optionalLesson = lessonRepository.findById(lessonId);
        if (optionalLesson.isPresent()){
            theme.deleteLesson(optionalLesson.get());
            lessonRepository.delete(optionalLesson.get());
        }
    }

    public void updateLesson(Long lessonId, String nameLesson, String descriptionLesson, Theme theme) {
        Optional<Lesson> optionalLesson = lessonRepository.findById(lessonId);
        if (optionalLesson.isPresent()){
            if (Objects.nonNull(nameLesson)){
                optionalLesson.get().setNameLesson(nameLesson);
            } if (Objects.nonNull(descriptionLesson)){
                optionalLesson.get().setDescriptionLesson(descriptionLesson);
            }
        }
        lessonRepository.save(optionalLesson.get());
    }


    public Optional<Lesson> findLessonById(Long id) {
        return lessonRepository.findById(id);
    }


}
