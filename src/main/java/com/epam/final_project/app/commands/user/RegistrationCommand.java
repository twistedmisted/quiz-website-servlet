package com.epam.final_project.app.commands.user;

import com.epam.final_project.app.commands.Command;
import com.epam.final_project.app.web.Page;
import com.epam.final_project.dao.MySQLDAOFactory;
import com.epam.final_project.dao.entity.UserDAO;
import com.epam.final_project.dao.model.User;
import com.epam.final_project.exception.DbException;
import com.epam.final_project.utils.PasswordEncryption;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RegistrationCommand implements Command {

    private static final Logger LOGGER = LogManager.getLogger(RegistrationCommand.class);

    private final UserDAO userDAO;

    public RegistrationCommand() {
        MySQLDAOFactory mySQLDAOFactory = new MySQLDAOFactory();
        userDAO = mySQLDAOFactory.getUserDAO();
    }

    @Override
    public Page execute(HttpServletRequest request, HttpServletResponse response) {
        String email = request.getParameter("email");
        String login = request.getParameter("login");
        String password = request.getParameter("password");
        String passwordConfirm = request.getParameter("password_confirm");
        try {
            if (isDataCorrect(email, login, password, passwordConfirm)) {
                userDAO.insert(User.createUser(email, login, PasswordEncryption.encrypt(password), "user"));
                return new Page("/login.jsp", true);
            }
            return new Page("/registration.jsp?error=true", true);
        } catch (DbException e) {
            LOGGER.error(e.getMessage());
            return new Page("/registration.jsp?error=true", true);
        }
    }

    private boolean isDataCorrect(String email, String login, String password, String passwordConfirm) throws DbException {
        if (password.isEmpty()) {
            return false;
        }
        if (passwordConfirm.isEmpty()) {
            return false;
        }
        if (!password.equals(passwordConfirm)) {
            return false;
        }
        if (userDAO.getByLogin(login) != null) {
            return false;
        }
        return userDAO.getByEmail(email) == null;
    }

}
