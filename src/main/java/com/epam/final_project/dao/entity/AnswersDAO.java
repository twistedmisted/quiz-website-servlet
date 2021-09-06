package com.epam.final_project.dao.entity;

import com.epam.final_project.dao.DbManager;
import com.epam.final_project.dao.model.Question;
import com.epam.final_project.exception.DbException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AnswersDAO {

    private static final Logger LOGGER = LogManager.getLogger(AnswersDAO.class);

    private static final String GET_ANSWERS = "SELECT answer FROM answers WHERE question_id=(?);";

    private static final String INSERT_ANSWERS = "INSERT INTO answers (answer, question_id) VALUES (?, ?);";

    private final DbManager dbManager;

    public AnswersDAO() {
        dbManager = DbManager.getInstance();
    }

    public List<Character> getAllById(long id) throws DbException {
        List<Character> answers = new ArrayList<>();
        try (Connection connection = dbManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(GET_ANSWERS)) {
            int k = 0;
            statement.setLong(++k, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    answers.add(resultSet.getString("answer").charAt(0));
                }
                return answers;
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            throw new DbException("Can not to get answers for quiz", e);
        }
    }

    public void insert(Question question) throws DbException {
        Connection connection = null;
        try {
            connection = dbManager.getConnection();
            connection.setAutoCommit(false);
            connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
            addAnswers(connection, question);
            connection.commit();
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            rollback(connection);
            throw new DbException("Can not to insert answers for question", e);
        } finally {
            close(connection);
        }
    }

    private void addAnswers(Connection connection, Question question) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(INSERT_ANSWERS,
                Statement.RETURN_GENERATED_KEYS)) {
            for (int i = 0; i < question.getAnswers().size(); i++) {
                int k = 0;
                statement.setString(++k, String.valueOf(question.getAnswers().get(i)));
                statement.setLong(++k, question.getId());
                statement.executeUpdate();
            }
        }
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