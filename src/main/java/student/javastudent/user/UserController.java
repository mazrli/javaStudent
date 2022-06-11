package student.javastudent.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;


@Controller
public class UserController {
    @Autowired
    private UserServier servier;

    @GetMapping("/students")
    public String showUserList(Model model){
        List<User> listUsers = servier.listAll();
        model.addAttribute("listUsers", listUsers);
        return "students";
    }

    @GetMapping("/students/new")
    public String showNewForm(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("pageType", "New");
        model.addAttribute("new","");
        return "students_form";
    }


    @GetMapping("/students/edit/{id}")
    public String showEditForm(@PathVariable("id") Integer id, Model model,
                               RedirectAttributes ra) {
        try {
            model.addAttribute("user", servier.findUserById(id));
            model.addAttribute("pageType", "Edit");
            model.addAttribute("edit","");
        }
        catch (NoSuchUserError e) {
            ra.addFlashAttribute("message", "There is no user " + id + ".");
            return "redirect:/users";
        }
        return "students_form";
    }
    @GetMapping("/students/delete/{id}")
    public String deleteUser(@PathVariable("id") Integer id, Model model,
                             RedirectAttributes ra) {
        try {
            servier.deleteUser(id);
            ra.addFlashAttribute("message", "User " + id + " has been deleted");
        }
        catch (NoSuchUserError e) {
            ra.addFlashAttribute("message", "There is no user " + id + ".");
        }
        return "redirect:/students";
    }


    @PostMapping("/students/save")
    public String saveUser(User user, RedirectAttributes ra) {
        servier.save(user);
        ra.addFlashAttribute("message",user.getName() + " was successfully saved!");
        return "redirect:/students";
    }

}
