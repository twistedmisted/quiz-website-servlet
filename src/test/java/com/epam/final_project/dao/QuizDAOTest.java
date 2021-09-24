package com.epam.final_project.dao;

import com.epam.final_project.dao.entity.QuizDAO;
import com.epam.final_project.exception.DbException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class QuizDAOTest {

    @Mock
    DbManager dbManager;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void getQuizTest() throws SQLException {
        Connection connection = mock(Connection.class);
        QuizDAO quizDAO = mock(QuizDAO.class);
        when(dbManager.getConnection()).thenReturn(connection);
        when(dbManager.getQuizDAO()).thenReturn(quizDAO);
        try {
            when(quizDAO.get("Incorrect Quiz Name")).thenThrow(DbException.class);
        } catch (DbException e) {
            e.printStackTrace();
        }
        try {
            assertNull(quizDAO.get("Incorrect Quiz Name"));
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

}
