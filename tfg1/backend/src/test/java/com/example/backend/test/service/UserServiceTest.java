package com.example.backend.test.service;

import com.example.backend.model.Category;
import com.example.backend.model.Course;
import com.example.backend.model.Theme;
import com.example.backend.model.User;
import com.example.backend.repository.CourseRepository;
import com.example.backend.repository.ThemeRepository;
import com.example.backend.repository.UserRepository;
import com.example.backend.service.CourseService;
import com.example.backend.service.ThemeService;
import com.example.backend.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock private UserRepository userRepository;

    @Mock private CourseRepository courseRepository;

    private UserService underTest;

    @BeforeEach
    private void setUp(){
        underTest = new UserService(userRepository, courseRepository);
    }

    @Test
    void create() throws IOException {
        //given
        String name = "name";
        String surname = "surname";
        String email = "email";
        String password = "password";
        User user = new User(name, surname, email, password);
        //when
        underTest.create(user);
        //then
        ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(userArgumentCaptor.capture());
        User capturedUser = userArgumentCaptor.getValue();
        assertEquals(capturedUser.getName(), name);
    }

    @Test
    void createFromParameters() throws IOException {
        //given
        String name = "name";
        String surname = "surname";
        String email = "email";
        String password = "password";
        String imagePath = "/static/assets/images/people/110/sergioPeinado.jpg";
        String description = "description";
        String contact = "contact";
        String facebook = "facebook";
        String twitter = "twitter";
        String youtube = "youtube";
        //when
        underTest.createFromParameters(name, surname, email, password, imagePath, description, contact, facebook, twitter, youtube);
        //then
        ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(userArgumentCaptor.capture());
        User capturedUser = userArgumentCaptor.getValue();
        assertEquals(capturedUser.getName(), name);
    }

    @Test
    void login() {
        //given
        String name = "name";
        String surname = "surname";
        String email = "email";
        String password = "password";
        User user = new User(name, surname, email, password);
        //when
        Optional<User> optionalUser = Optional.of(user);
        when(userRepository.findUserByEmailAndPassword(email, password)).thenReturn(optionalUser);
        Optional<User> foundUser = underTest.login(user);
        //then
        assertTrue(foundUser.isPresent());
        assertEquals(foundUser.get().getName(), name);
    }

    @Test
    void updateBasicInformation() {
        //given
        String name = "name";
        String surname = "surname";
        String email = "email";
        String password = "password";
        User user = new User(name, surname, email, password);
        //when
        Optional<User> optionalUser = Optional.of(user);
        when(underTest.getActiveUser()).thenReturn(optionalUser);
        underTest.updateBasicInformation(user);
        //then
        ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(userArgumentCaptor.capture());
        User capturedUser = userArgumentCaptor.getValue();
        assertEquals(capturedUser.getName(), name);
    }

    @Test
    void updateProfileInformation() throws SQLException, IOException {
        //given
        String name = "name";
        String surname = "surname";
        String email = "email";
        String password = "password";
        User user = new User(name, surname, email, password);
        String description = "description";
        String contact = "contact";
        String facebook = "facebook";
        String twitter = "twitter";
        String youtube = "youtube";
        user.setDescription(description);
        user.setContact(contact);
        user.setFacebook(facebook);
        user.setTwitter(twitter);
        user.setYoutube(youtube);
        //when
        Optional<User> optionalUser = Optional.of(user);
        when(underTest.getActiveUser()).thenReturn(optionalUser);
        underTest.updateProfileInformation(user, null);
        //then
        ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(userArgumentCaptor.capture());
        User capturedUser = userArgumentCaptor.getValue();
        assertEquals(capturedUser.getName(), name);
    }

    @Test
    void updatePassword() {
        //given
        String name = "name";
        String surname = "surname";
        String email = "email";
        String password = "password";
        User user = new User(name, surname, email, password);
        String newPassword1 = "1234";
        String newPassword2 = "1234";
        //when
        Optional<User> optionalUser = Optional.of(user);
        when(underTest.getActiveUser()).thenReturn(optionalUser);
        underTest.updatePassword(newPassword1, newPassword2);
        //then
        ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(userArgumentCaptor.capture());
        User capturedUser = userArgumentCaptor.getValue();
        assertEquals(capturedUser.getPassword(), newPassword1);
    }

    @Test
    void getUserCourses() {
        //given
        String name = "name";
        String surname = "surname";
        String email = "email";
        String password = "password";
        User user = new User(name, surname, email, password);
        //when
        Optional<User> optionalUser = Optional.of(user);
        when(underTest.getActiveUser()).thenReturn(optionalUser);
        List<Course> userCourses = underTest.getUserCourses();
        //then
        assertEquals(userCourses, null);
    }

    @Test
    void findUserById() {
        //given
        String name = "name";
        String surname = "surname";
        String email = "email";
        String password = "password";
        User user = new User(name, surname, email, password);
        //when
        Optional<User> optionalUser = Optional.of(user);
        when(userRepository.findUserById(user.getId())).thenReturn(optionalUser);
        Optional<User> userById = underTest.findUserById(user.getId());
        //then
        assertTrue(userById.isPresent());
        assertEquals(userById.get().getName(), name);
    }

    @Test
    void addUserCourse() {
        //given
        String nameCategory = "CategoryName";
        Category category = new Category(nameCategory);
        String userName = "UserName";
        User user = new User("UserName", "UserSurname", "UserEmail", "UserPassword");
        String nameCourse = "CourseName";
        Course course = new Course("CourseName", "CourseDescription", category, "Principiante", 5, user);
        //when
        underTest.addUserCourse(user, course);
        //then
        ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(userArgumentCaptor.capture());
        User capturedUser = userArgumentCaptor.getValue();
        assertEquals(capturedUser.getName(), userName);
    }

    @Test
    void deleteCourse() {
        //given
        String nameCategory = "CategoryName";
        Category category = new Category(nameCategory);
        String userName = "UserName";
        User user = new User("UserName", "UserSurname", "UserEmail", "UserPassword");
        String nameCourse = "CourseName";
        Course course = new Course("CourseName", "CourseDescription", category, "Principiante", 5, user);
        course.setAuthor(user);
        user.addUserCourse(course);
        //when
        Optional<User> optionalUser = Optional.of(user);
        when(userRepository.findUserByUserCoursesContains(course)).thenReturn(optionalUser);
        when(userRepository.findUsersByEnrolledCoursesContaining(course)).thenReturn(new LinkedList<>());
        when(userRepository.findUsersByWishedCoursesContaining(course)).thenReturn(new LinkedList<>());
        when(userRepository.findUsersByCompletedCoursesContaining(course)).thenReturn(new LinkedList<>());
        underTest.deleteCourse(user, course);
        //then
        ArgumentCaptor<Course> courseArgumentCaptor = ArgumentCaptor.forClass(Course.class);
        verify(courseRepository).delete(courseArgumentCaptor.capture());
        Course capturedCourse = courseArgumentCaptor.getValue();
        assertEquals(capturedCourse.getName(), nameCourse);
    }

    @Test
    void enrollCourse() {
        //given
        String nameCategory = "CategoryName";
        Category category = new Category(nameCategory);
        String userName = "UserName";
        User user = new User("UserName", "UserSurname", "UserEmail", "UserPassword");
        Course course = new Course("CourseName", "CourseDescription", category, "Principiante", 5, user);
        //when
        Optional<User> optionalUser = Optional.of(user);
        when(underTest.getActiveUser()).thenReturn(optionalUser);
        Optional<Course> optionalCourse = Optional.of(course);
        when(courseRepository.findById(course.getId())).thenReturn(optionalCourse);
        underTest.enrollCourse(course.getId());
        //then
        ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(userArgumentCaptor.capture());
        User capturedUser = userArgumentCaptor.getValue();
        assertEquals(capturedUser.getEnrolledCourses().size(), 1);
    }

    @Test
    void isCourseOwner() {
        //given
        String nameCategory = "CategoryName";
        Category category = new Category(nameCategory);
        String userName = "UserName";
        User user = new User("UserName", "UserSurname", "UserEmail", "UserPassword");
        String nameCourse = "CourseName";
        Course course = new Course("CourseName", "CourseDescription", category, "Principiante", 5, user);
        course.setAuthor(user);
        user.addUserCourse(course);
        //when
        Boolean courseOwner = underTest.isCourseOwner(user, course);
        //then
        assert(courseOwner);

    }

    @Test
    void isCourseEnrolled() {
        //given
        String nameCategory = "CategoryName";
        Category category = new Category(nameCategory);
        String userName = "UserName";
        User user = new User("UserName", "UserSurname", "UserEmail", "UserPassword");
        String nameCourse = "CourseName";
        Course course = new Course("CourseName", "CourseDescription", category, "Principiante", 5, user);
        user.addEnrolledCourse(course);
        //when
        Boolean courseEnrolled = underTest.isCourseEnrolled(user, course);
        //then
        assert(courseEnrolled);
    }

    @Test
    void unenrollCourse() {
        //given
        String nameCategory = "CategoryName";
        Category category = new Category(nameCategory);
        String userName = "UserName";
        User user = new User("UserName", "UserSurname", "UserEmail", "UserPassword");
        Course course = new Course("CourseName", "CourseDescription", category, "Principiante", 5, user);
        user.addEnrolledCourse(course);
        //when
        Optional<User> optionalUser = Optional.of(user);
        when(underTest.getActiveUser()).thenReturn(optionalUser);
        Optional<Course> optionalCourse = Optional.of(course);
        when(courseRepository.findById(course.getId())).thenReturn(optionalCourse);
        underTest.unenrollCourse(course.getId());
        //then
        ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(userArgumentCaptor.capture());
        User capturedUser = userArgumentCaptor.getValue();
        assertEquals(capturedUser.getEnrolledCourses().size(), 0);
    }

    @Test
    void makeAdmin() {
        //given
        String name = "name";
        String surname = "surname";
        String email = "email";
        String password = "password";
        User user = new User(name, surname, email, password);
        //when
        Optional<User> optionalUser = Optional.of(user);
        when(userRepository.findUserById(user.getId())).thenReturn(optionalUser);
        when(userRepository.save(user)).thenReturn(user);
        underTest.makeAdmin(user.getId());
        //then
        ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(userArgumentCaptor.capture());
        User capturedUser = userArgumentCaptor.getValue();
        assert(capturedUser.isAdmin());
    }

    @Test
    void findPageUsers() {
        //given
        //when
        Page<User> pageUsers = underTest.findPageUsers(0, 6);
        //then
        assertEquals(pageUsers, null);
    }

    @Test
    void findUserByName() {
        //given
        String name = "name";
        String surname = "surname";
        String email = "email";
        String password = "password";
        User user = new User(name, surname, email, password);
        //when
        List<User> list = new LinkedList<>();
        list.add(user);
        Optional<List<User>> optionalUserList = Optional.of(list);
        when(userRepository.findUsersByName(name)).thenReturn(optionalUserList);
        Optional<List<User>> usersByName = underTest.findUserByName(name);
        //then
        assertTrue(usersByName.isPresent());
        assertEquals(usersByName.get().get(0).getName(), name);
    }

    @Test
    void wishCourse() {
        //given
        String nameCategory = "CategoryName";
        Category category = new Category(nameCategory);
        String userName = "UserName";
        User user = new User("UserName", "UserSurname", "UserEmail", "UserPassword");
        Course course = new Course("CourseName", "CourseDescription", category, "Principiante", 5, user);
        //when
        Optional<User> optionalUser = Optional.of(user);
        when(underTest.getActiveUser()).thenReturn(optionalUser);
        Optional<Course> optionalCourse = Optional.of(course);
        when(courseRepository.findById(course.getId())).thenReturn(optionalCourse);
        underTest.wishCourse(course.getId());
        //then
        ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(userArgumentCaptor.capture());
        User capturedUser = userArgumentCaptor.getValue();
        assertEquals(capturedUser.getWishedCourses().size(), 1);
    }

    @Test
    void unwishCourse() {
        //given
        String nameCategory = "CategoryName";
        Category category = new Category(nameCategory);
        String userName = "UserName";
        User user = new User("UserName", "UserSurname", "UserEmail", "UserPassword");
        Course course = new Course("CourseName", "CourseDescription", category, "Principiante", 5, user);
        user.addWishedCourse(course);
        //when
        Optional<User> optionalUser = Optional.of(user);
        when(underTest.getActiveUser()).thenReturn(optionalUser);
        Optional<Course> optionalCourse = Optional.of(course);
        when(courseRepository.findById(course.getId())).thenReturn(optionalCourse);
        underTest.unwishCourse(course.getId());
        //then
        ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(userArgumentCaptor.capture());
        User capturedUser = userArgumentCaptor.getValue();
        assertEquals(capturedUser.getWishedCourses().size(), 0);
    }

    @Test
    void isCourseWished() {
        //given
        String nameCategory = "CategoryName";
        Category category = new Category(nameCategory);
        String userName = "UserName";
        User user = new User("UserName", "UserSurname", "UserEmail", "UserPassword");
        String nameCourse = "CourseName";
        Course course = new Course("CourseName", "CourseDescription", category, "Principiante", 5, user);
        user.addWishedCourse(course);
        //when
        Boolean courseWished = underTest.isCourseWished(user, course);
        //then
        assert(courseWished);
    }

    @Test
    void likeCourse() {
        //given
        String nameCategory = "CategoryName";
        Category category = new Category(nameCategory);
        User user = new User("UserName", "UserSurname", "UserEmail", "UserPassword");
        Course course = new Course("CourseName", "CourseDescription", category, "Principiante", 5, user);
        //when
        underTest.likeCourse(user, course);
        //then
        ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(userArgumentCaptor.capture());
        User capturedUser = userArgumentCaptor.getValue();
        assertEquals(capturedUser.getLikedCourses().size(), 1);
    }

    @Test
    void dislikeCourse() {
        //given
        String nameCategory = "CategoryName";
        Category category = new Category(nameCategory);
        User user = new User("UserName", "UserSurname", "UserEmail", "UserPassword");
        Course course = new Course("CourseName", "CourseDescription", category, "Principiante", 5, user);
        //when
        underTest.dislikeCourse(user, course);
        //then
        ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(userArgumentCaptor.capture());
        User capturedUser = userArgumentCaptor.getValue();
        assertEquals(capturedUser.getDislikedCourses().size(), 1);
    }

    @Test
    void quitLikeCourse() {
        //given
        String nameCategory = "CategoryName";
        Category category = new Category(nameCategory);
        User user = new User("UserName", "UserSurname", "UserEmail", "UserPassword");
        Course course = new Course("CourseName", "CourseDescription", category, "Principiante", 5, user);
        user.likeCourse(course);
        //when
        underTest.quitLikeCourse(user, course);
        //then
        ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(userArgumentCaptor.capture());
        User capturedUser = userArgumentCaptor.getValue();
        assert(capturedUser.getLikedCourses().isEmpty());
    }

    @Test
    void quitDislikeCourse() {
        //given
        String nameCategory = "CategoryName";
        Category category = new Category(nameCategory);
        User user = new User("UserName", "UserSurname", "UserEmail", "UserPassword");
        Course course = new Course("CourseName", "CourseDescription", category, "Principiante", 5, user);
        user.dislikeCourse(course);
        //when
        underTest.quitDislikeCourse(user, course);
        //then
        ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(userArgumentCaptor.capture());
        User capturedUser = userArgumentCaptor.getValue();
        assert(capturedUser.getDislikedCourses().isEmpty());
    }

    @Test
    void isCourseLiked() {
        //given
        String nameCategory = "CategoryName";
        Category category = new Category(nameCategory);
        User user = new User("UserName", "UserSurname", "UserEmail", "UserPassword");
        Course course = new Course("CourseName", "CourseDescription", category, "Principiante", 5, user);
        user.likeCourse(course);
        //when
        Boolean courseLiked = underTest.isCourseLiked(user, course);
        //then
        assert(courseLiked);
    }

    @Test
    void isCourseDisliked() {
        //given
        String nameCategory = "CategoryName";
        Category category = new Category(nameCategory);
        User user = new User("UserName", "UserSurname", "UserEmail", "UserPassword");
        Course course = new Course("CourseName", "CourseDescription", category, "Principiante", 5, user);
        user.dislikeCourse(course);
        //when
        Boolean courseDisliked = underTest.isCourseDisliked(user, course);
        //then
        assert(courseDisliked);
    }

    @Test
    void findCourseOwner() {
        //given
        String nameCategory = "CategoryName";
        Category category = new Category(nameCategory);
        User user = new User("UserName", "UserSurname", "UserEmail", "UserPassword");
        Course course = new Course("CourseName", "CourseDescription", category, "Principiante", 5, user);
        user.addUserCourse(course);
        //when
        Optional<User> optionalUser = Optional.of(user);
        when(userRepository.findUserByUserCoursesContains(course)).thenReturn(optionalUser);
        Optional<User> courseOwner = underTest.findCourseOwner(course);
        //then
        assert(courseOwner.isPresent());
        assertEquals(courseOwner.get().getName(), "UserName");
    }

    @Test
    void findUsersByEnrolledCourse() {
        //given
        String nameCategory = "CategoryName";
        Category category = new Category(nameCategory);
        User user = new User("UserName", "UserSurname", "UserEmail", "UserPassword");
        Course course = new Course("CourseName", "CourseDescription", category, "Principiante", 5, user);
        user.addEnrolledCourse(course);
        //when
        List<User> userList = new LinkedList<>();
        userList.add(user);
        when(userRepository.findUsersByEnrolledCoursesContaining(course)).thenReturn(userList);
        List<User> enrolledCourse = underTest.findUsersByEnrolledCourse(course);
        //then
        assertEquals(enrolledCourse.size(), 1);
    }

    @Test
    void findUsersByCompletedCourse() {
        //given
        String nameCategory = "CategoryName";
        Category category = new Category(nameCategory);
        User user = new User("UserName", "UserSurname", "UserEmail", "UserPassword");
        Course course = new Course("CourseName", "CourseDescription", category, "Principiante", 5, user);
        user.addCompletedCourse(course);
        //when
        List<User> userList = new LinkedList<>();
        userList.add(user);
        when(userRepository.findUsersByCompletedCoursesContaining(course)).thenReturn(userList);
        List<User> completedCourse = underTest.findUsersByCompletedCourse(course);
        //then
        assertEquals(completedCourse.size(), 1);
    }

    @Test
    void findUsersByWishedCourse() {
        //given
        String nameCategory = "CategoryName";
        Category category = new Category(nameCategory);
        User user = new User("UserName", "UserSurname", "UserEmail", "UserPassword");
        Course course = new Course("CourseName", "CourseDescription", category, "Principiante", 5, user);
        user.addWishedCourse(course);
        //when
        List<User> userList = new LinkedList<>();
        userList.add(user);
        when(userRepository.findUsersByWishedCoursesContaining(course)).thenReturn(userList);
        List<User> wishedCourse = underTest.findUsersByWishedCourse(course);
        //then
        assertEquals(wishedCourse.size(), 1);
    }

    @Test
    void banUser() {
        //given
        User user = new User("UserName", "UserSurname", "UserEmail", "UserPassword");
        //when
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        underTest.banUser(user.getId());
        //then
        ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(userArgumentCaptor.capture());
        User capturedUser = userArgumentCaptor.getValue();
        assert(capturedUser.isBanned());
    }

    @Test
    void unbanUser() {
        //given
        User user = new User("UserName", "UserSurname", "UserEmail", "UserPassword");
        //when
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        underTest.unbanUser(user.getId());
        //then
        ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(userArgumentCaptor.capture());
        User capturedUser = userArgumentCaptor.getValue();
        assertFalse(capturedUser.isBanned());
    }

    @Test
    void adminDeleteUser() {
        //given
        User user = new User("UserName", "UserSurname", "UserEmail", "UserPassword");
        //when
        when(userRepository.findUserById(user.getId())).thenReturn(Optional.of(user));
        underTest.adminDeleteUser(user.getId());
        //then
        ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).delete(userArgumentCaptor.capture());
        User capturedUser = userArgumentCaptor.getValue();
        assertEquals(capturedUser.getName(), "UserName");
    }

    @Test
    void deleteAccount() {
        //given
        User user = new User("UserName", "UserSurname", "UserEmail", "UserPassword");
        //when
        when(userRepository.findUserById(user.getId())).thenReturn(Optional.of(user));
        underTest.adminDeleteUser(user.getId());
        //then
        ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).delete(userArgumentCaptor.capture());
        User capturedUser = userArgumentCaptor.getValue();
        assertEquals(capturedUser.getName(), "UserName");
    }
}