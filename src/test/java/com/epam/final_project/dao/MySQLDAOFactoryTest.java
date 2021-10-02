package com.epam.final_project.dao;

import com.epam.final_project.dao.entity.*;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class MySQLDAOFactoryTest {

    MySQLDAOFactory mySQLDAOFactory;

    @Before
    public void setUp() {
        mySQLDAOFactory = new MySQLDAOFactory();
    }

    @Test
    public void getUserDAOTest() {
        UserDAO userDAO = mySQLDAOFactory.getUserDAO();
        assertNotNull(userDAO);
    }

    @Test
    public void getQuestionDAOTest() {
        QuestionDAO questionDAO = mySQLDAOFactory.getQuestionDAO();
        assertNotNull(questionDAO);
    }

    @Test
    public void getQuizDAOTest() {
        QuizDAO quizDAO = mySQLDAOFactory.getQuizDAO();
        assertNotNull(quizDAO);
    }

    @Test
    public void getVariantsDAOTest() {
        VariantsDAO variantsDAO = mySQLDAOFactory.getVariantsDAO();
        assertNotNull(variantsDAO);
    }

    @Test
    public void getAnswerDAOTest() {
        AnswersDAO answersDAO = mySQLDAOFactory.getAnswersDAO();
        assertNotNull(answersDAO);
    }

}