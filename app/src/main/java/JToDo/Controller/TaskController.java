package JToDo.Controller;

import JToDo.Model.Task;
import JToDo.Util.ConnectionFactory;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author patrik
 */
public class TaskController {
    
    /**
     * M�todos para salvar novas tasks no banco de dados.
     * @param task;
     */
    public void save(Task task) {

        String sql = "INSERT INTO tasks (idProject, "
                + "name,"
                + "description, "
                + "completed,"
                + "notes,"
                + "deadline,"
                + "createdAt,"
                + "updateAt)"
                + "VALUES ("
                + "?,?,?,?,?,?,?,?"
                + ")";

        Connection connection = null;
        PreparedStatement statement = null;

        try {

            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setInt(1, task.getIdProject());
            statement.setString(2, task.getName());
            statement.setString(3, task.getDescription());
            statement.setBoolean(4, task.isCompleted());
            statement.setString(5, task.getNotes());
            statement.setDate(6, new Date(task.getDeadline().getTime()));
            statement.setDate(7, new Date(task.getCreatedAt().getTime()));
            statement.setDate(8, new Date(task.getUpdatedAt().getTime()));
            statement.execute();

        } catch (SQLException e) {

            throw new RuntimeException("Erro ao salvar a tarefa" + e.getMessage(), e);

        } finally {

            ConnectionFactory.closeConnection(connection, statement);
            
        }
    }
    
    /**
     * Metodo para atualiazar uma tarefa no banco de dados.
     * @param task;
     */
    public void update(Task task) {
        
        String sql = "UPDATE tasks SET"
                + "idProject = ?,"
                + "name = ?,"
                + "description = ?,"
                + "completed = ?,"
                + "notes = ?,"
                + "deadline = ?,"
                + "createdAt = ?,"
                + "updateAt = ?"
                + "WHERE id = ?";

        Connection connection = null;
        PreparedStatement statement = null;

        try {
            
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setInt(1, task.getIdProject());
            statement.setString(2, task.getName());
            statement.setString(3, task.getDescription());
            statement.setBoolean(4, task.isCompleted());
            statement.setString(5, task.getNotes());
            statement.setDate(6, new Date(task.getDeadline().getTime()));
            statement.setDate(7, new Date(task.getCreatedAt().getTime()));
            statement.setDate(8, new Date(task.getUpdatedAt().getTime()));
            statement.setInt(9, task.getId());
            statement.execute();

        } catch (SQLException e) {
            
            throw new RuntimeException("Erro ao atualizar" + e.getMessage(), e);
            
        } finally {
            
            ConnectionFactory.closeConnection(connection, statement);
            
        }
    }
    
    /**
     * Metodo para remover uma tarefa no banco de dados através de um ID.
     * @param taskId;
     * @throws java.sql.SQLException
     */
    public void removeById(int taskId) throws SQLException {

        String sql = "DELETE FROM tasks WHERE id = ?";

        Connection connection = null;
        PreparedStatement statement = null;

        try {

            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(sql);

            statement.setInt(1, taskId);
            statement.execute();

        } catch (SQLException e) {
            
            throw new SQLException("Erro ao deletar a task" + e.getMessage(), e);
            
        } finally {
            
            ConnectionFactory.closeConnection(connection, statement);
            
        }

    }
    
    /**
     * Método para retornar uma lista das tarefas de acordo com o projeto.
     * @param idProject;
     * @return Uma lista de tarefas com base no projeto.
     */
    public List<Task> getAll(int idProject) {

        String sql = "SELECT * FROM tasks WHERE idProject = ?";

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        List<Task> tasks = new ArrayList<Task>();

        try {

            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(sql);

            statement.setInt(1, idProject);

            resultSet = statement.executeQuery();

            while (resultSet.next()) {

                Task task = new Task();
                task.setId(resultSet.getInt("id"));
                task.setIdProject(resultSet.getInt("idProject"));
                task.setName(resultSet.getString("name"));
                task.setDescription(resultSet.getString("description"));
                task.setNotes(resultSet.getString("notes"));
                task.setIsCompleted(resultSet.getBoolean("isCompleted"));
                task.setDeadline(resultSet.getDate("deadline"));
                task.setCreatedAt(resultSet.getDate("createdAt"));
                task.setUpdatedAt(resultSet.getDate("updatedAt"));

                tasks.add(task);

            }
        } catch (Exception e) {

            throw new RuntimeException("Erro ao buscar tarefas" + e.getMessage(), e);

        } finally {

            ConnectionFactory.closeConnection(connection, statement, resultSet);

        }

        return tasks;
    }

}
