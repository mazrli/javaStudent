package student.javastudent.Registration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import student.javastudent.Login.LoginUser;

public class RegistrationController {
    @Autowired
    private RegistrationService registrationService;

    @GetMapping("/registration")
    public String showPage(Model model) {
        model.addAttribute("user", new LoginUser());
        return "registration";
    }

    @GetMapping("/login")
    public String showLoginPage(Model model) {
        return "login";
    }

    @GetMapping("/adminOnly")
    public String showAdminPage(Model model) {
        return "admin_only";
    }

    @PostMapping("/registration/save")
    public String saveUser(LoginUser user, RedirectAttributes ra) {
        registrationService.register( new RegistrationRequest(user.getUsername(), user.getPassword(), user.getEmail()));
        return "redirect:/";
    }

    @GetMapping("/registration/confirm") // http://localhost:8080/registration/confirm?token=<>
    public String confirm(@RequestParam("token") String token) {
        String result = registrationService.confirmToken(token);
        return result;
    }
}
