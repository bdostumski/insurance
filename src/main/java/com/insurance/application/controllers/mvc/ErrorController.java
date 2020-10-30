package com.insurance.application.controllers.mvc;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

@Controller
public class ErrorController implements org.springframework.boot.web.servlet.error.ErrorController {

    @GetMapping("/error")
    public String showErrorPage(HttpServletRequest request, Model model) {

        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        if (status != null) {

            Integer statusCode = Integer.valueOf(status.toString());
            model.addAttribute("code", statusCode);

            if (statusCode == HttpStatus.NOT_FOUND.value()) {
                model.addAttribute("message", "Not found");
            } else if (statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
                model.addAttribute("message", "Internal server error");
            } else if (statusCode == HttpStatus.BAD_REQUEST.value()) {
                model.addAttribute("message", "Bad Request");
            }

            return "error";
        }

        return "redirect:/";
    }

    @Override
    public String getErrorPath() {
        return null;
    }
}
