package repo.impl;

import models.Department;
import repo.ArticleRepo;
import models.Article;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;

import java.util.List;
import java.util.Map;

public class ArticleRepoImpl implements ArticleRepo {

    private final Sql2o sql2o;
    public ArticleRepoImpl(Sql2o sql2o){ this.sql2o = sql2o; }

    @Override
    public void add(Article article) {
        String sql = "INSERT INTO articles (title, content) VALUES (:title, :content)";
        try(Connection con = sql2o.open()){
            int id = (int) con.createQuery(sql, true)
                    .bind(article)
                    .executeUpdate()
                    .getKey();
            article.setId(id);
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }

    @Override
    public Article findById(int id) {
        try(Connection con = sql2o.open()){
            return con.createQuery("SELECT * FROM articles WHERE id = :id")
                    .addParameter("id", id)
                    .executeAndFetchFirst(Article.class);
        }
    }


    @Override
    public List<Article> getAll() {
        try(Connection con = sql2o.open()){
            return con.createQuery("SELECT * FROM articles")
                    .executeAndFetch(Article.class);
        }
    }

    @Override
    public void deleteById(int id) {
        String sql = "DELETE from articles WHERE id=:id";
        try (Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .addParameter("id", id)
                    .executeUpdate();
        } catch (Sql2oException ex){
            System.out.println(ex);
        }
    }

    @Override
    public void clearAll() {
        String sql = "DELETE from articles";
        try (Connection con = sql2o.open()) {
            con.createQuery(sql).executeUpdate();
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }

    @Override
    public void addArticleToDepartment(Article article, Department department) {

    }

    @Override
    public Map<Object, Object> getDepartmentArticles(int scopedarticleId) {
        return null;
    }
}
