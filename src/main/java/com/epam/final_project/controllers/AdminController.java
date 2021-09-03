package com.epam.final_project.controllers;

import com.epam.final_project.dao.DbManager;
import com.epam.final_project.dao.entity.*;
import com.example.main_project.dao.entity.*;
import org.apache.log4j.Logger;

public class AdminController {

    private static final Logger logger = Logger.getLogger(AdminController.class);

    private static AdminController instance;

    private static final UserDAO USER_DAO = DbManager.getInstance().getUserDAO();

    private static final QuestionDAO QUESTION_DAO = DbManager.getInstance().getQuestionDAO();

    private static final QuizDAO QUIZ_DAO = DbManager.getInstance().getQuizDAO();

    private static final VariantsDAO VARIANTS_DAO = DbManager.getInstance().getVariantsDAO();

    private static final AnswersDAO ANSWER_DAO = DbManager.getInstance().getAnswerDAO();

    private AdminController() {
    }

    public static AdminController getInstance() {
        if (instance == null) {
            instance = new AdminController();
        }
        return instance;
    }

//    public void showUsers(HttpServletRequest request, HttpServletResponse response, ServletContext context) throws IOException {
//        final int NUMBER_USERS_ON_PAGE = 5;
//        DbManager dbManager = DbManager.getInstance();
//        UserDAO userDAO = dbManager.getUserDAO();
//        try {
//            int page = Integer.parseInt(request.getParameter("page"));
//            int start = NUMBER_USERS_ON_PAGE * (page - 1);
//            int totalNumberOfUsers = userDAO.getNumber();
//            int numberOfPages = getNumberOfPages(totalNumberOfUsers, NUMBER_USERS_ON_PAGE);
//            List<User> users = userDAO.getByRange(start, NUMBER_USERS_ON_PAGE);
//            request.setAttribute("users", users);
//            request.setAttribute("currentPage", page);
//            request.setAttribute("numberOfPages", numberOfPages);
//            context.getRequestDispatcher("/admin/users").forward(request, response);
//        } catch (SQLException | ServletException e) {
//            e.printStackTrace();
//            logger.error(e.getMessage());
//            response.sendRedirect("/admin?error=true");
//        }
//    }
//
//    private int getNumberOfPages(int totalNumberOfUsers, int numberUsersOnPage) {
//        if (totalNumberOfUsers == 0) {
//            return 1;
//        }
//        if (totalNumberOfUsers % numberUsersOnPage == 0) {
//            return totalNumberOfUsers / numberUsersOnPage;
//        } else {
//            return totalNumberOfUsers / numberUsersOnPage + 1;
//        }
//    }

//    public void editQuiz(HttpServletRequest request, HttpServletResponse response) {
//        long id = Long.parseLong(request.getParameter("id"));
//        try {
//            Quiz quiz = QUIZ_DAO.getByLoginAndPassword(id);
//
//            String name = request.getParameter("name");
//            int time = Integer.parseInt(request.getParameter("time"));
//            String difficulty = request.getParameter("difficulty");
//
//            if (!name.isEmpty() && QUIZ_DAO.getByLoginAndPassword(name) == null) {
//                quiz.setName(name);
//            }
//
//            if (!difficulty.isEmpty()) {
//                quiz.setDifficulty(difficulty);
//            }
//
//            if (time > 0) {
//                quiz.setTime(time);
//            }
//
//            QUIZ_DAO.update(quiz);
//            response.sendRedirect("/admin/quizzes?page=" + request.getParameter("page"));
//        } catch (Exception e) {
//            try {
//                response.sendRedirect("/admin/quizzes/edit?error=true");
//            } catch (IOException ioException) {
//                ioException.printStackTrace();
//            }
//            e.printStackTrace();
//        }
//    }
//
//    public void showQuestions(HttpServletRequest request, HttpServletResponse response) {
//        long id = Integer.parseInt(request.getParameter("id"));
//        try {
//            Quiz quiz = QUIZ_DAO.getByLoginAndPassword(id);
//            List<Question> questions = QUESTION_DAO.getAllByQuizId(quiz.getId());
//            request.getSession().setAttribute("questions", questions);
//            response.sendRedirect("/admin/quizzes/questions?quiz_id=" + id);
//        } catch (Exception throwables) {
//            throwables.printStackTrace();
//        }
//    }

//    public void deleteQuiz(HttpServletRequest request, HttpServletResponse response) {
//        long id = Long.parseLong(request.getParameter("id"));
//        try {
//            Quiz quiz = QUIZ_DAO.getByLoginAndPassword(id);
//            QUIZ_DAO.delete(quiz);
//            response.sendRedirect("/admin/quizzes?page=" + request.getParameter("page"));
//        } catch (Exception e) {
//            try {
//                response.sendRedirect("/admin/quizzes?error=true");
//            } catch (IOException ioException) {
//                ioException.printStackTrace();
//            }
//            e.printStackTrace();
//        }
//    }

//    public void editUser(HttpServletRequest request, HttpServletResponse response) {
//        long id = Long.parseLong(request.getParameter("id"));
//        try {
//            User user = USER_DAO.getByLoginAndPassword(id);
//
//            String email = request.getParameter("email");
//            String login = request.getParameter("login");
//            String accessLevel = request.getParameter("access-level");
//
//            if (!email.isEmpty() && USER_DAO.getByEmail(email) == null) {
//                user.setEmail(email);
//            }
//
//            if (!login.isEmpty() && USER_DAO.getByLogin(login) == null) {
//                user.setLogin(login);
//            }
//
//            if (!accessLevel.isEmpty()) {
//                user.setAccessLevel(accessLevel);
//            }
//
//            USER_DAO.update(user);
//            response.sendRedirect("/admin?page=" + request.getParameter("page"));
//        } catch (Exception e) {
//            try {
//                response.sendRedirect("/admin/edit?error=true");
//            } catch (IOException ioException) {
//                ioException.printStackTrace();
//            }
//            e.printStackTrace();
//        }
//    }

//    public void deleteUser(HttpServletRequest request, HttpServletResponse response) {
//        long id = Long.parseLong(request.getParameter("id"));
//        try {
//            User user = USER_DAO.getByLoginAndPassword(id);
//            USER_DAO.delete(user);
//            response.sendRedirect("/admin?page=" + request.getParameter("page"));
//        } catch (Exception e) {
//            try {
//                response.sendRedirect("/admin?error=true");
//            } catch (IOException ioException) {
//                ioException.printStackTrace();
//            }
//            e.printStackTrace();
//        }
//    }

//    public void blockUser(HttpServletRequest request, HttpServletResponse response) {
//        changeState(request, response, "banned");
//    }
//
//    public void unblockUser(HttpServletRequest request, HttpServletResponse response) {
//        changeState(request, response, "normal");
//    }
//
//    private void changeState(HttpServletRequest request, HttpServletResponse response, String state) {
//        long id = Long.parseLong(request.getParameter("id"));
//        try {
//            User user = USER_DAO.getByLoginAndPassword(id);
//            user.setState(state);
//            USER_DAO.update(user);
//            response.sendRedirect("/admin?page=" + request.getParameter("page"));
//        } catch (Exception e) {
//            try {
//                response.sendRedirect("/admin?error=true");
//            } catch (IOException ioException) {
//                ioException.printStackTrace();
//            }
//            e.printStackTrace();
//        }
//    }

//    public void deleteQuestion(HttpServletRequest request, HttpServletResponse response) {
//        long id = Long.parseLong(request.getParameter("id"));
//        try {
//            QUESTION_DAO.delete(QUESTION_DAO.getByLoginAndPassword(id));
//            response.sendRedirect("/App?action=show-questions&id=" + request.getParameter("quiz_id"));
//        } catch (Exception throwables) {
//            throwables.printStackTrace();
//        }
//    }

//    public void addQuiz(HttpServletRequest request, HttpServletResponse response) {
//        String name = request.getParameter("name");
//        int time = Integer.parseInt(request.getParameter("time"));
//        String difficulty = request.getParameter("difficulty");
//
//        // TODO: переробить, бо зроблено на скору руку
//        try {
//            boolean flag = true;
//            if (name.isEmpty() && QUIZ_DAO.getByLoginAndPassword(name) != null) {
//                flag = false;
//            }
//
//            if (difficulty.isEmpty()) {
//                flag = false;
//            }
//
//            if (time < 0) {
//                flag = false;
//            }
//
//            String url = "";
//            if (flag) {
//                Quiz quiz = new Quiz();
//                quiz.setName(name);
//                quiz.setTime(time);
//                quiz.setDifficulty(difficulty);
//                QUIZ_DAO.insert(quiz);
//                url = "/admin/quizzes?page=" + request.getParameter("page");
//            }
//            response.sendRedirect(url);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

//    public void addQuestion(HttpServletRequest request, HttpServletResponse response) {
//        String prompt = request.getParameter("prompt");
//        List<String> variants = new ArrayList<>();
//        List<Character> answers = new ArrayList<>();
//        for (int i = 0; i < 4; i++) {
//            char letter = (char) ('a' + i);
//            String variant = request.getParameter(letter + "-input");
//            if (!variant.isEmpty()) {
//                variants.add(variant);
//            }
//            String answer = request.getParameter(String.valueOf(letter));
//            if (answer != null) {
//                answers.add(answer.charAt(0));
//            }
//        }
//
//        boolean flag = true;
//        if (prompt.isEmpty()) {
//            flag = false;
//        }
//
//        if (variants.isEmpty()) {
//            flag = false;
//        }
//
//        if (answers.isEmpty()) {
//            flag = false;
//        }
//
//        String url = "/app/admin/add-question.jsp?error=true";
//        if (flag) {
//            Question question = new Question();
//            question.setPrompt(prompt);
//            question.setVariants(variants);
//            question.setAnswers(answers);
//
//            try {
//                QUESTION_DAO.insert(question);
//                long questionId = QUESTION_DAO.getByLoginAndPassword(question.getPrompt()).getId();
//                question.setId(questionId);
//                VARIANTS_DAO.insert(question);
//                ANSWER_DAO.insert(question);
//                long quizId = Long.parseLong(request.getParameter("id"));
//                Quiz quiz = QUIZ_DAO.getByLoginAndPassword(quizId);
//                QUIZ_DAO.setQuestionForQuiz(quiz, question);
//                url = "/admin/quizzes/questions?quiz_id=" + quizId;
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//        }
//
//        try {
//            response.sendRedirect(url);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
}
