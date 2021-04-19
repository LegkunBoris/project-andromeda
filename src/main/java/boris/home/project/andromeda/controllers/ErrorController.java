package boris.home.project.andromeda.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/errors")
public class ErrorController {

  @GetMapping("/403")
  public String error403() {
    return "/error/403";
  }

  @GetMapping
  public String error(@RequestParam String text, Model model) {
    model.addAttribute("error", text);
    return "/error/text_error";
  }
}
