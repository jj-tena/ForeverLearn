package com.example.backend.service;

import com.example.backend.model.Course;
import com.example.backend.model.User;
import com.example.backend.repository.CourseRepository;
import com.example.backend.repository.UserRepository;
import org.hibernate.engine.jdbc.BlobProxy;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;
import java.util.Optional;


@Service
public class UserService {

    private Long activeId;

    private final UserRepository userRepository;

    private final CourseRepository courseRepository;

    public UserService(UserRepository userRepository, CourseRepository courseRepository) {
        this.userRepository = userRepository;
        this.courseRepository = courseRepository;
    }

    public Optional<User> getActiveUser() {
        return userRepository.findUserById(activeId);
    }

    public void setActiveUser(User activeUser) {
        this.activeId = activeUser.getId();
    }

    @Transactional
    public User create(User user) throws IOException {
        Resource image = new ClassPathResource("/static/assets/images/default-profile.jpg");
        user.setProfilePhoto(BlobProxy.generateProxy(image.getInputStream(), image.contentLength()));
        user.setDescription("");
        user.setContact("");
        user.setFacebook("");
        user.setTwitter("");
        user.setYoutube("");
        return userRepository.save(user);
    }

    public Optional<User> login(User user){
        return userRepository.findUserByEmailAndPassword(user.getEmail(), user.getPassword());
    }

    public void resetPassword(User user){
        Optional<User> userFromDB = userRepository.findUserByEmail(user.getEmail());
        if (userFromDB.isPresent()){

        }
    }

    public void updateBasicInformation(User user){
        if (user.getName() != null) getActiveUser().get().setName(user.getName());
        if (user.getSurname() != null) getActiveUser().get().setSurname(user.getSurname());
        if (user.getEmail() != null) getActiveUser().get().setEmail(user.getEmail());
        userRepository.save(getActiveUser().get());
    }

    public void updateProfileInformation(User user){
        if (user.getDescription() != null) getActiveUser().get().setDescription(user.getDescription());
        if (user.getContact() != null) getActiveUser().get().setContact(user.getContact());
        if (user.getFacebook() != null) getActiveUser().get().setFacebook(user.getFacebook());
        if (user.getTwitter() != null) getActiveUser().get().setTwitter(user.getTwitter());
        if (user.getYoutube() != null) getActiveUser().get().setYoutube(user.getYoutube());
        userRepository.save(getActiveUser().get());
    }

    public void updatePassword(String password1, String password2){
        if((password1 != null) && (password1.contentEquals(password2))){
            getActiveUser().get().setPassword(password1);
        }
        userRepository.save(getActiveUser().get());
    }

    @Transactional
    public List<Course> getUserCourses(){
        return getActiveUser().get().getUserCourses();
    }

    @Transactional
    public Optional<User> findUserById(Long id){
        return userRepository.findUserById(id);
    }

    @Transactional
    public User addUserCourse(User user, Course course){
        user.addUserCourse(course);
        return userRepository.save(user);
    }

    @Transactional
    public void deleteCourse(User activeUser, Course course) {
        Optional<User> courseOwner = userRepository.findUserByUserCoursesContains(course);
        if (courseOwner.isPresent()){
            if (courseOwner.get().equals(activeUser)){
                courseRepository.delete(course);
            }
        }
    }

    public void logout() {
        this.activeId = null;
    }

    @Transactional
    public void enrollCourse(Long courseId){
        Optional<Course> optionalCourse = courseRepository.findById(courseId);
        if(optionalCourse.isPresent()){
            Optional<User> optionalUser = getActiveUser();
            if (optionalUser.isPresent()){
                optionalUser.get().enrollCourse(optionalCourse.get());
                userRepository.save(optionalUser.get());
            }
        }
    }
}
