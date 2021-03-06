package com.example.backend.service;

import com.example.backend.model.Lesson;
import com.example.backend.model.Theme;
import com.example.backend.repository.LessonRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LessonService {

    private final LessonRepository lessonRepository;

    private final ThemeService themeService;

    public LessonService(LessonRepository lessonRepository, ThemeService themeService) {
        this.lessonRepository = lessonRepository;
        this.themeService = themeService;
    }

    public Lesson createLesson(String nameLesson, String descriptionLesson, String iframeLesson, Theme theme) {
        Lesson lesson = new Lesson(nameLesson, descriptionLesson, iframeLesson);
        Lesson savedLesson = lessonRepository.save(lesson);
        theme.addLesson(savedLesson);
        themeService.save(theme);
        return lesson;
    }

    public void deleteLesson(Long lessonId, Theme theme) {
        Optional<Lesson> optionalLesson = lessonRepository.findById(lessonId);
        if (optionalLesson.isPresent()){
            theme.deleteLesson(optionalLesson.get());
            lessonRepository.delete(optionalLesson.get());
            themeService.save(theme);
        }
    }

    public void updateLesson(Long lessonId, String nameLesson, String descriptionLesson, String iframeLesson) {
        Optional<Lesson> optionalLesson = lessonRepository.findById(lessonId);
        if (optionalLesson.isPresent()){
            if (!nameLesson.isEmpty()){
                optionalLesson.get().setNameLesson(nameLesson);
            } if (!descriptionLesson.isEmpty()){
                optionalLesson.get().setDescriptionLesson(descriptionLesson);
            } if (!iframeLesson.isEmpty()){
                optionalLesson.get().setIframeLesson(iframeLesson);
            }
            lessonRepository.save(optionalLesson.get());
        }
    }


    public Optional<Lesson> findLessonById(Long id) {
        return lessonRepository.findById(id);
    }


}
