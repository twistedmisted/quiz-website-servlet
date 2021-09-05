package com.epam.final_project.dao.entity;

import com.epam.final_project.dao.DbManager;
import com.epam.final_project.dao.model.Question;
import com.epam.final_project.dao.model.Quiz;
import com.epam.final_project.exception.DbException;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class QuizDAO implements DAO<Quiz> {

    // TODO: ADD SUBJECT FOR ALL QUERY

    private static final Logger LOGGER = Logger.getLogger(QuizDAO.class);

    private static final String GET_QUIZ_BY_ID = "SELECT * FROM quiz WHERE id=(?);";

    private static final String GET_QUIZ_BY_NAME = "SELECT * FROM quiz WHERE name=(?);";

    private static final String GET_QUIZ_BY_SUBJECT = "SELECT * FROM quiz WHERE subject=(?);";

    private static final String DELETE_QUIZ = "DELETE FROM quiz WHERE id=(?);";

    private static final String UPDATE_QUIZ = "UPDATE quiz SET name=(?), time=(?), difficulty=(?), subject=(?) WHERE id=(?);";

    private static final String INSERT_QUIZ = "INSERT INTO quiz (name, time, difficulty, subject) VALUES (?, ?, ?, ?);";

    private static final String SET_QUESTION_FOR_QUIZ = "INSERT INTO questions_quiz (quiz_id, question_id) VALUES (?, ?);";

    private static final String GET_LAST_FOUR_QUIZZES =
            "SELECT quiz.* FROM quiz, questions_quiz " +
                    "WHERE quiz.id=questions_quiz.quiz_id " +
                    "GROUP BY quiz_id ORDER BY quiz.id DESC LIMIT 4;";

    private static final String GET_ALL_QUIZ =
            "SELECT quiz.* FROM quiz, questions_quiz " +
                    "WHERE quiz.id=questions_quiz.quiz_id GROUP BY quiz_id ORDER BY id;";

    private static final String INSERT_USER_FOR_QUIZ = "INSERT INTO users_quizzes (user_id, quiz_id) VALUES (?, ?);";

    private static final String GET_FOUR_USER_QUIZZES =
            "SELECT quiz.* FROM users_quizzes, quiz " +
                    "WHERE users_quizzes.user_id=(?) and users_quizzes.quiz_id=quiz.id " +
                    "ORDER BY id DESC " +
                    "LIMIT 4;";

    private static final String GET_ALL_SORT_BY_NAME = "SELECT * FROM quiz ORDER BY name";

    private static final String UPDATE_SCORE = "UPDATE users_quizzes SET score=? WHERE user_id=? AND quiz_id=?;";

    private static final String GET_ALL_SORT_BY_NUMBER_OF_QUESTIONS = "SELECT quiz.* FROM quiz, questions_quiz WHERE quiz.id=questions_quiz.quiz_id GROUP BY questions_quiz.quiz_id ORDER BY COUNT(questions_quiz.quiz_id);";

    private static final String GET_QUIZZES_BY_RANGE = "SELECT * FROM quiz LIMIT ?,?;";

    private static final String GET_NUMBER_OF_QUIZZES = "SELECT COUNT(*) FROM quiz;";

    private static final String GET_SUBJECTS = "SELECT DISTINCT subject FROM quiz;";

    private final DbManager dbManager;

    private final QuestionDAO questionDAO;

    public QuizDAO() {
        dbManager = DbManager.getInstance();
        questionDAO = dbManager.getQuestionDAO();
    }

    @Override
    public Quiz get(long id) throws DbException {
        try (Connection connection = dbManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(GET_QUIZ_BY_ID)) {
            int k = 0;
            statement.setLong(++k, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return mapQuiz(resultSet);
                }
                return null;
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            throw new DbException("Can not to get quiz", e);
        }
    }

    @Override
    public List<Quiz> getAll() throws DbException {
        return getQuizzes(GET_ALL_QUIZ);
    }

    @Override
    public void insert(Quiz quiz) throws DbException {
        Connection connection = null;
        try {
            connection = dbManager.getConnection();
            connection.setAutoCommit(false);
            connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
            addQuiz(connection, quiz);
            connection.commit();
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            rollback(connection);
            throw new DbException("Can not to insert quiz", e);
        } finally {
            close(connection);
        }
    }

    @Override
    public void update(Quiz quiz) throws DbException {
        try (Connection connection = dbManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_QUIZ)) {
            int k = 0;
            statement.setString(++k, quiz.getName());
            statement.setInt(++k, quiz.getTime());
            statement.setString(++k, quiz.getDifficulty());
            statement.setString(++k, quiz.getSubject());
            statement.setLong(++k, quiz.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            throw new DbException("Can not to update quiz", e);
        }
    }

    @Override
    public void delete(Quiz quiz) throws DbException {
        try (Connection connection = dbManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_QUIZ)) {
            int k = 0;
            statement.setLong(++k, quiz.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            throw new DbException("Can not to delete quiz", e);
        }
    }

    public Quiz get(String name) throws DbException {
        try (Connection connection = dbManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(GET_QUIZ_BY_NAME)) {
            int k = 0;
            statement.setString(++k, name);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return mapQuiz(resultSet);
                }
                return null;
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            throw new DbException("Can not to get quiz", e);
        }
    }

    public List<Quiz> getAllSortedByName() throws DbException {
        return getQuizzes(GET_ALL_SORT_BY_NAME);
    }

    public List<Quiz> getAllBySubject(String name) throws DbException {
        List<Quiz> quizzes = new ArrayList<>();
        try (Connection connection = dbManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(GET_QUIZ_BY_SUBJECT)) {
            int k = 0;
            statement.setString(++k, name);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    quizzes.add(mapQuiz(resultSet));
                }
                return quizzes;
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            throw new DbException("Can not to get all quizzes by subject", e);
        }
    }

    public List<Quiz> getAllSortedByDifficulty() throws DbException {
        List<Quiz> quizzes = getAll();
        List<String> difficultyOrder = Arrays.asList("easy", "medium", "hard");
        Comparator<Quiz> quizComparator = Comparator.comparing(q -> difficultyOrder.indexOf(q.getDifficulty()));
        quizzes.sort(quizComparator);
        return quizzes;
    }

    public List<Quiz> getAllSortedByNumberOfQuestions() throws DbException {
        return getQuizzes(GET_ALL_SORT_BY_NUMBER_OF_QUESTIONS);
    }

    public List<Quiz> getLastFour() throws DbException {
        return getQuizzes(GET_LAST_FOUR_QUIZZES);
    }

    public List<Quiz> getQuizzesByUserId(long id) throws DbException {
        List<Quiz> quizzes = new ArrayList<>();
        try (Connection connection = dbManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(GET_FOUR_USER_QUIZZES)) {
            int k = 0;
            statement.setLong(++k, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    quizzes.add(mapQuiz(resultSet));
                }
                return quizzes;
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            throw new DbException("Can not to get quizzes", e);
        }
    }

    public List<Quiz> getByRange(long start, long number) throws DbException {
        List<Quiz> quizzes = new ArrayList<>();
        try (Connection connection = dbManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(GET_QUIZZES_BY_RANGE)) {
            int k = 0;
            statement.setLong(++k, start);
            statement.setLong(++k, number);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    quizzes.add(mapQuiz(resultSet));
                }
            }
            return quizzes;
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            throw new DbException("Can not to get quizzes by range", e);
        }
    }

    public int getNumber() throws DbException {
        try (Connection connection = dbManager.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(GET_NUMBER_OF_QUIZZES)) {
            if (resultSet.next()) {
                return resultSet.getInt(1);
            }
            return 0;
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            throw new DbException("Can not to number of quizzes", e);
        }
    }

    public List<String> getSubjects() throws DbException {
        List<String> subjects = new ArrayList<>();
        try (Connection connection = dbManager.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(GET_SUBJECTS)) {
            while (resultSet.next()) {
                subjects.add(resultSet.getString(1));
            }
            return subjects;
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            throw new DbException("Can not to get subjects", e);
        }
    }

    private List<Quiz> getQuizzes(String query) throws DbException {
        List<Quiz> quizzes = new ArrayList<>();
        try (Connection connection = dbManager.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                quizzes.add(mapQuiz(resultSet));
            }
            return quizzes;
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            throw new DbException("Can not to get quizzes", e);
        }
    }

    private Quiz mapQuiz(ResultSet resultSet) throws SQLException {
        Quiz quiz = new Quiz();
        quiz.setId(resultSet.getLong("id"));
        quiz.setName(resultSet.getString("name"));
        quiz.setTime(resultSet.getInt("time"));
        quiz.setDifficulty(resultSet.getString("difficulty"));
        quiz.setSubject(resultSet.getString("subject"));
        return quiz;
    }

    private void addQuiz(Connection connection, Quiz quiz) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(INSERT_QUIZ)) {
            int k = 0;
            statement.setString(++k, quiz.getName());
            statement.setInt(++k, quiz.getTime());
            statement.setString(++k, quiz.getDifficulty());
            statement.setString(++k, quiz.getSubject());
            statement.executeUpdate();
        }
    }

    public void setQuestionForQuiz(Quiz quiz, Question question) throws DbException {
        Connection connection = null;
        try {
            connection = dbManager.getConnection();
            connection.setAutoCommit(false);
            connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
            addQuestionForQuiz(connection, quiz, question);
            connection.commit();
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            rollback(connection);
            throw new DbException("Can not to set question for quiz", e);
        } finally {
            close(connection);
        }
    }

    public void setUserForQuiz(long userId, long quizId) throws DbException {
        try (Connection connection = dbManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(INSERT_USER_FOR_QUIZ)) {
            int k = 0;
            statement.setLong(++k, userId);
            statement.setLong(++k, quizId);
            statement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            throw new DbException("Can not to set user for quiz", e);
        }
    }

    private void addQuestionForQuiz(Connection connection, Quiz quiz, Question question) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(SET_QUESTION_FOR_QUIZ)) {
            int k = 0;
            statement.setLong(++k, quiz.getId());
            statement.setLong(++k, question.getId());
            statement.executeUpdate();
        }
    }

    public void updateScore(long userId, long quizId, int score) throws DbException {
        try (Connection connection = dbManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_SCORE)) {
            int k = 0;
            statement.setInt(++k, score);
            statement.setLong(++k, userId);
            statement.setLong(++k, quizId);
            statement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            throw new DbException("Can not to update score", e);
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
