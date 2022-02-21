package com.example.backend.test.unit.repository;

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
class UserRepositoryTest {

    @Autowired
    private UserRepository underTest;

    @Autowired
    private CourseRepository courseRepository;

    @BeforeEach
    void setUp(){
    }

    @AfterEach
    void tearDown(){
        courseRepository.deleteAll();
        underTest.deleteAll();
    }

    @Test
    void findUserByEmailAndPassword() {
        //given
        String email = "Email";
        String password = "Password";
        User user = new User("Name", "Surname", email, password);
        underTest.save(user);
        //when
        Optional<User> userByEmail = underTest.findUserByEmail(email);
        //then
        assertTrue(userByEmail.isPresent());
        assertEquals(userByEmail.get().getName(), "Name");
    }

    @Test
    void findUserByEmail() {
        //given
        String email = "Email";
        User user = new User("Name", "Surname", email, "Password");
        underTest.save(user);
        //when
        Optional<User> userByEmail = underTest.findUserByEmail(email);
        //then
        assertTrue(userByEmail.isPresent());
        assertEquals(userByEmail.get().getName(), "Name");
    }

    @Test
    void findUserById() {
        //given
        User user = new User("Name", "Surname", "Email", "Password");
        underTest.save(user);
        //when
        Long id = 1L;
        Optional<User> userById = underTest.findUserById(id);
        //then
        assertTrue(userById.isPresent());
        assertEquals(userById.get().getName(), "Name");
    }

    @Test
    void findUsersByName() {
        //given
        String name1 = "Name1";
        User user1 = new User(name1, "Surname1", "Email1", "Password1");
        underTest.save(user1);
        User user2 = new User("Name2", "Surname2", "Email2", "Password2");
        underTest.save(user2);
        User user3 = new User(name1, "Surname3", "Email3", "Password3");
        underTest.save(user3);
        //when
        Optional<List<User>> usersByName = underTest.findUsersByName(name1);
        //then
        assertTrue(usersByName.isPresent());
        assertEquals(usersByName.get().size(), 3);
    }

    @Test
    void findUserByUserCoursesContains() {
    }

    @Test
    void findUsersByEnrolledCoursesContaining() {
    }

    @Test
    void findUsersByCompletedCoursesContaining() {
    }

    @Test
    void findUsersByWishedCoursesContaining() {
    }

    @Test
    void findAll() {
        //given
        User user1 = new User("Name1", "Surname1", "Email1", "Password1");
        underTest.save(user1);
        User user2 = new User("Name2", "Surname2", "Email2", "Password2");
        underTest.save(user2);
        User user3 = new User("Name3", "Surname3", "Email3", "Password3");
        underTest.save(user3);
        //when
        Page<User> all = underTest.findAll(PageRequest.of(0, 10));
        //then
        assertTrue(all.hasContent());
        assertEquals(all.getNumberOfElements(), 3);
    }
}