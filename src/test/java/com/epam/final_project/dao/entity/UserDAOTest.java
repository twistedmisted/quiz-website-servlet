package com.epam.final_project.dao.entity;

import com.epam.final_project.dao.DbManager;
import com.epam.final_project.dao.MySQLDAOFactory;
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

    @Mock
    MySQLDAOFactory mySQLDAOFactory;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getByLoginAndPasswordTest() throws SQLException {
        Connection connection = mock(Connection.class);
        UserDAO userDAO = mock(UserDAO.class);
        when(dbManager.getConnection()).thenReturn(connection);
        when(mySQLDAOFactory.getUserDAO()).thenReturn(userDAO);
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
