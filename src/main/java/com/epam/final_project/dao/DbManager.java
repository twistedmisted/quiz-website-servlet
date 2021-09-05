package com.epam.final_project.dao;

import com.epam.final_project.dao.entity.*;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class DbManager {

    private static DbManager instance;

    private final DataSource dataSource;

    private DbManager() {
        try {
            Context initContext = new InitialContext();
            Context envContext = (Context) initContext.lookup("java:comp/env");
            dataSource = (DataSource) envContext.lookup("jdbc/webapp-pool");
        } catch (NamingException e) {
            throw new IllegalStateException("Cannot init DbManager", e);
        }
    }

    public static synchronized DbManager getInstance() {
        if (instance == null) {
            instance = new DbManager();
        }
        return instance;
    }

    public Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    public VariantsDAO getVariantsDAO() {
        return new VariantsDAO();
    }

    public UserDAO getUserDAO() {
        return new UserDAO();
    }

    public QuestionDAO getQuestionDAO() {
        return new QuestionDAO();
    }

    public QuizDAO getQuizDAO() {
        return new QuizDAO();
    }

    public AnswersDAO getAnswerDAO() {
        return new AnswersDAO();
    }

}
