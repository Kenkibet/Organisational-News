package repo;

import models.Article;
import models.Department;

import java.util.List;

public interface DepartmentRepo {
    void add(Department department);
    Department findById(int id);
    List<Department> getAll();
    void addDepartmentToArticle(Department department, Article scopedarticle);
    List<Article> getDeparmentArticles(int departmentId);
    void deleteById(int id);
    void clearAll();
}
