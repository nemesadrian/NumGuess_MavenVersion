package org.fasttrackit.dev.lesson1.numgenerator.servlet;

import org.fasttrackit.dev.lesson1.numgenerator.NumGeneratorBusinessLogic;
import org.fasttrackit.dev.lesson1.numgenerator.SendMail;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by condor on 29/11/14.
 * FastTrackIT, 2015
 */


/* didactic purposes

Some items are intentionally not optimized or not coded in the right way

FastTrackIT 2015

*/

public class NumGenServlet extends HttpServlet {

    private static final String REQUEST_PARAM_RESTARTGAME = "requestRestartGame";
    private static final String REQUEST_PARAM_GUESSNUMBER = "requestGuessNumber";
    private static final String SESSION_KEY_RESTART = "_sessionKey_restart";
    private static final String SESSION_KEY_NUMBER_GENERATOR_BUSINESS_LOGIC = "_sessionKey_NumberGeneratorBusinessLogic";
    private static final String VALUE_INIT = "1";

    //json


    public void service(HttpServletRequest request, HttpServletResponse response) {

        boolean success = false;
        String hint = "";
        int numar = -1;
        int nrGuesses = -1;
        int time = 0;
        HttpSession session = request.getSession(true);

        String requestRestartGame = request.getParameter(REQUEST_PARAM_RESTARTGAME);
        if (requestRestartGame != null && requestRestartGame.equals(VALUE_INIT)) {
            session.setAttribute(SESSION_KEY_RESTART, VALUE_INIT);
        }

        NumGeneratorBusinessLogic nbl;
        if ((session.getAttribute(SESSION_KEY_RESTART) != null) && (session.getAttribute(SESSION_KEY_RESTART).equals(VALUE_INIT))) {
            nbl = new NumGeneratorBusinessLogic();
            session.setAttribute(SESSION_KEY_NUMBER_GENERATOR_BUSINESS_LOGIC, nbl);
            session.setAttribute(SESSION_KEY_RESTART, null);
        } else {
            nbl = (NumGeneratorBusinessLogic) session.getAttribute(SESSION_KEY_NUMBER_GENERATOR_BUSINESS_LOGIC);
            if (nbl == null) {
                nbl = new NumGeneratorBusinessLogic();
                session.setAttribute(SESSION_KEY_NUMBER_GENERATOR_BUSINESS_LOGIC, nbl);
            }
        }

        String jsonResponse;

        if (requestRestartGame != null && requestRestartGame.equals(VALUE_INIT)) {
            jsonResponse = "{\"keyRestartGame\":\"restartOk\"}";
            returnJsonResponse(response, jsonResponse);

        } else {

            String requestGuessNumber = request.getParameter(REQUEST_PARAM_GUESSNUMBER);
            int iGuessNumber = 0;
            boolean isAvalidNumber = true;
            try {
                iGuessNumber = Integer.parseInt(requestGuessNumber);
                if(iGuessNumber < nbl.MIN_NUMBER || iGuessNumber > nbl.MAX_NUMBER) isAvalidNumber = false;
            } catch (NumberFormatException e) {
                isAvalidNumber = false;
            }

            if (isAvalidNumber) {
                success = nbl.determineGuess(iGuessNumber);
                hint = nbl.getHint();
                numar = nbl.getNumber();
                nrGuesses = nbl.getNumGuesses();
                time = nbl.getTime();

                jsonResponse = "{\"keySuccess\":\"" + success + "\", \"keyHint\":\"" + hint + "\", \"keyNrGuesses\":\"" + nrGuesses + "\", \"keyTime\":\"" + time + "\"}";

            } else {
                jsonResponse = "{\"keyError\":\"WRONGNUMBERFORMAT\"}";
            }

            returnJsonResponse(response, jsonResponse);
            if (success) try {
                SendMail.Send("cherryteamfast", "FastTrackIT2", "flaviumesesan@gmail.com",
                        "Congratulation You Won!!!",
                        "Ai castigat!\n Numarul castigator este: " + numar +
                                "\nAi ghicit dupa " + nrGuesses + " incercari." +
                                "\nIncearca sa scoti un timp sub " + time);
            } catch (MessagingException e) {
                e.printStackTrace();
            }
        }
    }

    /**/
    private void returnJsonResponse(HttpServletResponse response, String jsonResponse) {
        response.setContentType("application/json");
        PrintWriter pr = null;
        try {
            pr = response.getWriter();
        } catch (IOException e) {
            e.printStackTrace();
        }
        assert pr != null;
        pr.write(jsonResponse);
        pr.close();
    }
}
