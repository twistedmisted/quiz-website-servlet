package com.epam.final_project.dao;

import com.epam.final_project.dao.entity.UserDAO;
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

public class UserDAOTest {

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
    public void getByLoginAndPasswordTest() throws SQLException {
        Connection connection = mock(Connection.class);
        UserDAO userDAO = mock(UserDAO.class);
        when(dbManager.getConnection()).thenReturn(connection);
        when(dbManager.getUserDAO()).thenReturn(userDAO);
        try {
            when(userDAO.getByLoginAndPassword("incorrect","incorrect")).thenThrow(DbException.class);
        } catch (DbException e) {
            e.printStackTrace();
        }
        try {
            assertNull(userDAO.getByLoginAndPassword("incorrect", "incorrect"));
        } catch (DbException e) {
            e.printStackTrace();
        }

    }

}
