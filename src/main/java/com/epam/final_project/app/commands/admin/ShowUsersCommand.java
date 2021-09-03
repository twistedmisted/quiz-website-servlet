package com.epam.final_project.app.commands.admin;

import com.epam.final_project.app.Page;
import com.epam.final_project.app.commands.Command;
import com.epam.final_project.dao.DbManager;
import com.epam.final_project.dao.entity.UserDAO;
import com.epam.final_project.exception.DbException;
import com.epam.final_project.dao.model.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

import java.util.List;

public class ShowUsersCommand implements Command {

    private static final Logger LOGGER = Logger.getLogger(ShowUsersCommand.class);

    @Override
    public Page execute(HttpServletRequest request, HttpServletResponse response) {
        final int NUMBER_USERS_ON_PAGE = 5;
        DbManager dbManager = DbManager.getInstance();
        UserDAO userDAO = dbManager.getUserDAO();
        try {
            int page = getPage(request);
            int start = NUMBER_USERS_ON_PAGE * (page - 1);
            int totalNumberOfUsers = userDAO.getNumber();
            int numberOfPages = getNumberOfPages(totalNumberOfUsers, NUMBER_USERS_ON_PAGE);
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

    private int getNumberOfPages(int totalNumberOfUsers, int numberUsersOnPage) {
        if (totalNumberOfUsers == 0) {
            return 1;
        }
        if (totalNumberOfUsers % numberUsersOnPage == 0) {
            return totalNumberOfUsers / numberUsersOnPage;
        } else {
            return totalNumberOfUsers / numberUsersOnPage + 1;
        }
    }
}
