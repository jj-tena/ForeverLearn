package com.example.backend.test.service;

import com.example.backend.model.Category;
import com.example.backend.model.Theme;
import com.example.backend.model.User;
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
import org.springframework.data.domain.PageRequest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.Blob;
import java.util.LinkedList;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

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
        assertEquals(capturedCategory.getName(), category.getName());
    }

    @Test
    void findById() {
        //given
        String name = "Demo";
        Category category = new Category(name);
        //when
        Optional<Category> optionalCategory = Optional.of(category);
        when(categoryRepository.findById(category.getId())).thenReturn(optionalCategory);
        Optional<Category> categoryFound = underTest.findById(category.getId());
        //then
        assertTrue(categoryFound.isPresent());
        assertEquals(categoryFound.get().getName(), name);
    }

    @Test
    void findAll() {
        //when
        underTest.findAll();
        //then
        verify(categoryRepository).findAll();
    }

    @Test
    void findByName() {
        //given
        String name = "Demo";
        Category category = new Category(name);
        //when
        underTest.findByName(name);
        //then
        verify(categoryRepository).findByName(name);
    }

    @Test
    void findPageCategories() {
        //given
        int numberPage = 0;
        int content = 10;
        //when
        underTest.findPageCategories(numberPage, content);
        //then
        verify(categoryRepository).findAll(PageRequest.of(numberPage, content));
    }

    @Test
    void findCategoriesByName() {
        //given
        String name = "Demo";
        Category category = new Category(name);
        //when
        underTest.findCategoriesByName(name);
        //then
        verify(categoryRepository).findAllByName(name);
    }

    @Test
    void createCategory() throws IOException {
        //given
        String categoryName = "categoryName";
        Category category = new Category(categoryName);
        Optional<Category> optionalCategory = Optional.of(category);
        //when
        User user = new User("UserName", "UserSurname", "UserEmail", "UserPassword");
        user.setAdmin(true);
        Optional<User> optionalUser = Optional.of(user);
        when(userService.getActiveUser()).thenReturn(optionalUser);
        when(categoryRepository.save(any())).thenReturn(category);
        underTest.createCategory(categoryName, null);
        //then
        ArgumentCaptor<Category> categoryArgumentCaptor = ArgumentCaptor.forClass(Category.class);
        verify(categoryRepository).save(categoryArgumentCaptor.capture());
        Category capturedCategory = categoryArgumentCaptor.getValue();
        assertEquals(capturedCategory.getName(), categoryName);
    }

    @Test
    void editCategory() throws IOException {
        //given
        String categoryName = "categoryName";
        Category category = new Category(categoryName);
        Optional<Category> optionalCategory = Optional.of(category);
        String newName = "nameModified";
        //when
        User user = new User("UserName", "UserSurname", "UserEmail", "UserPassword");
        user.setAdmin(true);
        Optional<User> optionalUser = Optional.of(user);
        when(userService.getActiveUser()).thenReturn(optionalUser);
        when(categoryRepository.findById(category.getId())).thenReturn(optionalCategory);
        when(categoryRepository.save(any())).thenReturn(category);
        underTest.editCategory(newName, null, category.getId());
        //then
        ArgumentCaptor<Category> categoryArgumentCaptor = ArgumentCaptor.forClass(Category.class);
        verify(categoryRepository).save(categoryArgumentCaptor.capture());
        Category capturedCategory = categoryArgumentCaptor.getValue();
        assertEquals(capturedCategory.getName(), newName);
    }

    @Test
    void deleteCategory() {
        //given
        String categoryName = "categoryName";
        Category category = new Category(categoryName);
        Optional<Category> optionalCategory = Optional.of(category);
        String nameOthers = "Otros";
        Category categoryOthers = new Category(nameOthers);
        Optional<Category> optionalCategoryOthers = Optional.of(categoryOthers);
        //when
        User user = new User("UserName", "UserSurname", "UserEmail", "UserPassword");
        user.setAdmin(true);
        Optional<User> optionalUser = Optional.of(user);
        when(userService.getActiveUser()).thenReturn(optionalUser);
        when(categoryRepository.findById(category.getId())).thenReturn(optionalCategory);
        when(categoryRepository.findByName("Otros")).thenReturn(optionalCategoryOthers);
        when(courseRepository.findCoursesByCategory(any())).thenReturn(new LinkedList<>());
        underTest.deleteCategory(category.getId());
        //then
        ArgumentCaptor<Category> categoryArgumentCaptor = ArgumentCaptor.forClass(Category.class);
        verify(categoryRepository).delete(categoryArgumentCaptor.capture());
        Category capturedCategory = categoryArgumentCaptor.getValue();
        assertEquals(capturedCategory.getName(), categoryName);
    }
}