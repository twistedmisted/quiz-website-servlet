package com.epam.final_project.app.commands.admin;

import com.epam.final_project.app.Page;
import com.epam.final_project.app.commands.Command;
import com.epam.final_project.dao.DbManager;
import com.epam.final_project.dao.entity.AnswersDAO;
import com.epam.final_project.dao.entity.QuestionDAO;
import com.epam.final_project.dao.entity.QuizDAO;
import com.epam.final_project.dao.entity.VariantsDAO;
import com.epam.final_project.dao.model.Quiz;
import com.epam.final_project.exception.DbException;
import com.epam.final_project.dao.model.Question;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class AddQuestionCommand implements Command {

    private static final Logger LOGGER = Logger.getLogger(AddQuestionCommand.class);

    @Override
    public Page execute(HttpServletRequest request, HttpServletResponse response) {
        if (request.getMethod().equalsIgnoreCase("getByLoginAndPassword")) {
            return new Page("/WEB-INF/jsp/admin/add-question.jsp", false);
        }
        DbManager dbManager = DbManager.getInstance();
        QuestionDAO questionDAO = dbManager.getQuestionDAO();
        VariantsDAO variantsDAO = dbManager.getVariantsDAO();
        AnswersDAO answersDAO = dbManager.getAnswerDAO();
        QuizDAO quizDAO = dbManager.getQuizDAO();
        String prompt = request.getParameter("prompt");
        List<String> variants = new ArrayList<>();
        List<Character> answers = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            char letter = (char) ('a' + i);
            String variant = request.getParameter(letter + "-input");
            if (!variant.isEmpty()) {
                variants.add(variant);
            }
            String answer = request.getParameter(String.valueOf(letter));
            if (answer != null) {
                answers.add(answer.charAt(0));
            }
        }
        if (!validateValues(prompt, variants, answers)) {
            return new Page("/admin/add-question?id=" + request.getParameter("id") + "&error=true", true);
        }
        try {
            Question question = Question.createQuestion(prompt, variants, answers);
            questionDAO.insert(question);
            long questionId = questionDAO.get(question.getPrompt()).getId();
            question.setId(questionId);
            variantsDAO.insert(question);
            answersDAO.insert(question);
            long quizId = Long.parseLong(request.getParameter("id"));
            Quiz quiz = quizDAO.get(quizId);
            quizDAO.setQuestionForQuiz(quiz, question);
            System.out.println("HERE");
            return new Page("/admin/quizzes/questions?id=" + quizId, true);
        } catch (DbException e) {
            LOGGER.error(e);
            return new Page("/admin/add-question?id=" + request.getParameter("id") + "&error=true", true);
        }
    }

    private boolean validateValues(String prompt, List<String> variants, List<Character> answers) {
        if (prompt.isEmpty()) {
            return false;
        }

        if (variants.isEmpty()) {
            return false;
        }

        return !answers.isEmpty();
    }
}
