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
        User user1 = new User("NombreA", "ApellidosA", "CorreoA", "ContraseñaA");
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



    }
}
