package com.epam.final_project.app.commands.user;

import com.epam.final_project.app.Page;
import com.epam.final_project.app.commands.Command;
import com.epam.final_project.dao.DbManager;
import com.epam.final_project.dao.entity.UserDAO;
import com.epam.final_project.dao.model.User;
import com.epam.final_project.exception.DbException;
import com.epam.final_project.utils.PasswordEncryption;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

public class RegistrationCommand implements Command {

    private static final Logger LOGGER = Logger.getLogger(RegistrationCommand.class);

    private final DbManager dbManager = DbManager.getInstance();

    private final UserDAO userDAO = dbManager.getUserDAO();

    @Override
    public Page execute(HttpServletRequest request, HttpServletResponse response) {
        String email = request.getParameter("email");
        String login = request.getParameter("login");
        String password = request.getParameter("password");
        String passwordConfirm = request.getParameter("password_confirm");

        try {
            if (!password.isEmpty()
                    && !passwordConfirm.isEmpty()
                    && password.equals(passwordConfirm)
                    && userDAO.getByLogin(login) == null
                    && userDAO.getByEmail(email) == null) {
                userDAO.insert(User.createUser(email, login, PasswordEncryption.encrypt(password), "user"));
                return new Page("/login.jsp", true);
            }
            return new Page("/registration.jsp?error=true", true);
        } catch (DbException e) {
            LOGGER.error(e.getMessage());
            return new Page("/registration.jsp?error=true", true);
        }
    }

}
