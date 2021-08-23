package repo.impl;

import repo.DepartmentRepo;
import models.Article;
import models.Department;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;

import java.util.ArrayList;
import java.util.List;

public class DepartmentRepoImpl implements DepartmentRepo {

    private final Sql2o sql2o;
    public DepartmentRepoImpl(Sql2o sql2o){ this.sql2o = sql2o; }

    @Override
    public void add(Department department) {
        String sql = "INSERT INTO departments (d_name, description, no_of_employees) VALUES (:d_name, :description, :no_of_employees)";
        try(Connection con = sql2o.open()){
            int id = (int) con.createQuery(sql, true)
                    .bind(department)
                    .executeUpdate()
                    .getKey();
            department.setId(id);
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }

    @Override
    public Department findById(int id) {
        try(Connection con = sql2o.open()){
            return con.createQuery("SELECT * FROM departments WHERE id = :id")
                    .addParameter("id", id)
                    .executeAndFetchFirst(Department.class);
        }catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public List<Department> getAll() {
        try(Connection con = sql2o.open()){
            return con.createQuery("SELECT * FROM departments")
                    .executeAndFetch(Department.class);
        }
    }

    @Override
    public void deleteById(int id) {
        String sql = "DELETE from departments WHERE id=:id";
        String joinSql = "DELETE from departments_articles WHERE department_id = :department_id";
        try (Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .addParameter("id", id)
                    .executeUpdate();
            con.createQuery(joinSql)
                    .addParameter("department_id", id)
                    .executeUpdate();
        } catch (Sql2oException ex){
            System.out.println(ex);
        }
    }

    @Override
    public void clearAll() {
        String sql = "DELETE from departments";
        try (Connection con = sql2o.open()) {
            con.createQuery(sql).executeUpdate();
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }
    @Override
    public void addDepartmentToArticle(Department department, Article Article){
        String sql = "INSERT INTO departments_articles (department_id, article_id) VALUES (:department_id, :article_id)";
        try (Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .addParameter("department_id", department.getId())
                    .addParameter("article_id", Article.getId())
                    .executeUpdate();
        } catch (Sql2oException ex){
            System.out.println(ex);
        }
    }
    @Override
    public List<Article> getDeparmentArticles(int department_id) {

        ArrayList<Article> articles = new ArrayList<>();

        String joinQuery = "SELECT darticle_id FROM departments_articles WHERE department_id = :department_id";

        try (Connection con = sql2o.open()) {
            List<Integer> allarticleIds = con.createQuery(joinQuery)
                    .addParameter("department_id", department_id)
                    .executeAndFetch(Integer.class);
            for (Integer articleId : allarticleIds){
                String articleQuery = "SELECT * FROM articles WHERE id = :article_id";
                articles.add(
                        con.createQuery(articleQuery)
                                .addParameter("article_id", articleId)
                                .executeAndFetchFirst(Article.class));
            } //why are we doing a second sql query - set?
        } catch (Sql2oException ex){
            System.out.println(ex);
        }
        return articles;
    }
}
