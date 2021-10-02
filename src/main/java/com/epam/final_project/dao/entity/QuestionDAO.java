package com.epam.final_project.dao.entity;

import com.epam.final_project.dao.DbManager;
import com.epam.final_project.dao.MySQLDAOFactory;
import com.epam.final_project.dao.model.Question;
import com.epam.final_project.exception.DbException;
import com.epam.final_project.exception.NotSupportedActionException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class QuestionDAO implements DAO<Question> {

    private static final Logger LOGGER = LogManager.getLogger(QuestionDAO.class);

    private static final String GET_QUESTION_BY_ID = "SELECT * FROM question WHERE id=(?);";

    private static final String GET_QUESTION_BY_PROMPT = "SELECT * FROM question WHERE prompt=(?);";

    private static final String GET_QUESTIONS_BY_QUIZ_ID = "SELECT * FROM question WHERE quiz_id=(?);";

    private static final String DELETE_QUESTION = "DELETE FROM question WHERE id=(?);";

    private static final String INSERT_QUESTION = "INSERT INTO question VALUES (default, ?, ?);";

    private static final String GET_NUMBER_OF_QUESTIONS = "SELECT COUNT(*) FROM question WHERE quiz_id=?;";

    private final DbManager dbManager;

    private final VariantsDAO variantsDAO;

    private final AnswersDAO answersDAO;

    public QuestionDAO() {
        MySQLDAOFactory mySQLDAOFactory = new MySQLDAOFactory();
        dbManager = DbManager.getInstance();
        variantsDAO = mySQLDAOFactory.getVariantsDAO();
        answersDAO = mySQLDAOFactory.getAnswersDAO();
    }

    @Override
    public Question get(long id) throws DbException {
        try (Connection connection = dbManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(GET_QUESTION_BY_ID)) {
            int k = 0;
            statement.setLong(++k, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return mapQuestion(resultSet);
                }
                return null;
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            throw new DbException("Can not to get question", e);
        }
    }

    @Override
    public List<Question> getAll() throws DbException {
        throw new NotSupportedActionException("This action is not supported");
    }

    @Override
    public Question insert(Question question) throws DbException {
        throw new NotSupportedActionException("This action is not supported");
    }

    public Question insert(Question question, long quizId) throws DbException {
        Connection connection = null;
        try {
            connection = dbManager.getConnection();
            connection.setAutoCommit(false);
            connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
            addQuestion(connection, question, quizId);
            connection.commit();
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            rollback(connection);
            throw new DbException("Can not to insert question", e);
        } finally {
            close(connection);
        }
        return question;
    }

    @Override
    public void update(Question question) throws DbException {
        throw new NotSupportedActionException("This actions is not supported");
    }

    @Override
    public void delete(Question question) throws DbException {
        try (Connection connection = dbManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_QUESTION)) {
            int k = 0;
            statement.setLong(++k, question.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            throw new DbException("Can not to delete question", e);
        }
    }

    public Question get(String prompt) throws DbException {
        try (Connection connection = dbManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(GET_QUESTION_BY_PROMPT)) {
            int k = 0;
            statement.setString(++k, prompt);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return mapQuestion(resultSet);
                }
                return null;
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            throw new DbException("Can not to get question", e);
        }
    }

    public List<Question> getAllByQuizId(long id) throws DbException {
        List<Question> questions = new ArrayList<>();
        try (Connection connection = dbManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(GET_QUESTIONS_BY_QUIZ_ID)) {
            int k = 0;
            statement.setLong(++k, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    questions.add(mapQuestion(resultSet));
                }
                return questions;
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            throw new DbException("Can not to get all questions", e);
        }
    }

    public int getNumberQuestionsByQuiz(long quizId) throws DbException {
        try (Connection connection = dbManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(GET_NUMBER_OF_QUESTIONS)) {
            int k = 0;
            statement.setLong(++k, quizId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt(1);
                }
                return 0;
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            throw new DbException("Can not to get number of questions by quiz", e);
        }
    }

    private Question mapQuestion(ResultSet resultSet) throws SQLException {
        Question question = new Question();
        long id = resultSet.getLong("id");
        question.setId(id);
        question.setPrompt(resultSet.getString("prompt"));
        try {
            question.setVariants(variantsDAO.getAllById(id));
            question.setAnswers(answersDAO.getAllById(id));
        } catch (DbException e) {
            LOGGER.error(e);
            throw new SQLException("Can not to set options", e);
        }
        return question;
    }


    private void addQuestion(Connection connection, Question question, long quizId) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(INSERT_QUESTION,
                Statement.RETURN_GENERATED_KEYS)) {
            int k = 0;
            statement.setString(++k, question.getPrompt());
            statement.setLong(++k, quizId);
            int count = statement.executeUpdate();
            if (count > 0) {
                try (ResultSet resultSet = statement.getGeneratedKeys()) {
                    if (resultSet.next()) {
                        question.setId(resultSet.getLong(1));
                        return;
                    }
                }
            }
        }
        throw new SQLException("Can not to add a question");
    }


    private void rollback(Connection con) {
        try {
            if (con != null) {
                con.rollback();
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        }
    }

    private void close(AutoCloseable stmt) {
        if (stmt != null) {
            try {
                stmt.close();
            } catch (Exception e) {
                LOGGER.error(e.getMessage());
            }
        }
    }

}
