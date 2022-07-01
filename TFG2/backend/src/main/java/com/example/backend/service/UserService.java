package com.example.backend.service;

import com.example.backend.model.ActiveUser;
import com.example.backend.model.Course;
import com.example.backend.model.User;
import com.example.backend.repository.CourseRepository;
import com.example.backend.repository.UserRepository;
import org.hibernate.engine.jdbc.BlobProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private ActiveUser activeUser;

    private final UserRepository userRepository;

    private final CourseRepository courseRepository;

    public UserService(UserRepository userRepository, CourseRepository courseRepository) {
        this.userRepository = userRepository;
        this.courseRepository = courseRepository;
    }

    public Optional<User> getActiveUser() {
        if (Objects.isNull(activeUser.getUser())){
            return Optional.empty();
        }
        return userRepository.findUserById(activeUser.getUser().getId());
    }

    public void setActiveUser(User connectedUser) {
        Optional<User> optionalUser = userRepository.findUserById(connectedUser.getId());
        optionalUser.ifPresent(user -> this.activeUser.setUser(user));
    }

    public void logout() {
        this.activeUser.setUser(null);
    }

    @Transactional
    public User create(User user) throws IOException {
        if (userRepository.findUserByEmail(user.getEmail()).isEmpty()){
            Resource image = new ClassPathResource("/static/assets/images/default-profile.jpg");
            user.setProfilePhoto(BlobProxy.generateProxy(image.getInputStream(), image.contentLength()));
            user.setDescription("");
            user.setContact("");
            user.setFacebook("");
            user.setTwitter("");
            user.setYoutube("");
            return userRepository.save(user);
        }
        return null;
    }

    @Transactional
    public User createFromParameters(String name, String surname, String email, String password, String imagePath, String description, String contact, String facebook, String twitter, String youtube) throws IOException {
        if (userRepository.findUserByEmail(email).isEmpty()){
            User user = new User(name, surname, email, password);
            Resource image = new ClassPathResource(imagePath);
            user.setProfilePhoto(BlobProxy.generateProxy(image.getInputStream(), image.contentLength()));
            user.setDescription(description);
            user.setContact(contact);
            user.setFacebook(facebook);
            user.setTwitter(twitter);
            user.setYoutube(youtube);
            return userRepository.save(user);
        }
        return null;
    }

    public Optional<User> login(User user){
        return userRepository.findUserByEmailAndPassword(user.getEmail(), user.getPassword());
    }

    public void updateBasicInformation(User user){
        Optional<User> optionalUser = getActiveUser();
        if (optionalUser.isPresent()){
            if (user.getName() != null) optionalUser.get().setName(user.getName());
            if (user.getSurname() != null) optionalUser.get().setSurname(user.getSurname());
            if (user.getEmail() != null) optionalUser.get().setEmail(user.getEmail());
            userRepository.save(optionalUser.get());
        }

    }

    @Transactional
    public void updateProfileInformation(User user, MultipartFile profilePhoto) throws SQLException, IOException {
        Optional<User> optionalUser = getActiveUser();
        if (optionalUser.isPresent()){
            User activeUser = optionalUser.get();
            if (!user.getDescription().contentEquals("")) activeUser.setDescription(user.getDescription());
            if (!user.getContact().contentEquals("")) activeUser.setContact(user.getContact());
            if (!user.getFacebook().contentEquals("")) activeUser.setFacebook(user.getFacebook());
            if (!user.getTwitter().contentEquals("")) activeUser.setTwitter(user.getTwitter());
            if (!user.getYoutube().contentEquals("")) activeUser.setYoutube(user.getYoutube());
            if (Objects.nonNull(profilePhoto) && !Objects.requireNonNull(profilePhoto.getOriginalFilename()).contentEquals("")) {
                activeUser.setProfilePhoto(BlobProxy.generateProxy(profilePhoto.getInputStream(), profilePhoto.getSize()));
            }
            userRepository.save(activeUser);
        }
    }

    public void updatePassword(String password1, String password2){
        Optional<User> optionalUser = getActiveUser();
        if((password1 != null) && (password1.contentEquals(password2)) && optionalUser.isPresent()){
            optionalUser.get().setPassword(password1);
            userRepository.save(optionalUser.get());
        }
    }

    @Transactional
    public List<Course> getUserCourses(){
        Optional<User> optionalUser = getActiveUser();
        return optionalUser.map(User::getUserCourses).orElse(null);
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
                courseOwner.get().deleteUserCourse(course);
                userRepository.findUsersByEnrolledCoursesContaining(course).forEach(user -> user.deleteEnrolledCourse(course));
                userRepository.findUsersByWishedCoursesContaining(course).forEach(user -> user.deleteWishedCourse(course));
                userRepository.findUsersByCompletedCoursesContaining(course).forEach(user -> user.deleteCompletedCourse(course));
                courseRepository.delete(course);
            }
        }
    }

    @Transactional
    public void enrollCourse(Long courseId){
        Optional<Course> optionalCourse = courseRepository.findById(courseId);
        if(optionalCourse.isPresent()){
            Optional<User> optionalUser = getActiveUser();
            if (optionalUser.isPresent()){
                optionalUser.get().addEnrolledCourse(optionalCourse.get());
                userRepository.save(optionalUser.get());
            }
        }
    }

    public Boolean isCourseOwner(User user, Course course) {
        return (user.equals(course.getAuthor()));
    }

    public Boolean isCourseEnrolled(User user, Course course) {
        return user.isCourseEnrolled(course);
    }

    @Transactional
    public void unenrollCourse(Long courseId) {
        Optional<Course> optionalCourse = courseRepository.findById(courseId);
        if(optionalCourse.isPresent()){
            Optional<User> optionalUser = getActiveUser();
            if (optionalUser.isPresent()){
                optionalUser.get().deleteEnrolledCourse(optionalCourse.get());
                userRepository.save(optionalUser.get());
            }
        }
    }

    public Optional<User> makeAdmin(Long userId){
        Optional<User> toReturn = Optional.empty();
        Optional<User> optionalUser = userRepository.findUserById(userId);
        if (optionalUser.isPresent()){
            optionalUser.get().setAdmin(true);
            toReturn = Optional.of(userRepository.save(optionalUser.get()));
        }
        return toReturn;
    }

    public Page<User> findPageUsers(Integer numberPage, Integer content){
        return userRepository.findAll(PageRequest.of(numberPage, content));
    }

    public Optional<List<User>> findUserByName(String name) {
        return userRepository.findUsersByName(name);
    }

    public void wishCourse(Long courseId) {
        Optional<Course> optionalCourse = courseRepository.findById(courseId);
        if(optionalCourse.isPresent()){
            Optional<User> optionalUser = getActiveUser();
            if (optionalUser.isPresent()){
                optionalUser.get().addWishedCourse(optionalCourse.get());
                userRepository.save(optionalUser.get());
            }
        }
    }

    public void unwishCourse(Long courseId) {
        Optional<Course> optionalCourse = courseRepository.findById(courseId);
        if(optionalCourse.isPresent()){
            Optional<User> optionalUser = getActiveUser();
            if (optionalUser.isPresent()){
                optionalUser.get().deleteWishedCourse(optionalCourse.get());
                userRepository.save(optionalUser.get());
            }
        }
    }

    public Boolean isCourseWished(User user, Course course) {
        return user.isCourseWished(course);
    }

    public void likeCourse(User user, Course course){
        user.likeCourse(course);
        userRepository.save(user);
    }

    public void dislikeCourse(User user, Course course){
        user.dislikeCourse(course);
        userRepository.save(user);
    }

    public void quitLikeCourse(User user, Course course){
        user.quitLikeCourse(course);
        userRepository.save(user);
    }

    public void quitDislikeCourse(User user, Course course){
        user.quitDislikeCourse(course);
        userRepository.save(user);
    }

    public Boolean isCourseLiked(User user, Course course){
        return user.isCourseLiked(course);
    }

    public Boolean isCourseDisliked(User user, Course course){
        return user.isCourseDisliked(course);
    }

    public Optional<User>findCourseOwner(Course course){
        return userRepository.findUserByUserCoursesContains(course);
    }

    public List<User> findUsersByEnrolledCourse(Course course){
        return userRepository.findUsersByEnrolledCoursesContaining(course);
    }

    public List<User> findUsersByCompletedCourse(Course course){
        return userRepository.findUsersByCompletedCoursesContaining(course);
    }

    public List<User> findUsersByWishedCourse(Course course){
        return userRepository.findUsersByWishedCoursesContaining(course);
    }

    public void banUser(Long id) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()){
            optionalUser.get().ban();
            userRepository.save(optionalUser.get());
        }
    }

    public void unbanUser(Long id) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()){
            optionalUser.get().unban();
            userRepository.save(optionalUser.get());
        }

    }

    public void adminDeleteUser(Long id) {
        Optional<User> optionalUser = userRepository.findUserById(id);
        if (optionalUser.isPresent()){
            List<Course> userCourses = optionalUser.get().getUserCourses();
            if (Objects.nonNull(userCourses)){
                List<Course> aux = new ArrayList<>();
                userCourses.forEach(course -> {
                    course.setAuthor(null);
                    courseRepository.save(course);
                    aux.add(course);
                });
                optionalUser.get().getUserCourses().clear();
                courseRepository.deleteAll(aux);
            }
            userRepository.delete(optionalUser.get());
        }
    }

    public void deleteAccount() {
        if (getActiveUser().isPresent()){
            adminDeleteUser(getActiveUser().get().getId());
        }
    }

    public boolean isCourseCompleted(User user, Course course) {
        return user.getCompletedCourses().contains(course);
    }
}
