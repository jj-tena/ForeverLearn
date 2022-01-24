package com.example.backend.controller;

import com.example.backend.model.Category;
import com.example.backend.model.User;
import com.example.backend.service.CategoryService;
import com.example.backend.service.CourseService;
import com.example.backend.service.UserService;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;

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
        User user1 = new User("NombreA", "ApellidosA", "CorreoA@email.com", "ContraseñaA");
        userService.create(user1);

        //Categories initialization
        categoryService.create("Crecimiento personal", "/static/assets/images/categories/personal/dificil.png");
        categoryService.create("Deporte", "/static/assets/images/categories/sports/ejercicio.png");
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
        courseService.create("Curso1", "Descripcion1", categoryService.findByName("Crecimiento personal").get(), "Principiante", "/static/assets/images/paths/angular_430x168.png", 6);
        courseService.create("Curso2", "Descripcion2", categoryService.findByName("Deporte").get(), "Principiante", "/static/assets/images/paths/swift_430x168.png", 6);
        courseService.create("Curso3", "Descripcion3", categoryService.findByName("Disciplinas académicas").get(), "Principiante", "/static/assets/images/paths/photoshop_430x168.png", 6);
        courseService.create("Curso4", "Descripcion4", categoryService.findByName("Diseño").get(), "Principiante", "/static/assets/images/paths/craft_430x168.png", 6);
        courseService.create("Curso5", "Descripcion5", categoryService.findByName("Finanzas").get(), "Principiante", "/static/assets/images/paths/wordpress_430x168.png", 6);
        courseService.create("Curso6", "Descripcion6", categoryService.findByName("Fotografía y vídeo").get(), "Principiante", "/static/assets/images/paths/react_430x168.png", 6);
        courseService.create("Curso7", "Descripcion7", categoryService.findByName("Informática y software").get(), "Principiante", "/static/assets/images/paths/redis_430x168.png", 6);
        courseService.create("Curso8", "Descripcion8", categoryService.findByName("Marketing").get(), "Principiante", "/static/assets/images/paths/angular_testing_430x168.png", 6);
        courseService.create("Curso9", "Descripcion9", categoryService.findByName("Música").get(), "Principiante", "/static/assets/images/paths/typescript_430x168.png", 6);
        courseService.create("Curso10", "Descripcion10", categoryService.findByName("Profesiones").get(), "Principiante", "/static/assets/images/paths/angular_routing_430x168.png", 6);
        courseService.create("Curso11", "Descripcion11", categoryService.findByName("Salud").get(), "Principiante", "/static/assets/images/paths/invision_430x168.png", 6);
        courseService.create("Curso12", "Descripcion12", categoryService.findByName("Otros").get(), "Principiante", "/static/assets/images/paths/devops_430x168.png", 6);

    }
}
