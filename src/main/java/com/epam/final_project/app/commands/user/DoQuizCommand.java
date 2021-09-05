package com.epam.final_project.app.commands.user;

import com.epam.final_project.app.Page;
import com.epam.final_project.app.commands.Command;
import com.epam.final_project.dao.DbManager;
import com.epam.final_project.dao.entity.QuestionDAO;
import com.epam.final_project.dao.entity.QuizDAO;
import com.epam.final_project.dao.model.Question;
import com.epam.final_project.dao.model.Quiz;
import com.epam.final_project.dao.model.User;
import com.epam.final_project.exception.DbException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class DoQuizCommand implements Command {

    private static final Logger LOGGER = LogManager.getLogger(DoQuizCommand.class);

    private final DbManager dbManager = DbManager.getInstance();

    private final QuizDAO quizDAO = dbManager.getQuizDAO();

    private final QuestionDAO questionDAO = dbManager.getQuestionDAO();

    private User user;
    private Quiz quiz;
    private List<Question> questions;

    @Override
    public Page execute(HttpServletRequest request, HttpServletResponse response) {
        long quizId = Long.parseLong(request.getParameter("quiz_id"));
        try {
            if (request.getSession().getAttribute("questions") == null) {
                initParams(request, quizId);
            }
            return getQuestion(request, response, quizId);
        } catch (Exception e) {
            return new Page("/app/quiz?id=" + quizId, true);
        }
    }

    private Page getQuestion(HttpServletRequest request, HttpServletResponse response, long quizId) throws DbException {
        int index = Integer.parseInt(request.getParameter("question")) - 1;
        if (index > 0) {
            checkAnswerAndUpdateScore(request);
        }
        if (checkTime(request) || checkIndex(questions.size(), index)) {
            updateScore(request);
            clearSession(request);
            return new Page("/app/quiz?id=" + quizId, true);
        }
        Question question = questions.get(index);
        request.getSession().setAttribute("question", question);
        return new Page("/WEB-INF/jsp/app/question.jsp", false);
    }

    private void updateScore(HttpServletRequest request) throws DbException {
        int score = 0;
        if (request.getSession().getAttribute("score") != null) {
            score = (int) request.getSession().getAttribute("score");
            score = score * 100 / questions.size();
            quizDAO.updateScore(user.getId(), quiz.getId(), score);
        }
    }

    private void checkAnswerAndUpdateScore(HttpServletRequest request) {
        List<Character> userAnswers = new ArrayList<>();
        List<Character> answers = ((Question) request.getSession().getAttribute("question")).getAnswers();
        int score = (int) request.getSession().getAttribute("score");
        for (int i = 0; i < 4; i++) {
            char letter = (char) ('a' + i);
            String answer = request.getParameter(String.valueOf(letter));
            if (answer != null) {
                userAnswers.add(answer.charAt(0));
            }
        }

        Collections.sort(userAnswers);
        Collections.sort(answers);

        if (userAnswers.equals(answers)) {
            score++;
        }
        request.getSession().setAttribute("score", score);
    }

    private void initParams(HttpServletRequest request, long quizId) throws Exception {
        user = (User) request.getSession().getAttribute("user");
        try {
            quiz = quizDAO.get(quizId);
            quizDAO.setUserForQuiz(user.getId(), quizId);
            questions = questionDAO.getAllByQuizId(quizId);
            if (questions.isEmpty()) {
                // TODO: add page when questions is empty
            }
            setOptionsToSession(request);
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            throw new Exception("Error set questions");
        }
    }

    private boolean checkIndex(int size, int index) {
        return index >= size;
    }

    private boolean checkTime(HttpServletRequest request) {
        Date timeNow = new Date();
        Date quizEndTime = (Date) request.getSession().getAttribute("quiz_finish");
        return timeNow.compareTo(quizEndTime) > -1;
    }

    private void setOptionsToSession(HttpServletRequest request) throws SQLException {
        request.getSession().setAttribute("quiz_id", quiz.getId());
        request.getSession().setAttribute("questions", questions);
        request.getSession().setAttribute("score", 0);
        int timeForQuiz = quiz.getTime();
        long quizFinishAt = System.currentTimeMillis() + (long) timeForQuiz * 60 * 1000;
        request.getSession().setAttribute("quiz_finish", new Date(quizFinishAt));
    }

    private void clearSession(HttpServletRequest request) {
        request.getSession().removeAttribute("quiz_id");
        request.getSession().removeAttribute("question");
        request.getSession().removeAttribute("questions");
        request.getSession().removeAttribute("score");
        request.getSession().removeAttribute("quiz_finish");
    }
}
