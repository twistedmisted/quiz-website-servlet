package com.epam.final_project.app.commands.admin;

import com.epam.final_project.app.commands.Command;
import com.epam.final_project.app.web.Page;
import com.epam.final_project.dao.MySQLDAOFactory;
import com.epam.final_project.dao.entity.UserDAO;
import com.epam.final_project.dao.model.User;
import com.epam.final_project.exception.DbException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class EditUserCommand implements Command {

    private static final Logger LOGGER = LogManager.getLogger(EditUserCommand.class);

    private final UserDAO userDAO;

    public EditUserCommand() {
        MySQLDAOFactory mySQLDAOFactory = new MySQLDAOFactory();
        userDAO = mySQLDAOFactory.getUserDAO();
    }

    @Override
    public Page execute(HttpServletRequest request, HttpServletResponse response) {
        long id = Long.parseLong(request.getParameter("id"));
        try {
            User user = userDAO.get(id);
            if (user == null) {
                return new Page("/admin/users?", true);
            }
            if (request.getMethod().equalsIgnoreCase("get")) {
                return setAttributes(request, id, user);
            }
            if (!validation(request, userDAO, id)) {
                return new Page("/admin/edit-user?id=" + id + "&error=true", true);
            }
            userDAO.update(createNewUser(request, user));
            System.out.println(request.getParameter("page"));
            return new Page("/admin/users?page=" + request.getParameter("page"), true);
        } catch (DbException e) {
            LOGGER.error(e);
            return new Page("/admin/users?error=true", true);
        }
    }

    private Page setAttributes(HttpServletRequest request, long id, User user) {
        request.setAttribute("user", user);
        request.setAttribute("id", id);
        return new Page("/WEB-INF/jsp/admin/edit-user.jsp", false);
    }

    private boolean validation(HttpServletRequest request, UserDAO userDAO, long id) throws DbException {
        String email = request.getParameter("email");
        String login = request.getParameter("login");
        String accessLevel = request.getParameter("access-level");
        if ((email.isEmpty() || userDAO.getByEmail(email) != null) && userDAO.getByEmail(email).getId() != id) {
            return false;
        }
        if ((login.isEmpty() || userDAO.getByLogin(login) != null) && userDAO.getByLogin(login).getId() != id) {
            return false;
        }
        return !accessLevel.isEmpty();
    }

    private User createNewUser(HttpServletRequest request, User user) {
        user.setLogin(request.getParameter("login"));
        user.setEmail(request.getParameter("email"));
        user.setAccessLevel(request.getParameter("access-level"));
        return user;
    }
}
