package com.example.backend.controller;

import com.example.backend.model.*;
import com.example.backend.service.*;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.Optional;

@Component
public class DatabaseController {

    private final UserService userService;

    private final CategoryService categoryService;

    private final CourseService courseService;

    private final ThemeService themeService;

    private final LessonService lessonService;

    private final RequirementService requirementService;

    private final ObjectiveService objectiveService;


    public DatabaseController(UserService userService, CategoryService categoryService, CourseService courseService, ThemeService themeService, LessonService lessonService, RequirementService requirementService, ObjectiveService objectiveService) {
        this.userService = userService;
        this.categoryService = categoryService;
        this.courseService = courseService;
        this.themeService = themeService;
        this.lessonService = lessonService;
        this.requirementService = requirementService;
        this.objectiveService = objectiveService;
    }

    @PostConstruct
    public void initialize() throws IOException {
        User savedUser1 = userService.createFromParameters("Sergio", "Peinado", "sergio@email.com", "se", "/static/assets/images/people/110/sergioPeinado.jpg",
                "Hola soy Sergio Peinado, deportista aficionado y profesional del mundo de la salud, me apasiona mantenerme en movimiento y saber más sobre" +
                        " mi cuerpo para mejorar en mi profesión, en mis cursos hablaré sobre los deportes que domino y diversas áreas sobre las que me he formado" +
                        " como sanitario. Además debido a mi relación con el deporte y la salud he ejercicido como coach y motivador personal. Espero que os" +
                        " guste todo lo que tengo que enseñar. ","Podéis contactar conmigo a través de mi número de teléfono: 612345678, atenderé encantado " +
                        "cualquiera de vuestras consultas.","EntrenaConSergioPeinado","Sergio_Trainer","https://www.youtube.com/c/EntrenaSergioPeinado");
        userService.makeAdmin(savedUser1.getId());
        User savedUser2 = userService.createFromParameters("Marc", "Vidal", "marc@email.com", "ma", "/static/assets/images/people/110/marcVidal.jpg",
                "Me presento, soy Marc Vidal, me dedico a la economía y las finanzas, tema del que me gustar hablar y compartir con mis seguidores," +
                        " también suelo hablar de las profesiones más demandadas y los retos del futuro a los que nos enfrentamos como sociedad. Si quieres" +
                        " estar informado no te pierdas mis cursos. ","Podéis contactar conmigo a través de mi dirección de correo electrónico: " +
                        " marc@email.com.","vidal.marc","marcvidal","https://www.youtube.com/c/MarcVidalVlog");
        User savedUser3 = userService.createFromParameters("Sara", "Fructuoso", "sara@email.com", "sa", "/static/assets/images/people/110/saraFructuoso.jpg",
                "Soy una creadora de contenido todo en uno, en mis producciones trabajo desde las fases iniciales del diseño hasta el marketing " +
                        "para publicitarlas, pasando por fotografía, vídeo y producción, a veces incluso me atrevo a componer algún tema de la banda sonora.","Puedes contactarme " +
                        "a través de mis redes sociales, estoy abierta a nuevas colaboraciones y proyectos. Si te gusta lo que haga no dudes en contactar,",
                "","sarafructuoso__","https://www.youtube.com/channel/UCeZq-em4g_Jp-jMQdynKvBw");
        User savedUser4 = userService.createFromParameters("Amy", "Hennig", "amy@email.com", "am", "/static/assets/images/people/110/amyHennig.jpg",
                "Soy una profesional de la industria de los videojuegos. Con más de 20 años de experiencia, tengo créditos tanto en juegos para Atari 7800 como para " +
                        "títulos actuales como ‘Uncharted: Drake’s Fortune’. Además dedico mi tiempo a aprender sobre disciplinas como la física y las matemáticas." +
                        "En mis cursos trataré de hacer ver mi pasión por mis interesés","Actualmente mis datos de contacto son privados, disculpen las molestias.",
                "","amy_hennig","");

        //Categories initialization
        Category category1 = categoryService.create("Crecimiento personal", "/static/assets/images/categories/personal/dificil.png");
        Category category2 = categoryService.create("Deportes", "/static/assets/images/categories/sports/ejercicio.png");
        Category category3 = categoryService.create("Disciplinas académicas", "/static/assets/images/categories/academy/biblioteca.png");
        Category category4 = categoryService.create("Diseño", "/static/assets/images/categories/design/ocurrencia.png");
        Category category5 = categoryService.create("Finanzas", "/static/assets/images/categories/finances/prestamo.png");
        Category category6 = categoryService.create("Fotografía y vídeo", "/static/assets/images/categories/photography/camara-fotografica.png");
        Category category7 = categoryService.create("Informática y software", "/static/assets/images/categories/development/chip.png");
        Category category8 = categoryService.create("Marketing", "/static/assets/images/categories/marketing/anuncio-publicitario.png");
        Category category9 = categoryService.create("Música", "/static/assets/images/categories/music/gramofono.png");
        Category category10 = categoryService.create("Profesiones", "/static/assets/images/categories/professions/trabajador.png");
        Category category11 = categoryService.create("Salud", "/static/assets/images/categories/health/latido-del-corazon.png");
        Category category12 = categoryService.create("Otros", "/static/assets/images/categories/other/encogimiento-de-hombros.png");

        //Courses initialization
        Optional<Course> course1 = courseService.create("Inteligencia emocional", "Descripcion1", category1, "Intermedio", "/static/assets/images/courses/cursoInteligenciaEmocional.png", 3, savedUser1.getId());
        Optional<Course>  course2 = courseService.create("Bases del fútbol", "Descripcion2", category2, "Principiante", "/static/assets/images/courses/cursoFutbol.png", 5, savedUser1.getId());
        


        Optional<Course>  course3 = courseService.create("Curso de física para segundo de bachillerato", "Descripcion3", category3, "Avanzado", "/static/assets/images/courses/cursoFisica.jpg", 6, savedUser4.getId());
        Optional<Course>  course4 = courseService.create("Curso Procreate", "Descripcion4", category4, "Principiante", "/static/assets/images/courses/cursoProcreate.png", 6, savedUser3.getId());
        Optional<Course>  course5 = courseService.create("Contabilidad y costes", "Descripcion5", category5, "Principiante", "/static/assets/images/courses/cursoEconomia.png", 6, savedUser2.getId());
        Optional<Course>  course6 = courseService.create("Todo sobre el cine", "Descripcion6", category6, "Principiante", "/static/assets/images/courses/cursoCine.jpg", 6, savedUser3.getId());
        Optional<Course>  course7 = courseService.create("Introducción a la programación en Pascal", "Descripcion7", category7, "Principiante", "/static/assets/images/courses/cursoPascal.png", 6, savedUser4.getId());
        Optional<Course>  course8 = courseService.create("Técnicas de Marketing para expertos", "Descripcion8", category8, "Principiante", "/static/assets/images/courses/cursoMarketing.jpeg", 6, savedUser3.getId());
        Optional<Course>  course9 = courseService.create("Géneros musicales", "Descripcion9", category9, "Principiante", "/static/assets/images/courses/cursoGenerosMusicales.jpg", 6, savedUser3.getId());
        Optional<Course>  course10 = courseService.create("La profesión del profesor", "Descripcion10", category10, "Principiante", "/static/assets/images/courses/cursoProfesor.jpg", 6, savedUser2.getId());
        Optional<Course>  course11 = courseService.create("Principales afecciones del sistema endocrino", "Descripcion11", category11, "Principiante", "/static/assets/images/courses/cursoEndocrino.png", 6, savedUser1.getId());
        Optional<Course>  course12 = courseService.create("Introducción al mundo pokémon", "Descripcion12", category12, "Principiante", "/static/assets/images/courses/cursoPokemon.png", 6, savedUser4.getId());
        Optional<Course>  course13 = courseService.create("Bases del baloncesto", "Descripcion12", category2, "Principiante", "/static/assets/images/courses/cursoBaloncesto.jpg", 6, savedUser1.getId());

    }
}
