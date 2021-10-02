package com.epam.final_project.dao;

import com.epam.final_project.dao.entity.*;

public interface DAOFactory {
    UserDAO getUserDAO();
    QuestionDAO getQuestionDAO();
    QuizDAO getQuizDAO();
    VariantsDAO getVariantsDAO();
    AnswersDAO getAnswersDAO();
}
