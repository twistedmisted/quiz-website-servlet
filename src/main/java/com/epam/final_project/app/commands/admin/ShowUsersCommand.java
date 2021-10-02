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

import java.util.List;

public class ShowUsersCommand implements Command {

    private static final Logger LOGGER = LogManager.getLogger(ShowUsersCommand.class);

    private static final int NUMBER_USERS_ON_PAGE = 5;

    private final UserDAO userDAO;

    public ShowUsersCommand() {
        MySQLDAOFactory mySQLDAOFactory = new MySQLDAOFactory();
        userDAO = mySQLDAOFactory.getUserDAO();
    }

    @Override
    public Page execute(HttpServletRequest request, HttpServletResponse response) {
        try {
            int page = getPage(request);
            int start = NUMBER_USERS_ON_PAGE * (page - 1);
            int totalNumberOfUsers = userDAO.getNumber();
            int numberOfPages = getNumberOfPages(totalNumberOfUsers);
            List<User> users = userDAO.getByRange(start, NUMBER_USERS_ON_PAGE);
            request.setAttribute("users", users);
            request.setAttribute("currentPage", page);
            request.setAttribute("numberOfPages", numberOfPages);
            return new Page("/WEB-INF/jsp/admin/users.jsp", false);
        } catch (DbException e) {
            LOGGER.error(e);
            return new Page("/admin?error=true", true);
        }
    }

    private int getPage(HttpServletRequest request) {
        if (request.getParameter("page") == null) {
            return 1;
        }
        return Integer.parseInt(request.getParameter("page"));
    }

    private int getNumberOfPages(int totalNumberOfUsers) {
        if (totalNumberOfUsers == 0) {
            return 1;
        }
        if (totalNumberOfUsers % NUMBER_USERS_ON_PAGE == 0) {
            return totalNumberOfUsers / NUMBER_USERS_ON_PAGE;
        } else {
            return totalNumberOfUsers / NUMBER_USERS_ON_PAGE + 1;
        }
    }
}
