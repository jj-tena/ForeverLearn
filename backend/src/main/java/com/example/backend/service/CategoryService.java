package com.example.backend.service;

import com.example.backend.model.Category;
import com.example.backend.model.Course;
import com.example.backend.model.User;
import com.example.backend.repository.CategoryRepository;
import com.example.backend.repository.CourseRepository;
import org.hibernate.engine.jdbc.BlobProxy;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    private final UserService userService;

    private final CourseRepository courseRepository;

    public CategoryService(CategoryRepository categoryRepository, UserService userService, CourseRepository courseRepository) {
        this.categoryRepository = categoryRepository;
        this.userService = userService;
        this.courseRepository = courseRepository;
    }

    public Category create(String name, String imagePath) throws IOException {
        Category category = new Category();
        category.setName(name);
        Resource image = new ClassPathResource(imagePath);
        category.setPicture(BlobProxy.generateProxy(image.getInputStream(), image.contentLength()));
        return categoryRepository.save(category);
    }

    public List<Category> findAll(){
        return categoryRepository.findAll();
    }

    public Optional<Category> findById(Long id){
        return categoryRepository.findById(id);
    }

    public Optional<Category> findByName(String name){
        return categoryRepository.findByName(name);
    }

    public Page<Category> findPageCategories(Integer numberPage, int content) {
        return categoryRepository.findAll(PageRequest.of(numberPage, content));
    }

    public Optional<List<Category>> findCategoriesByName(String name) {
        return categoryRepository.findAllByName(name);
    }

    @Transactional
    public Optional<Category> createCategory(String name, MultipartFile image) throws IOException {
        Optional<Category> optionalCategory = Optional.empty();
        Optional<User> optionalUser = userService.getActiveUser();
        if (optionalUser.isPresent() && optionalUser.get().isAdmin()){
            Category category = new Category(name);
            if (Objects.nonNull(image) && !Objects.equals(image.getOriginalFilename(), ""))  {
                category.setPicture(BlobProxy.generateProxy(image.getInputStream(), image.getSize()));
            }
            Category savedCategory = categoryRepository.save(category);
            optionalCategory = Optional.of(savedCategory);
        }
        return optionalCategory;
    }

    public Optional<Category> editCategory(String name, MultipartFile image, Long id) throws IOException {
        Optional<User> optionalUser = userService.getActiveUser();
        Optional<Category> optionalCategory = categoryRepository.findById(id);
        if (optionalUser.isPresent() && optionalUser.get().isAdmin() && optionalCategory.isPresent()){
            optionalCategory.get().setName(name);
            if (Objects.nonNull(image) && !Objects.equals(image.getOriginalFilename(), ""))  {
                optionalCategory.get().setPicture(BlobProxy.generateProxy(image.getInputStream(), image.getSize()));
            }
            Category savedCategory = categoryRepository.save(optionalCategory.get());
            optionalCategory = Optional.of(savedCategory);
        }
        return optionalCategory;
    }

    @Transactional
    public void deleteCategory(Long id) {
        Optional<Category> optionalCategory = categoryRepository.findById(id);
        Optional<User> optionalUser = userService.getActiveUser();
        Optional<Category> categoryOthers = categoryRepository.findByName("Otros");
        if (optionalUser.isPresent() && optionalUser.get().isAdmin() && optionalCategory.isPresent() && categoryOthers.isPresent() && !optionalCategory.get().equals(categoryOthers.get())){
            List<Course> courseList = courseRepository.findCoursesByCategory(optionalCategory.get());
            courseList.forEach(course -> {
                course.setCategory(categoryOthers.get());
                courseRepository.save(course);
            });
            categoryRepository.delete(optionalCategory.get());
        }
    }
}
