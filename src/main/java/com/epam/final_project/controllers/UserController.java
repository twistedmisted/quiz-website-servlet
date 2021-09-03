package com.epam.final_project.controllers;

import com.epam.final_project.dao.DbManager;
import com.epam.final_project.dao.entity.QuizDAO;
import com.epam.final_project.dao.entity.QuestionDAO;
import com.epam.final_project.dao.entity.UserDAO;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class UserController {

    private static UserController instance;

    private final DbManager dbManager = DbManager.getInstance();

    private final UserDAO userDAO = dbManager.getUserDAO();

    private final QuestionDAO questionDAO = dbManager.getQuestionDAO();

    private final QuizDAO quizDAO = dbManager.getQuizDAO();

    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    ;

    private UserController() {
    }

    public static UserController getInstance() {
        if (instance == null) {
            instance = new UserController();
        }
        return instance;
    }

//    public void startQuiz(HttpServletRequest request, HttpServletResponse response) {
//        long quizId = Long.parseLong(request.getParameter("quiz_id"));
//        try {
//            quizDAO.setUserForQuiz(((User) request.getSession().getAttribute("user")).getId(), quizId);
//            List<Question> questions = questionDAO.getAllByQuizId(quizId);
//            if (!questions.isEmpty()) {
//                request.getSession().setAttribute("quiz_id", quizId);
//                request.getSession().setAttribute("questions", questions);
//                request.getSession().setAttribute("score", 0);
//                int timeForQuiz = quizDAO.getByLoginAndPassword((long) request.getSession().getAttribute("quiz_id")).getTime();
//                long quizFinishAt = System.currentTimeMillis() + (long) timeForQuiz * 60 * 1000;
//                request.getSession().setAttribute("quiz_finish", new Date(quizFinishAt));
//                nextQuestion(request, response);
//            } else {
//                // TODO: запитань нема
//                response.sendRedirect("/quiz.jsp?id=" + quizId);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            try {
//                response.sendRedirect("/quiz.jsp?id=" + quizId);
//            } catch (IOException ioException) {
//                ioException.printStackTrace();
//            }
//        }
//    }
//
//    public void nextQuestion(HttpServletRequest request, HttpServletResponse response) {
//        // TODO: перевірки коли користувач поверенеться назад
//        // TODO: переробить логіку, шоб коли час закінчився, то запитання не враховувалось
//        String requestParameter = request.getParameter("question");
//
//        Date now = new Date();
//        Date end = (Date) request.getSession().getAttribute("quiz_finish");
//
//        int score = 0;
//        int index = 0;
//        if (requestParameter != null) {
//            index = Integer.parseInt(request.getParameter("question")) - 1;
//            score = checkAnswers(request);
//        }
//
//        List<Question> questions = (List<Question>) request.getSession().getAttribute("questions");
//
//        String url = "/question.jsp?question=" + (index + 1);
//        if (index >= questions.size() - 1 || now.compareTo(end) > -1) {
//            long quizId = (long) request.getSession().getAttribute("quiz_id");
//            score = score * 100 / questions.size();
//            try {
//                quizDAO.updateScore(((User) request.getSession().getAttribute("user")).getId(), quizId, score);
//            } catch (SQLException e) {
//                e.printStackTrace();
//
//            }
//            url = "/quiz.jsp?id=" + quizId;
//            request.getSession().removeAttribute("quiz_id");
//            request.getSession().removeAttribute("question");
//            request.getSession().removeAttribute("score");
//        } else {
//            Question question = questions.getByLoginAndPassword(index);
//            request.getSession().setAttribute("question", question);
//            request.getSession().setAttribute("score", score);
//        }
//
//        try {
//            response.sendRedirect(url);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    private int checkAnswers(HttpServletRequest request) {
//        List<Character> userAnswers = new ArrayList<>();
//        List<Character> answers = ((Question) request.getSession().getAttribute("question")).getAnswers();
//        int score = (int) request.getSession().getAttribute("score");
//        for (int i = 0; i < 4; i++) {
//            char letter = (char) ('a' + i);
//            String answer = request.getParameter(String.valueOf(letter));
//            if (answer != null) {
//                userAnswers.add(answer.charAt(0));
//            }
//        }
//
//        Collections.sort(userAnswers);
//        Collections.sort(answers);
//
//        if (userAnswers.equals(answers)) {
//            return ++score;
//        }
//        return score;
//    }
//
//    public void logout(HttpServletRequest request, HttpServletResponse response) {
//        request.getSession().invalidate();
//        try {
//            response.sendRedirect("/");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

}
