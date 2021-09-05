package com.epam.final_project.app.commands.admin;

import com.epam.final_project.app.Page;
import com.epam.final_project.app.commands.Command;
import com.epam.final_project.dao.DbManager;
import com.epam.final_project.dao.entity.UserDAO;
import com.epam.final_project.dao.model.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class UnblockUserCommand implements Command {

    private static final Logger LOGGER = LogManager.getLogger(UnblockUserCommand.class);

    @Override
    public Page execute(HttpServletRequest request, HttpServletResponse response) {
        long id = Long.parseLong(request.getParameter("id"));
        UserDAO userDAO = DbManager.getInstance().getUserDAO();
        try {
            User user = userDAO.get(id);
            user.setState("normal");
            userDAO.update(user);
            return new Page("/admin/users?page=" + request.getParameter("page"), true);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            return new Page("/admin/users?error=true", true);
        }
    }
}
