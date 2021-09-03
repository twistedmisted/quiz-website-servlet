package com.epam.final_project.dao.entity;

import com.epam.final_project.dao.DbManager;
import com.epam.final_project.exception.DbException;
import com.epam.final_project.exception.NotSupportedActionException;
import com.epam.final_project.dao.model.Question;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class QuestionDAO implements DAO<Question> {

    private static final Logger LOGGER = Logger.getLogger(QuestionDAO.class);

    private static final String GET_QUESTION_BY_ID = "SELECT * FROM question WHERE id=(?);";

    private static final String GET_QUESTION_BY_PROMPT = "SELECT * FROM question WHERE prompt=(?);";

    private static final String GET_QUESTIONS_BY_QUIZ_ID = "SELECT * FROM questions_quiz WHERE quiz_id=(?);";

    private static final String GET_QUESTIONS = "SELECT * FROM question ORDER BY id";

    private static final String DELETE_QUESTION = "DELETE FROM question WHERE id=(?);";

    private static final String INSERT_QUESTION = "INSERT INTO question (prompt) VALUES (?);";

    private static final String GET_QUESTIONS_BY_RANGE = "SELECT * FROM questions_quiz WHERE quiz_id=? LIMIT ?,?;";

    private static final String GET_NUMBER_OF_QUESTIONS = "SELECT COUNT(*) FROM questions_quiz WHERE quiz_id=?;";

    private DbManager dbManager;

    private VariantsDAO variantsDAO;

    private AnswersDAO answersDAO;

    public QuestionDAO() {
        dbManager = DbManager.getInstance();
        variantsDAO = dbManager.getVariantsDAO();
        answersDAO = dbManager.getAnswerDAO();
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
    public void insert(Question question) throws DbException {
        Connection connection = null;
        try {
            connection = dbManager.getConnection();
            connection.setAutoCommit(false);
            connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
            addQuestion(connection, question);
            connection.commit();
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            rollback(connection);
            throw new DbException("Can not to insert question", e);
        } finally {
            close(connection);
        }
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
                    questions.add(get(resultSet.getLong("question_id")));
                }
                return questions;
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            throw new DbException("Can not to get all questions", e);
        }
    }

    public List<Question> getQuestionsForQuizByRange(long quizId, long start, int number) throws DbException {
        List<Question> questions = new ArrayList<>();
        try (Connection connection = dbManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(GET_QUESTIONS_BY_RANGE)) {
            int k = 0;
            statement.setLong(++k, quizId);
            statement.setLong(++k, start);
            statement.setLong(++k, number);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    questions.add(mapQuestion(resultSet));
                }
            }
            return questions;
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            throw new DbException("Can not to get questions by range", e);
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


    private void addQuestion(Connection connection, Question question) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(INSERT_QUESTION,
                Statement.RETURN_GENERATED_KEYS)) {
            int k = 0;
            statement.setString(++k, question.getPrompt());
            int count = statement.executeUpdate();
            if (count > 0) {
                try (ResultSet resultSet = statement.getGeneratedKeys()) {
                    if (resultSet.next()) {
                        question.setId(resultSet.getLong(1));
                    }
                }
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
