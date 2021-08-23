package models;

import java.util.Objects;

public class Article {

    public int id;
    public String title;
    public String content;
    public String type;
    public static final String TYPE = "Not Scoped";

    public Article(String title, String content) {
        this.title = title;
        this.content = content;
        this.type = TYPE;
    }

    public Article(String title, String content, String scoped) {
        this.title = title;
        this.content = content;
        this.type = scoped;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Article article = (Article) o;
        return getTitle().equals(article.getTitle()) &&
                getContent().equals(article.getContent()) &&
                getType().equals(article.getType());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTitle(), getContent(), getType());
    }
}
