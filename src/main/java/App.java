import com.google.gson.Gson;
import exceptions.ApiException;
import models.Article;
import models.Department;
import models.User;
import repo.impl.ArticleRepoImpl;
import repo.impl.DepartmentRepoImpl;
import repo.impl.UserRepoImpl;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import static spark.Spark.*;

public class App {

    public static void main(String[] args) {
        String connectionString = "jdbc:h2:~/organisationalapi.db;INIT=RUNSCRIPT from 'classpath:db/create.sql'";
        Sql2o sql2o = new Sql2o(connectionString, "", "");

        DepartmentRepoImpl departmentDao = new DepartmentRepoImpl(sql2o);
        ArticleRepoImpl articleDao = new ArticleRepoImpl(sql2o);
        UserRepoImpl userDao = new UserRepoImpl(sql2o);
        Connection conn = sql2o.open();

        Gson gson = new Gson();


        post("/departments/new", "application/json", (req, res) -> {
            Department department = gson.fromJson(req.body(), Department.class);
            departmentDao.add(department);
            res.status(201);;
            return gson.toJson(department);
        });

        //READ
        get("/departments", "application/json", (req, res) -> {
            return gson.toJson(departmentDao.getAll());
        });

        get("/departments/:id", "application/json", (req, res) -> {
            return gson.toJson(departmentDao.findById(Integer.parseInt(req.params("id"))));
        });

        post("/departments/:departmentId/scopedarticles/:scopedarticleId", "application/json", (req, res) -> {
            int departmentId = Integer.parseInt(req.params("departmentId"));
            int scopedarticleId = Integer.parseInt(req.params("scopedarticleId"));
            Department department = departmentDao.findById(departmentId);
            Article Article = articleDao.findById(scopedarticleId);

            if (department != null && Article != null){
                articleDao.addArticleToDepartment(Article, department);
                res.status(201);
                return gson.toJson(String.format("Department '%s' and Article '%s' have been associated",Article.getTitle(), department.getName()));
            }
            else {
                throw new ApiException(404, String.format("Department or Article does not exist"));
            }
        });

        get("/departments/:id/scopedarticles", "application/json", (req, res) -> {
            int departmentId = Integer.parseInt(req.params("id"));
            Department departmentToFind = departmentDao.findById(departmentId);
            if (departmentToFind == null){
                throw new ApiException(404, String.format("No department with the id: \"%s\" exists", req.params("id")));
            }
            else if (departmentDao.getDeparmentArticles(departmentId).size()==0){
                return "{\"message\":\"I'm sorry, but no scopedarticles are listed for this department.\"}";
            }
            else {
                return gson.toJson(departmentDao.getDeparmentArticles(departmentId));
            }
        });

        get("/scopedarticles/:id/departments", "application/json", (req, res) -> {
            int scopedarticleId = Integer.parseInt(req.params("id"));
            Article scopedarticleToFind = articleDao.findById(scopedarticleId);
            if (scopedarticleToFind == null){
                throw new ApiException(404, String.format("No Article with the id: \"%s\" exists", req.params("id")));
            }
            else if (articleDao.getDepartmentArticles(scopedarticleId).size()==0){
                return "{\"message\":\"I'm sorry, but no departments are listed for this Article.\"}";
            }
            else {
                return gson.toJson(articleDao.getDepartmentArticles(scopedarticleId));
            }
        });

        post("/users/new", "application/json", (req, res) -> {
            User user  = gson.fromJson(req.body(), User.class);
            userDao.add(user);
            res.status(201);
            return gson.toJson(user);
        });

        //READ
        get("/articles", "application/json", (req, res) -> {
            return gson.toJson(articleDao.getAll());
        });


        post("/articles/new", "application/json", (req, res) -> {
            Article article  = gson.fromJson(req.body(), Article.class);
            articleDao.add(article);
            res.status(201);;
            return gson.toJson(article);
        });

        get("/articles", "application/json", (req, res) -> {

            return gson.toJson(articleDao.getAll());
        });

        get("/articles/:id", "application/json", (req, res) -> {
            Article scopedarticleToFind = articleDao.findById(Integer.parseInt(req.params("id")));
            if (scopedarticleToFind == null){
                throw new ApiException(404, String.format("No Article with the id: \"%s\" exists", req.params("id")));
            }
            else {
                return gson.toJson(scopedarticleToFind);
            }

        });


        post("/scopedscopedarticles/new", "application/json", (req, res) -> {
            Article Article  = gson.fromJson(req.body(), Article.class);
            articleDao.add(Article);
            res.status(201);;
            return gson.toJson(Article);
        });

        get("/scopedarticles", "application/json", (req, res) -> {
            return gson.toJson(articleDao.getAll());
        });

        get("/scopedarticles/:id", "application/json", (req, res) -> {
            return gson.toJson(articleDao.findById(Integer.parseInt(req.params("id"))));
        });


        after((req, res) ->{
            res.type("application/json");
        });
    }
}
