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
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LogInCommand implements Command {

    private static final Logger LOGGER = LogManager.getLogger(LogInCommand.class);

    private final DbManager dbManager = DbManager.getInstance();

    private final UserDAO userDAO = dbManager.getUserDAO();

    @Override
    public Page execute(HttpServletRequest request, HttpServletResponse response) {
        String login = request.getParameter("login");
        String password = PasswordEncryption.encrypt(request.getParameter("password"));
        try {
            User user = userDAO.getByLoginAndPassword(login, password);
            if (user != null) {
                if (!user.getAccessLevel().equals("banned")) {
                    request.getSession().setAttribute("user", user);
                    return new Page("/app/home", true);
                }
                return new Page("/login.jsp?state=banned", true);
            }
            return new Page("/login.jsp?error=true", true);
        } catch (DbException e) {
            LOGGER.error(e);
            return new Page("/login.jsp?error=true", true);
        }
    }

}
