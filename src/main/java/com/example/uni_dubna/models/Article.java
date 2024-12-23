package com.example.uni_dubna.models;
import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "articles")
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String annotation;

    @Column(nullable = false)
    private String status; // Статус, например "submitted", "approved", "rejected"

    private String rubric; // Рубрика для статьи

    @ManyToOne(cascade = CascadeType.ALL)  // Cascade на авторе, если он удаляется
    private ScientificUser author;

    @ElementCollection(fetch = FetchType.LAZY) // Ленивая загрузка для списка соавторов
    private List<String> coAuthors;

    @Lob
    private byte[] document;  // Хранение файла статьи

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAnnotation() {
        return annotation;
    }

    public void setAnnotation(String annotation) {
        this.annotation = annotation;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRubric() {
        return rubric;
    }

    public void setRubric(String rubric) {
        this.rubric = rubric;
    }

    public ScientificUser getAuthor() {
        return author;
    }

    public void setAuthor(ScientificUser author) {
        this.author = author;
    }

    public List<String> getCoAuthors() {
        return coAuthors;
    }

    public void setCoAuthors(List<String> coAuthors) {
        this.coAuthors = coAuthors;
    }

    public byte[] getDocument() {
        return document;
    }

    public void setDocument(byte[] document) {
        this.document = document;
    }
}