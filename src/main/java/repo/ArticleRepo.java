package repo;

import models.Article;
import models.Department;

import java.util.List;
import java.util.Map;

public interface ArticleRepo {
    void add(Article article);
    List<Article> getAll();
    Article findById(int id);
    void deleteById(int id);
    void clearAll();
    void addArticleToDepartment(Article article, Department dept);
    Map<Object, Object> getDepartmentArticles(int id);
}
