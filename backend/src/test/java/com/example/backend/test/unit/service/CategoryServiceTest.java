package com.example.backend.test.unit.service;

import com.example.backend.model.Category;
import com.example.backend.repository.CategoryRepository;
import com.example.backend.repository.CourseRepository;
import com.example.backend.service.CategoryService;
import com.example.backend.service.UserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.Extensions;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class CategoryServiceTest {

    @Mock private CategoryRepository categoryRepository;

    @Mock private UserService userService;

    @Mock private CourseRepository courseRepository;

    private CategoryService underTest;

    @BeforeEach
    private void setUp(){
        underTest = new CategoryService(categoryRepository, userService, courseRepository);
    }

    @Test
    void create() throws IOException {
        //given
        String name = "Demo";
        String imagePath = "";
        Category category = new Category(name);
        //when
        underTest.create(name, imagePath);
        //then
        ArgumentCaptor<Category> categoryArgumentCaptor = ArgumentCaptor.forClass(Category.class);
        verify(categoryRepository).save(categoryArgumentCaptor.capture());
        Category capturedCategory = categoryArgumentCaptor.getValue();
        assertThat(capturedCategory.getName()).isEqualTo(category.getName());
    }

    @Test
    void findAll() {
        //when
        underTest.findAll();
        //then
        verify(categoryRepository).findAll();
    }

    @Test
    void findById() {
    }

    @Test
    void findByName() {
    }

    @Test
    void findPageCategories() {
    }

    @Test
    void findCategoriesByName() {
    }

    @Test
    void createCategory() {
    }

    @Test
    void editCategory() {
    }

    @Test
    void deleteCategory() {
    }
}