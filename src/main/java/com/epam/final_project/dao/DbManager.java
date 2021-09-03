package com.epam.final_project.dao;

import com.epam.final_project.dao.entity.*;
import com.example.main_project.dao.entity.*;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class DbManager {

    private static DbManager instance;

    private UserDAO userDAO;

    private QuestionDAO questionDAO;

    private QuizDAO quizDAO;

    private VariantsDAO variantsDAO;

    private AnswersDAO answersDAO;

    private SubjectDAO subjectDAO;

    public final DataSource dataSource;

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
        if (variantsDAO == null) {
            variantsDAO = new VariantsDAO();
        }
        return variantsDAO;
    }

    public UserDAO getUserDAO() {
        if (userDAO == null) {
            userDAO = new UserDAO();
        }
        return userDAO;
    }

    public QuestionDAO getQuestionDAO() {
        if (questionDAO == null) {
            questionDAO = new QuestionDAO();
        }
        return questionDAO;
    }

    public QuizDAO getQuizDAO() {
        if (quizDAO == null) {
            quizDAO = new QuizDAO();
        }
        return quizDAO;
    }

    public AnswersDAO getAnswerDAO() {
        if (answersDAO == null) {
            answersDAO = new AnswersDAO();
        }
        return answersDAO;
    }

    public SubjectDAO getSubjectDAO() {
        if (subjectDAO == null) {
            subjectDAO = new SubjectDAO();
        }
        return subjectDAO;
    }
}
