package com.example.backend.service;

import com.example.backend.model.Course;
import com.example.backend.model.User;
import com.example.backend.repository.UserRepository;
import org.hibernate.engine.jdbc.BlobProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;


@Service
public class UserService {

    private User activeUser;

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getActiveUser() {
        return activeUser;
    }

    public void setActiveUser(User activeUser) {
        this.activeUser = activeUser;
    }

    public User create(User user) throws IOException {
        Resource image = new ClassPathResource("/static/assets/images/default-profile.jpg");
        //user.setProfilePhoto(BlobProxy.generateProxy(image.getInputStream(), image.contentLength()));
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
        if (user.getName() != null) activeUser.setName(user.getName());
        if (user.getSurname() != null) activeUser.setSurname(user.getSurname());
        if (user.getEmail() != null) activeUser.setEmail(user.getEmail());
        userRepository.save(activeUser);
    }

    public void updateProfileInformation(User user){
        if (user.getDescription() != null) activeUser.setDescription(user.getDescription());
        if (user.getContact() != null) activeUser.setContact(user.getContact());
        if (user.getFacebook() != null) activeUser.setFacebook(user.getFacebook());
        if (user.getTwitter() != null) activeUser.setTwitter(user.getTwitter());
        if (user.getYoutube() != null) activeUser.setYoutube(user.getYoutube());
        userRepository.save(activeUser);
    }

    public void updatePassword(String password1, String password2){
        if((password1 != null) && (password1.contentEquals(password2))){
            activeUser.setPassword(password1);
        }
        userRepository.save(activeUser);
    }

    public List<Course> getUserCourses(){
        return activeUser.getUserCourses();
    }

    public Optional<User> findUserById(Long id){
        return userRepository.findUserById(id);
    }

    public User addUserCourse(User user, Course course){
        user.addUserCourse(course);
        return userRepository.save(user);
    }
}
