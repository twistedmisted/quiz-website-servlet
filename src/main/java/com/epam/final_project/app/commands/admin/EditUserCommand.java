package com.epam.final_project.app.commands.admin;

import com.epam.final_project.app.Page;
import com.epam.final_project.app.commands.Command;
import com.epam.final_project.dao.DbManager;
import com.epam.final_project.dao.entity.UserDAO;
import com.epam.final_project.exception.DbException;
import com.epam.final_project.dao.model.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class EditUserCommand implements Command {

    private static final Logger LOGGER = LogManager.getLogger(EditUserCommand.class);

    @Override
    public Page execute(HttpServletRequest request, HttpServletResponse response) {
        DbManager dbManager = DbManager.getInstance();
        UserDAO userDAO = dbManager.getUserDAO();
        long id = Long.parseLong(request.getParameter("id"));
        try {
            User user = userDAO.get(id);
            if (user == null) {
                return new Page("/admin/users?page=" + request.getParameter("page"), true);
            }
            if (request.getMethod().equalsIgnoreCase("getByLoginAndPassword")) {
                request.setAttribute("user", user);
                request.setAttribute("id", id);
                request.setAttribute("page", request.getParameter("page"));
                return new Page("/WEB-INF/jsp/admin/edit-user.jsp", false);
            }
            userDAO.update(createNewUser(request, userDAO, user));
            return new Page("/admin/users?page=" + request.getParameter("page"), true);
        } catch (DbException e) {
            LOGGER.error(e);
            return new Page("/admin/users?error=true", true);
        }
    }

    private User createNewUser(HttpServletRequest request, UserDAO userDAO, User user) throws DbException {
        String email = request.getParameter("email");
        String login = request.getParameter("login");
        String accessLevel = request.getParameter("access-level");
        if (!email.isEmpty() && userDAO.getByEmail(email) == null) {
            user.setEmail(email);
        }
        if (!login.isEmpty() && userDAO.getByLogin(login) == null) {
            user.setLogin(login);
        }
        if (!accessLevel.isEmpty()) {
            user.setAccessLevel(accessLevel);
        }
        return user;
    }
}
