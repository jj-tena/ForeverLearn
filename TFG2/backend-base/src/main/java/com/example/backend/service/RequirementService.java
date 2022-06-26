package com.example.backend.service;

import com.example.backend.model.Course;
import com.example.backend.model.Requirement;
import com.example.backend.repository.RequirementRepository;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
public class RequirementService {

    private final RequirementRepository requirementRepository;

    private final CourseService courseService;

    public RequirementService(RequirementRepository requirementRepository, CourseService courseService) {
        this.requirementRepository = requirementRepository;
        this.courseService = courseService;
    }

    public void createRequirement(String nameRequirement, Course course) {
        if(Objects.nonNull(nameRequirement)){
            Requirement requirement = new Requirement(nameRequirement);
            Requirement savedRequirement = requirementRepository.save(requirement);
            course.addRequirement(savedRequirement);
            courseService.save(course);
        }
    }

    public void deleteRequirement(Long requirementId, Course course) {
        Optional<Requirement> requirement = requirementRepository.findById(requirementId);
        if(requirement.isPresent()){
            course.deleteRequirement(requirement.get());
            requirementRepository.delete(requirement.get());
            courseService.save(course);
        }
    }

    public void updateRequirement(Long requirementId, String nameRequirement) {
        Optional<Requirement> optionalRequirement = requirementRepository.findById(requirementId);
        if(optionalRequirement.isPresent() && !nameRequirement.isEmpty()){
            optionalRequirement.get().setNameRequirement(nameRequirement);
            requirementRepository.save(optionalRequirement.get());
        }
    }
}
