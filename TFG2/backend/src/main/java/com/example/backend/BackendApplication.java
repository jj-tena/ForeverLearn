package com.example.backend;

import com.example.backend.controller.PdfController;
import com.itextpdf.text.DocumentException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

@SpringBootApplication
public class BackendApplication {

    public static void main(String[] args) throws DocumentException, IOException {
        SpringApplication.run(BackendApplication.class, args);
    }

}
