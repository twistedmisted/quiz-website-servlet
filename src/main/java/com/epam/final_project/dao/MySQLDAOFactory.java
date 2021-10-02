package com.epam.final_project.dao;

import com.epam.final_project.dao.entity.*;

public class MySQLDAOFactory implements DAOFactory {

    @Override
    public UserDAO getUserDAO() {
        return new UserDAO();
    }

    @Override
    public QuestionDAO getQuestionDAO() {
        return new QuestionDAO();
    }

    @Override
    public QuizDAO getQuizDAO() {
        return new QuizDAO();
    }

    @Override
    public VariantsDAO getVariantsDAO() {
        return new VariantsDAO();
    }

    @Override
    public AnswersDAO getAnswersDAO() {
        return new AnswersDAO();
    }

}
