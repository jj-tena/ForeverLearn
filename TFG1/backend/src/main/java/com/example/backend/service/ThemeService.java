package com.example.backend.service;

import com.example.backend.model.Course;
import com.example.backend.model.Theme;
import com.example.backend.repository.ThemeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class ThemeService {

    private final ThemeRepository themeRepository;

    private final CourseService courseService;

    public ThemeService(ThemeRepository topicRepository, CourseService courseService) {
        this.themeRepository = topicRepository;
        this.courseService = courseService;
    }

    @Transactional
    public Theme createTheme(String nameTheme, String descriptionTheme, Course course) {
        Theme theme = new Theme(nameTheme, descriptionTheme);
        Theme savedTheme = themeRepository.save(theme);
        course.addTheme(savedTheme);
        courseService.save(course);
        return savedTheme;
    }


    public void deleteTheme(Long themeId, Course course) {
        Optional<Theme> optionalTheme = themeRepository.findById(themeId);
        if (optionalTheme.isPresent()){
            course.deleteTheme(optionalTheme.get());
            courseService.save(course);
            themeRepository.delete(optionalTheme.get());
        }
    }

    public void updateTheme(Long themeId, String nameTheme, String descriptionTheme) {
        Optional<Theme> theme = themeRepository.findById(themeId);
        if(theme.isPresent()){
            if (!nameTheme.isEmpty()){
                theme.get().setNameTheme(nameTheme);
            } if (!descriptionTheme.isEmpty()){
                theme.get().setDescriptionTheme(descriptionTheme);
            }
            themeRepository.save(theme.get());
        }
    }

    public Optional<Theme> findThemeById(Long themeId){
        return themeRepository.findById(themeId);
    }

    public Theme save(Theme theme){
        return themeRepository.save(theme);
    }
}
