package com.market.coursework.controller;

import com.market.coursework.security.AuthService;
import com.market.coursework.security.dto.LoginRequest;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService service;

    @PostMapping("/sign-in")
    public String authenticate(@Valid @ModelAttribute LoginRequest request) {
        service.authenticateRequest(request);

        return "redirect:/v1/items";
    }

    @GetMapping("/login")
    public String showAddItemForm(Model model) {
        model.addAttribute("login", new LoginRequest());
        return "login";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {
        // Perform any necessary logout actions, such as clearing session attributes
        request.getSession().invalidate();

        // Redirect to a login page or any other desired destination
        return "redirect:/v1/items";
    }
}
