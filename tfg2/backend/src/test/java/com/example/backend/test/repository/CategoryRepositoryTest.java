package com.example.backend.test.repository;

import com.example.backend.model.Category;
import com.example.backend.repository.CategoryRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.as;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class CategoryRepositoryTest {

    @Autowired
    private CategoryRepository underTest;

    @BeforeEach
    void setUp(){
    }

    @AfterEach
    void tearDown(){
        underTest.deleteAll();
    }

    @Test
    void itShouldFindOneCategoryByName() {
        //given
        Category category = new Category("Prueba");
        underTest.save(category);
        //when
        Optional<Category> prueba = underTest.findByName("Prueba");
        //then
        assertThat(prueba).isPresent();
        assertEquals("Prueba", prueba.get().getName());
    }

    @Test
    void itShouldFindEveryCategory() {
        //given
        Category category1 = new Category("Prueba1");
        underTest.save(category1);
        Category category2 = new Category("Prueba2");
        underTest.save(category2);
        Category category3 = new Category("Prueba3");
        underTest.save(category3);
        //when
        Page<Category> categoryPage = underTest.findAll(PageRequest.of(0, 10));
        //then
        assertTrue(categoryPage.hasContent());
        assertEquals(categoryPage.getNumberOfElements(), 3);
    }

    @Test
    void itShouldFindEveryCategoryByName() {
        //given
        Category category1 = new Category("Prueba");
        underTest.save(category1);
        Category category2 = new Category("Auxiliar");
        underTest.save(category2);
        Category category3 = new Category("Prueba");
        underTest.save(category3);
        //when
        Optional<List<Category>> categoryList = underTest.findAllByName("Prueba");
        //then
        assertTrue(categoryList.isPresent());
        assertEquals(categoryList.get().size(), 2);
    }

    @Test
    void itShouldFindOneCategoryById() {
        //given
        Category category1 = new Category("Prueba1");
        underTest.save(category1);
        Category category2 = new Category("Prueba2");
        underTest.save(category2);
        Category category3 = new Category("Prueba3");
        underTest.save(category3);
        //when
        Long id = 3L;
        Optional<Category> optionalCategory = underTest.findById(id);
        //then
        assertTrue(optionalCategory.isPresent());
        System.out.println(optionalCategory.get().getName());
        assertEquals(optionalCategory.get().getName(), "Prueba2");
    }
}