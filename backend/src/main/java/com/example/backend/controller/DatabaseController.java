package com.example.backend.controller;

import com.example.backend.model.Category;
import com.example.backend.model.Course;
import com.example.backend.model.User;
import com.example.backend.service.CategoryService;
import com.example.backend.service.CourseService;
import com.example.backend.service.UserService;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.Optional;

@Component
public class DatabaseController {

    private final UserService userService;

    private final CategoryService categoryService;

    private final CourseService courseService;


    public DatabaseController(UserService userService, CategoryService categoryService, CourseService courseService) {
        this.userService = userService;
        this.categoryService = categoryService;
        this.courseService = courseService;
    }

    @PostConstruct
    public void initialize() throws IOException {
        User user1 = new User("Arturo", "Antúnez", "arturo@email.com", "aa");
        User savedUser1 = userService.create(user1);
        User user2 = new User("Beatriz", "Bueno", "beatriz@email.com", "bb");
        User savedUser2 = userService.create(user2);

        //Categories initialization
        categoryService.create("Crecimiento personal", "/static/assets/images/categories/personal/dificil.png");
        categoryService.create("Deportes", "/static/assets/images/categories/sports/ejercicio.png");
        categoryService.create("Disciplinas académicas", "/static/assets/images/categories/academy/biblioteca.png");
        categoryService.create("Diseño", "/static/assets/images/categories/design/ocurrencia.png");
        categoryService.create("Finanzas", "/static/assets/images/categories/finances/prestamo.png");
        categoryService.create("Fotografía y vídeo", "/static/assets/images/categories/photography/camara-fotografica.png");
        categoryService.create("Informática y software", "/static/assets/images/categories/development/chip.png");
        categoryService.create("Marketing", "/static/assets/images/categories/marketing/anuncio-publicitario.png");
        categoryService.create("Música", "/static/assets/images/categories/music/gramofono.png");
        categoryService.create("Profesiones", "/static/assets/images/categories/professions/trabajador.png");
        categoryService.create("Salud", "/static/assets/images/categories/health/latido-del-corazon.png");
        categoryService.create("Otros", "/static/assets/images/categories/other/encogimiento-de-hombros.png");

        //Courses initialization
        Optional<Course> course1 = courseService.create("Curso1", "Descripcion1", categoryService.findByName("Crecimiento personal").get(), "Principiante", "/static/assets/images/paths/angular_430x168.png", 6, savedUser1.getId());
        //userService.addUserCourse(user1, course1);
        Optional<Course>  course2 = courseService.create("Curso2", "Descripcion2", categoryService.findByName("Deportes").get(), "Principiante", "/static/assets/images/paths/swift_430x168.png", 6, savedUser1.getId());
        //userService.addUserCourse(user1, course2);
        Optional<Course>  course3 = courseService.create("Curso3", "Descripcion3", categoryService.findByName("Disciplinas académicas").get(), "Principiante", "/static/assets/images/paths/photoshop_430x168.png", 6, savedUser1.getId());
        //userService.addUserCourse(user1, course3);
        Optional<Course>  course4 = courseService.create("Curso4", "Descripcion4", categoryService.findByName("Diseño").get(), "Principiante", "/static/assets/images/paths/craft_430x168.png", 6, savedUser1.getId());
        //userService.addUserCourse(user1, course4);
        Optional<Course>  course5 = courseService.create("Curso5", "Descripcion5", categoryService.findByName("Finanzas").get(), "Principiante", "/static/assets/images/paths/wordpress_430x168.png", 6, savedUser1.getId());
        //userService.addUserCourse(user1, course5);
        Optional<Course>  course6 = courseService.create("Curso6", "Descripcion6", categoryService.findByName("Fotografía y vídeo").get(), "Principiante", "/static/assets/images/paths/react_430x168.png", 6, savedUser1.getId());
        //userService.addUserCourse(user1, course6);
        Optional<Course>  course7 = courseService.create("Curso7", "Descripcion7", categoryService.findByName("Informática y software").get(), "Principiante", "/static/assets/images/paths/redis_430x168.png", 6, savedUser2.getId());
        //userService.addUserCourse(user1, course7);
        Optional<Course>  course8 = courseService.create("Curso8", "Descripcion8", categoryService.findByName("Marketing").get(), "Principiante", "/static/assets/images/paths/angular_testing_430x168.png", 6, savedUser2.getId());
        //userService.addUserCourse(user1, course8);
        Optional<Course>  course9 = courseService.create("Curso9", "Descripcion9", categoryService.findByName("Música").get(), "Principiante", "/static/assets/images/paths/typescript_430x168.png", 6, savedUser2.getId());
        //userService.addUserCourse(user1, course9);
        Optional<Course>  course10 = courseService.create("Curso10", "Descripcion10", categoryService.findByName("Profesiones").get(), "Principiante", "/static/assets/images/paths/angular_routing_430x168.png", 6, savedUser2.getId());
        //userService.addUserCourse(user1, course10);
        Optional<Course>  course11 = courseService.create("Curso11", "Descripcion11", categoryService.findByName("Salud").get(), "Principiante", "/static/assets/images/paths/invision_430x168.png", 6, savedUser2.getId());
        //userService.addUserCourse(user1, course11);
        Optional<Course>  course12 = courseService.create("Curso12", "Descripcion12", categoryService.findByName("Otros").get(), "Principiante", "/static/assets/images/paths/devops_430x168.png", 6, savedUser2.getId());
        //userService.addUserCourse(user1, course12);
    }
}
