package com.epam.final_project.dao.entity;

import com.epam.final_project.dao.DbManager;
import com.epam.final_project.exception.DbException;
import com.epam.final_project.exception.NotSupportedActionException;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SubjectDAO implements DAO<String> {

    private static final Logger LOGGER = Logger.getLogger(SubjectDAO.class);

    private static final String GET_ALL_SUBJECT = "SELECT name FROM subject ORDER BY id;";

    private static final String GET_SUBJECT_FOR_QUIZ =
            "SELECT subject.name FROM quiz, subject WHERE quiz.subject_id=subject.id and quiz.id=(?);";

    private final DbManager dbManager;

    public SubjectDAO() {
        dbManager = DbManager.getInstance();
    }

    public String setSubjectForQuiz(long quizId) throws DbException {
        try (Connection connection = dbManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(GET_SUBJECT_FOR_QUIZ)) {
            int k = 0;
            statement.setLong(++k, quizId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getString("name");
                }
                return null;
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            throw new DbException("Can not to set subject for quiz", e);
        }
    }

    @Override
    public String get(long id) throws DbException {
        throw new NotSupportedActionException("This is not supported action");
    }

    @Override
    public List<String> getAll() throws DbException {
        List<String> subjects = new ArrayList<>();
        try (Connection connection = dbManager.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(GET_ALL_SUBJECT)) {
            while (resultSet.next()) {
                subjects.add(resultSet.getString(1));
            }
            return subjects;
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            throw new DbException("Can not to get subjects", e);
        }
    }

    @Override
    public void insert(String s) throws DbException {
        throw new NotSupportedActionException("This is not supported action");
    }

    @Override
    public void update(String s) throws DbException {
        throw new NotSupportedActionException("This is not supported action");
    }

    @Override
    public void delete(String s) throws DbException {
        throw new NotSupportedActionException("This is not supported action");
    }
}
