package final_project.dao;

import final_project.dao.db.SqlDaoException;
import final_project.entity.Comment;

import java.util.List;

public interface AdministratorDAO {
    List<Comment> getAllComment() throws SqlDaoException;
}
