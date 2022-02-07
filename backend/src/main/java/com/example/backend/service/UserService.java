package com.example.backend.service;

import com.example.backend.model.Course;
import com.example.backend.model.User;
import com.example.backend.repository.CourseRepository;
import com.example.backend.repository.UserRepository;
import org.hibernate.engine.jdbc.BlobProxy;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.SQLException;
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

    @Transactional
    public User createFromParameters(String name, String surname, String email, String password, String imagePath, String description, String contact, String facebook, String twitter, String youtube) throws IOException {
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
            if (profilePhoto.getOriginalFilename() != "") {
                activeUser.setProfilePhoto(BlobProxy.generateProxy(profilePhoto.getInputStream(), profilePhoto.getSize()));
            }
            userRepository.save(activeUser);
        }
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
                courseOwner.get().deleteUserCourse(course);
                userRepository.findUsersByEnrolledCoursesContaining(course).forEach(user -> user.deleteEnrolledCourse(course));
                userRepository.findUsersByWishedCoursesContaining(course).forEach(user -> user.deleteWishedCourse(course));
                userRepository.findUsersByCompletedCoursesContaining(course).forEach(user -> user.deleteCompletedCourse(course));
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
        user.quitLikeCourse(course);
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
}
