package com.example.backend.test.service;

import com.example.backend.model.Category;
import com.example.backend.model.Course;
import com.example.backend.model.User;
import com.example.backend.repository.CategoryRepository;
import com.example.backend.repository.CourseRepository;
import com.example.backend.service.CategoryService;
import com.example.backend.service.CourseService;
import com.example.backend.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CourseServiceTest {

    @Mock private CourseRepository courseRepository;;

    @Mock private UserService userService;

    @Mock private CategoryService categoryService;

    private CourseService underTest;

    @BeforeEach
    private void setUp(){
        underTest = new CourseService(courseRepository, userService, categoryService);
    }

    @Test
    void findCourses() {
        //when
        underTest.findCourses();
        //then
        verify(courseRepository).findAll();
    }

    @Test
    void findPageCourses() {
        //given
        int numberPage = 0;
        int content = 10;
        //when
        underTest.findPageCourses(numberPage, content);
        //then
        verify(courseRepository).findAll(PageRequest.of(numberPage, content));
    }

    @Test
    void findCourseById() {
        //given
        Long id = 1L;
        //when
        underTest.findCourseById(id);
        //then
        verify(courseRepository).findById(id);
    }

    @Test
    void findCourseByName() {
        //given
        String name = "name";
        //when
        underTest.findCourseByName(name);
        //then
        verify(courseRepository).findCourseByName(name);
    }

    @Test
    void findCoursesByCategory() {
        //given
        Category category = new Category("name");
        //when
        underTest.findCoursesByCategory(category);
        //then
        verify(courseRepository).findCoursesByCategory(category);
    }

    @Test
    void findPageCoursesByCategory() {
        //given
        int numberPage = 0;
        Category category = new Category("name");
        //when
        underTest.findPageCoursesByCategory(numberPage, category);
        //then
        verify(courseRepository).findCoursesByCategory(category, PageRequest.of(numberPage, 12));
    }

    @Test
    void create() throws IOException {
        //given
        Category category = new Category("CategoryName");
        User user = new User("UserName", "UserSurname", "UserEmail", "UserPassword");
        String nameCourse = "CourseName";
        String descriptionCourse = "CourseDescription";
        String difficulty = "Principiante";
        String imagePath = "/static/assets/images/categories/personal/dificil.png";
        int length = 5;
        Course course = new Course("CourseName", "CourseDescription", category, "Principiante", 5, user);
        //when
        when(userService.findUserById(user.getId())).thenReturn(Optional.of(user));
        when(courseRepository.save(any())).thenReturn(course);
        underTest.create(nameCourse, descriptionCourse, category, difficulty, imagePath, length, user.getId());
        //then
        ArgumentCaptor<Course> courseArgumentCaptor = ArgumentCaptor.forClass(Course.class);
        verify(courseRepository).save(courseArgumentCaptor.capture());
        Course capturedCourse = courseArgumentCaptor.getValue();
        assertEquals(capturedCourse.getName(), nameCourse);
        verify(userService).addUserCourse(any(), any());
    }

    @Test
    void createCourse() throws IOException {
        //given
        String nameCategory = "CategoryName";
        Category category = new Category(nameCategory);
        User user = new User("UserName", "UserSurname", "UserEmail", "UserPassword");
        String nameCourse = "CourseName";
        MultipartFile image = null;
        Course course = new Course("CourseName", "CourseDescription", category, "Principiante", 5, user);
        //when
        when(userService.getActiveUser()).thenReturn(Optional.of(user));
        when(categoryService.findByName(nameCategory)).thenReturn(Optional.of(category));
        when(courseRepository.save(any())).thenReturn(course);
        underTest.createCourse(course, nameCategory, image);
        //then
        ArgumentCaptor<Course> courseArgumentCaptor = ArgumentCaptor.forClass(Course.class);
        verify(courseRepository).save(courseArgumentCaptor.capture());
        Course capturedCourse = courseArgumentCaptor.getValue();
        assertEquals(capturedCourse.getAuthor(), user);
        assertEquals(capturedCourse.getCategory(), category);
        verify(userService).addUserCourse(any(), any());
    }

    @Test
    void updateCourse() throws IOException {
        //given
        String nameCategory = "CategoryName";
        Category category = new Category(nameCategory);
        User user = new User("UserName", "UserSurname", "UserEmail", "UserPassword");
        String nameCourse = "CourseName";
        MultipartFile image = null;
        Course oldCourse = new Course("CourseName", "CourseDescription", category, "Principiante", 5, user);
        Course newCourse = new Course("nameModified", "descriptionModified", category, "Intermedio", 6, user);
        //when
        when(courseRepository.findById(any())).thenReturn(Optional.of(oldCourse));
        when(categoryService.findByName(nameCategory)).thenReturn(Optional.of(category));
        when(courseRepository.save(any())).thenReturn(newCourse);
        underTest.updateCourse(oldCourse.getId(), newCourse, nameCategory, image);
        //then
        ArgumentCaptor<Course> courseArgumentCaptor = ArgumentCaptor.forClass(Course.class);
        verify(courseRepository).save(courseArgumentCaptor.capture());
        Course capturedCourse = courseArgumentCaptor.getValue();
        assertEquals(capturedCourse.getName(), "nameModified");
    }

    @Test
    void save() {
        //given
        Category category = new Category("CategoryName");
        User user = new User("UserName", "UserSurname", "UserEmail", "UserPassword");
        Course course = new Course("CourseName", "CourseDescription", category, "Principiante", 5, user);
        //when
        underTest.save(course);
        //then
        verify(courseRepository).save(course);
    }

    @Test
    void findCoursesByName() {
        //given
        String name = "name";
        //when
        underTest.findCoursesByName(name);
        //then
        verify(courseRepository).findCoursesByName(name);
    }

    @Test
    void like() {
        //given
        Category category = new Category("CategoryName");
        User user = new User("UserName", "UserSurname", "UserEmail", "UserPassword");
        Course course = new Course("CourseName", "CourseDescription", category, "Principiante", 5, user);
        //when
        underTest.like(course);
        //then
        ArgumentCaptor<Course> courseArgumentCaptor = ArgumentCaptor.forClass(Course.class);
        verify(courseRepository).save(courseArgumentCaptor.capture());
        Course capturedCourse = courseArgumentCaptor.getValue();
        assertEquals(capturedCourse.getLikes(), 1);
    }

    @Test
    void quitLike() {
        //given
        Category category = new Category("CategoryName");
        User user = new User("UserName", "UserSurname", "UserEmail", "UserPassword");
        Course course = new Course("CourseName", "CourseDescription", category, "Principiante", 5, user);
        course.like();
        //when
        underTest.quitLike(course);
        //then
        ArgumentCaptor<Course> courseArgumentCaptor = ArgumentCaptor.forClass(Course.class);
        verify(courseRepository).save(courseArgumentCaptor.capture());
        Course capturedCourse = courseArgumentCaptor.getValue();
        assertEquals(capturedCourse.getLikes(), 0);
    }

    @Test
    void dislike() {
        //given
        Category category = new Category("CategoryName");
        User user = new User("UserName", "UserSurname", "UserEmail", "UserPassword");
        Course course = new Course("CourseName", "CourseDescription", category, "Principiante", 5, user);
        //when
        underTest.dislike(course);
        //then
        ArgumentCaptor<Course> courseArgumentCaptor = ArgumentCaptor.forClass(Course.class);
        verify(courseRepository).save(courseArgumentCaptor.capture());
        Course capturedCourse = courseArgumentCaptor.getValue();
        assertEquals(capturedCourse.getDislikes(), 1);
    }

    @Test
    void quitDislike() {
        //given
        Category category = new Category("CategoryName");
        User user = new User("UserName", "UserSurname", "UserEmail", "UserPassword");
        Course course = new Course("CourseName", "CourseDescription", category, "Principiante", 5, user);
        course.dislike();
        //when
        underTest.quitDislike(course);
        //then
        ArgumentCaptor<Course> courseArgumentCaptor = ArgumentCaptor.forClass(Course.class);
        verify(courseRepository).save(courseArgumentCaptor.capture());
        Course capturedCourse = courseArgumentCaptor.getValue();
        assertEquals(capturedCourse.getLikes(), 0);
    }

    @Test
    void banCourse() {
        //given
        Category category = new Category("CategoryName");
        User user = new User("UserName", "UserSurname", "UserEmail", "UserPassword");
        Course course = new Course("CourseName", "CourseDescription", category, "Principiante", 5, user);
        //when
        when(courseRepository.findById(course.getId())).thenReturn(Optional.of(course));
        underTest.banCourse(course.getId());
        //then
        ArgumentCaptor<Course> courseArgumentCaptor = ArgumentCaptor.forClass(Course.class);
        verify(courseRepository).save(courseArgumentCaptor.capture());
        Course capturedCourse = courseArgumentCaptor.getValue();
        assertTrue(capturedCourse.isBanned());
    }

    @Test
    void unbanCourse() {
        //given
        Category category = new Category("CategoryName");
        User user = new User("UserName", "UserSurname", "UserEmail", "UserPassword");
        Course course = new Course("CourseName", "CourseDescription", category, "Principiante", 5, user);
        course.ban();
        //when
        when(courseRepository.findById(course.getId())).thenReturn(Optional.of(course));
        underTest.unbanCourse(course.getId());
        //then
        ArgumentCaptor<Course> courseArgumentCaptor = ArgumentCaptor.forClass(Course.class);
        verify(courseRepository).save(courseArgumentCaptor.capture());
        Course capturedCourse = courseArgumentCaptor.getValue();
        assertFalse(capturedCourse.isBanned());
    }

    @Test
    void adminDeleteCourse() {
        //given
        Category category = new Category("CategoryName");
        User user = new User("UserName", "UserSurname", "UserEmail", "UserPassword");
        user.setAdmin(true);
        Course course = new Course("CourseName", "CourseDescription", category, "Principiante", 5, user);
        user.addUserCourse(course);
        //when
        when(courseRepository.findById(any())).thenReturn(Optional.of(course));
        when(userService.getActiveUser()).thenReturn(Optional.of(user));
        when(userService.findCourseOwner(course)).thenReturn(Optional.of(user));
        when(userService.findUsersByEnrolledCourse(any())).thenReturn(new LinkedList<>());
        when(userService.findUsersByWishedCourse(any())).thenReturn(new LinkedList<>());
        when(userService.findUsersByCompletedCourse(any())).thenReturn(new LinkedList<>());

        underTest.adminDeleteCourse(course.getId());
        //then
        ArgumentCaptor<Course> courseArgumentCaptor = ArgumentCaptor.forClass(Course.class);
        verify(courseRepository).delete(courseArgumentCaptor.capture());
        Course capturedCourse = courseArgumentCaptor.getValue();
        assertEquals(capturedCourse.getName(), "CourseName");
    }
}