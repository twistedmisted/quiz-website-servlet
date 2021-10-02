package com.epam.final_project.dao.entity;

import com.epam.final_project.dao.DbManager;
import com.epam.final_project.dao.MySQLDAOFactory;
import com.epam.final_project.exception.DbException;
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

    @Mock
    MySQLDAOFactory mySQLDAOFactory;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getQuizTest() throws SQLException {
        Connection connection = mock(Connection.class);
        QuizDAO quizDAO = mock(QuizDAO.class);
        when(dbManager.getConnection()).thenReturn(connection);
        when(mySQLDAOFactory.getQuizDAO()).thenReturn(quizDAO);
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
