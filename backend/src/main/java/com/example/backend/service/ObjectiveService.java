package com.example.backend.service;

import com.example.backend.model.Course;
import com.example.backend.model.Objective;
import com.example.backend.repository.ObjectiveRepository;
import org.springframework.stereotype.Service;

import javax.crypto.spec.OAEPParameterSpec;
import java.util.Optional;

@Service
public class ObjectiveService {

    private final ObjectiveRepository objectiveRepository;

    private final CourseService courseService;

    public ObjectiveService(ObjectiveRepository objectiveRepository, CourseService courseService) {
        this.objectiveRepository = objectiveRepository;
        this.courseService = courseService;
    }

    public void createObjective(String nameObjective, Course course) {
        Objective objective = new Objective(nameObjective);
        Objective savedObjective = objectiveRepository.save(objective);
        course.addObjective(savedObjective);
        courseService.save(course);
    }

    public void updateObjective(Long objectiveId, String nameObjective) {
        Optional<Objective> optionalObjective = objectiveRepository.findById(objectiveId);
        if (optionalObjective.isPresent()){
            optionalObjective.get().setNameObjective(nameObjective);
            objectiveRepository.save(optionalObjective.get());
        }
    }

    public void deleteObjective(Long objectiveId, Course course) {
        Optional<Objective> optionalObjective = objectiveRepository.findById(objectiveId);
        if (optionalObjective.isPresent()){
            course.deleteObjective(optionalObjective.get());
            objectiveRepository.delete(optionalObjective.get());
            courseService.save(course);
        }
    }
}
