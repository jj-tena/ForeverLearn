package com.example.backend.test.repository;

import com.example.backend.model.Category;
import com.example.backend.model.Course;
import com.example.backend.model.User;
import com.example.backend.repository.CategoryRepository;
import com.example.backend.repository.CourseRepository;
import com.example.backend.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class CourseRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private CourseRepository underTest;

    @BeforeEach
    void setUp(){
    }

    @AfterEach
    void tearDown(){
        userRepository.deleteAll();
        categoryRepository.deleteAll();
        underTest.deleteAll();
    }

    @Test
    void findCoursesByCategory() {
        //given
        Category category1 = new Category("CategoryName1");
        categoryRepository.save(category1);
        Category category2 = new Category("CategoryName2");
        categoryRepository.save(category2);
        User user = new User("UserName", "UserSurname", "UserEmail", "UserPassword");
        userRepository.save(user);
        Course course1 = new Course("Name1", "Description1", category1, "Principiante", 5, user);
        underTest.save(course1);
        Course course2 = new Course("Name2", "Description2", category2, "Intermedio", 6, user);
        underTest.save(course2);
        Course course3 = new Course("Name1", "Description3", category1, "Avanzado", 7, user);
        underTest.save(course3);
        //when
        List<Course> coursesByCategory = underTest.findCoursesByCategory(category1);
        //then
        assertFalse(coursesByCategory.isEmpty());
        assertEquals(coursesByCategory.size(), 2);
    }

    @Test
    void findCourseByName() {
        //given
        Category category = new Category("CategoryName");
        categoryRepository.save(category);
        User user = new User("UserName", "UserSurname", "UserEmail", "UserPassword");
        userRepository.save(user);
        Course course1 = new Course("Name1", "Description1", category, "Principiante", 5, user);
        underTest.save(course1);
        //when
        Optional<Course> optionalCourse = underTest.findCourseByName("Name1");
        //then
        assertTrue(optionalCourse.isPresent());
        assertEquals(optionalCourse.get().getDescription(), "Description1");
    }

    @Test
    void findAll() {
        //given
        Category category = new Category("CategoryName");
        categoryRepository.save(category);
        User user = new User("UserName", "UserSurname", "UserEmail", "UserPassword");
        userRepository.save(user);
        Course course1 = new Course("Name1", "Description1", category, "Principiante", 5, user);
        underTest.save(course1);
        Course course2 = new Course("Name2", "Description2", category, "Intermedio", 6, user);
        underTest.save(course2);
        Course course3 = new Course("Name3", "Description3", category, "Avanzado", 7, user);
        underTest.save(course3);
        //when
        Page<Course> coursePage = underTest.findAll(PageRequest.of(0, 10));
        //then
        assertTrue(coursePage.hasContent());
        assertEquals(coursePage.getNumberOfElements(), 3);
    }

    @Test
    void testFindPageCoursesByCategory() {
        //given
        Category category1 = new Category("CategoryName1");
        categoryRepository.save(category1);
        Category category2 = new Category("CategoryName2");
        categoryRepository.save(category2);
        User user = new User("UserName", "UserSurname", "UserEmail", "UserPassword");
        userRepository.save(user);
        Course course1 = new Course("Name1", "Description1", category1, "Principiante", 5, user);
        underTest.save(course1);
        Course course2 = new Course("Name2", "Description2", category2, "Intermedio", 6, user);
        underTest.save(course2);
        Course course3 = new Course("Name1", "Description3", category1, "Avanzado", 7, user);
        underTest.save(course3);
        //when
        Page<Course> coursesByCategory = underTest.findCoursesByCategory(category1, PageRequest.of(0, 10));
        //then
        assertTrue(coursesByCategory.hasContent());
        assertEquals(coursesByCategory.getNumberOfElements(), 2);
    }

    @Test
    void findCoursesByName() {
        //given
        Category category = new Category("CategoryName");
        categoryRepository.save(category);
        User user = new User("UserName", "UserSurname", "UserEmail", "UserPassword");
        userRepository.save(user);
        Course course1 = new Course("Name1", "Description1", category, "Principiante", 5, user);
        underTest.save(course1);
        Course course2 = new Course("Name2", "Description2", category, "Intermedio", 6, user);
        underTest.save(course2);
        Course course3 = new Course("Name1", "Description3", category, "Avanzado", 7, user);
        underTest.save(course3);
        //when
        Optional<List<Course>> optionalCourses = underTest.findCoursesByName("Name1");
        //then
        assertTrue(optionalCourses.isPresent());
        assertEquals(optionalCourses.get().size(), 2);
    }
}