package com.example.backend.controller;

import com.example.backend.model.Participation;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.apache.tomcat.util.http.fileupload.ByteArrayOutputStream;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.awt.Font;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.Optional;

@RestController
public class PdfController {

    private final ParticipationController participationController;

    public PdfController(ParticipationController participationController) {
        this.participationController = participationController;
    }

    @RequestMapping(value = "/report-course-{courseId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<InputStreamResource> citiesReportc(Model model, @PathVariable Long courseId) throws IOException, DocumentException {

        Optional<Participation> optionalParticipation = participationController.certificate(model, courseId);

        if (optionalParticipation.isPresent()){
            String filename = MessageFormat.format("Recharge-{0}.pdf", "test");

            ByteArrayInputStream bis = write(optionalParticipation.get().getStudent().getName() + " " + optionalParticipation.get().getStudent().getSurname(),
                    optionalParticipation.get().getCourse().getName(),
                    optionalParticipation.get().getCourse().getAuthor().getName() + " " + optionalParticipation.get().getCourse().getAuthor().getSurname(),
                    optionalParticipation.get().getPoints(), optionalParticipation.get().getPosts().size(), optionalParticipation.get().getQuestions().size(),
                    optionalParticipation.get().totalViewsInPosts() + optionalParticipation.get().totalViewsInQuestions(),
                    optionalParticipation.get().totalLikesInPosts() + optionalParticipation.get().totalLikesInQuestions()
            );

            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Disposition", "inline; "+filename);

            return ResponseEntity.ok().headers(headers).contentType(MediaType.APPLICATION_PDF)
                    .body(new InputStreamResource(bis));
        }
        return ResponseEntity.of(null);
    }

    public static ByteArrayInputStream write(String student, String course, String teacher, int points, int posts, int questions, int visits, int likes) throws DocumentException, IOException {

        Document document = new Document(PageSize.A4, 10f, 10f, 50f, 30f);
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        PdfWriter.getInstance(document, out);
        document.open();
        document.setMargins(1,1,1,1);

        Paragraph foreverlearn = new Paragraph("Forever Learn \n\n",
                FontFactory.getFont("arial", 32, Font.BOLD, BaseColor.BLACK));
        foreverlearn.setAlignment(1);
        Paragraph studentP = new Paragraph("Se complace en otorgar al estudiante: \n\n",
                FontFactory.getFont("times-roman", 22, Font.BOLD, BaseColor.GRAY));
        studentP.setAlignment(1);
        Paragraph studentName = new Paragraph(student + "\n\n",
                FontFactory.getFont("times-roman", 22, Font.BOLD, BaseColor.DARK_GRAY));
        studentName.setAlignment(1);
        Paragraph courseP = new Paragraph("El diploma de superación del curso: \n\n",
                FontFactory.getFont("times-roman", 22, Font.BOLD, BaseColor.GRAY));
        courseP.setAlignment(1);
        Paragraph courseName = new Paragraph(course + "\n\n",
                FontFactory.getFont("times-roman", 22, Font.BOLD, BaseColor.DARK_GRAY));
        courseName.setAlignment(1);
        Paragraph teacherP = new Paragraph("Impartido por el profesor: \n\n",
                FontFactory.getFont("times-roman", 22, Font.BOLD, BaseColor.GRAY));
        teacherP.setAlignment(1);
        Paragraph teacherName = new Paragraph(teacher + "\n\n\n",
                FontFactory.getFont("times-roman", 22, Font.BOLD, BaseColor.DARK_GRAY));
        teacherName.setAlignment(1);

        document.add(foreverlearn);
        document.add(studentP);
        document.add(studentName);
        document.add(courseP);
        document.add(courseName);
        document.add(teacherP);
        document.add(teacherName);

        Paragraph participation = new Paragraph("Consiguiendo las siguientes métricas de participación: \n\n",
                FontFactory.getFont("times-roman", 20, Font.BOLD, BaseColor.LIGHT_GRAY));
        participation.setAlignment(1);

        document.add(participation);

        PdfPTable statistics = new PdfPTable(5);
        statistics.addCell("PUNTOS");
        statistics.addCell("POSTS");
        statistics.addCell("PREGUNTAS");
        statistics.addCell("VISITAS");
        statistics.addCell("ME GUSTAS");

        statistics.addCell(String.valueOf(points));
        statistics.addCell(String.valueOf(posts));
        statistics.addCell(String.valueOf(questions));
        statistics.addCell(String.valueOf(visits));
        statistics.addCell(String.valueOf(likes));

        document.add(statistics);

        document.close();
        return new ByteArrayInputStream(out.toByteArray());
    }

}