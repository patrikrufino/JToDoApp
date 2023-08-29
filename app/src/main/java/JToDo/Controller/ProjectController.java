package JToDo.Controller;

import JToDo.Model.Project;
import JToDo.Util.ConnectionFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author patrik
 */
public class ProjectController {

    public void save(Project project) {
        String sql = "INSET INTO projects (name,"
                + "description,"
                + "createdAt,"
                + "updateAt)"
                + " VALUES(?,?,?,?)";

        Connection connection = null;
        PreparedStatement statement = null;

        try {

            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setString(1, project.getName());
            statement.setString(2, project.getDescription());
            statement.setDate(3, new Date(project.getCreatedAt().getTime()));
            statement.setDate(4, new Date(project.getUpdateAt().getTime()));
            statement.execute();

        } catch (SQLException e) {

            throw new RuntimeException("Erro ao salvar projeto" + e.getMessage(), e);

        } finally {

            ConnectionFactory.closeConnection(connection, statement);

        }
    }

    /**
     *
     * @param project
     */
    public void update(Project project) {

        String sql = "UPDATE projects SET"
                + "name = ?,"
                + "description = ?,"
                + "createdAt = ?,"
                + "updateAt = ?"
                + "WHERE id = ?";

        Connection connection = null;
        PreparedStatement statement = null;

        try {

            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setString(1, project.getName());
            statement.setString(2, project.getDescription());
            statement.setDate(3, new Date(project.getCreatedAt().getTime()));
            statement.setDate(4, new Date(project.getUpdateAt().getTime()));
            statement.setInt(5, project.getId());
            statement.execute();

        } catch (SQLException e) {

            throw new RuntimeException("Erro ao atualizar" + e.getMessage(), e);

        } finally {

            ConnectionFactory.closeConnection(connection, statement);

        }

    }

    public void removeById(int projecttId) throws SQLException {

        String sql = "DELETE FROM projects WHERE id = ?";

        Connection connection = null;
        PreparedStatement statement = null;

        try {

            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setInt(1, projecttId);
            statement.execute();

        } catch (SQLException e) {

            throw new SQLException("Erro ao deletar a task" + e.getMessage(), e);

        } finally {

            ConnectionFactory.closeConnection(connection, statement);

        }

    }

    public List<Project> getAll(int idProject) {

        String sql = "SELECT * FROM projects WHERE id = ?";

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        List<Project> projects = new ArrayList<Project>();

        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setInt(1, idProject);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {

                Project project = new Project();
                project.setId(resultSet.getInt("id"));
                project.setName(resultSet.getString("name"));
                project.setDescription(resultSet.getString("description"));
                project.setCreatedAt(resultSet.getDate("createdAt"));
                project.setUpdateAt(resultSet.getDate("updateAt"));

            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar projetos" + e.getMessage(), e);
        } finally {
            ConnectionFactory.closeConnection(connection, statement, resultSet);

        }
        
        return projects;

    }

}
