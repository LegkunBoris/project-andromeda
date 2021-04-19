package boris.home.project.andromeda.controllers;

import boris.home.project.andromeda.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@AllArgsConstructor
@Controller
public class DefaultController {

  private final UserRepository userRepository;

  @GetMapping("/")
  public String home1() {
    return "/home";
  }

  @GetMapping("/home")
  public String home() {
    return "/home";
  }

  @GetMapping("/admin")
  public String admin() {
    return "/admin";
  }

  @GetMapping("/user")
  public String user() {
    return "/user";
  }

  @GetMapping("/users")
  public String users(Model model) {
    model.addAttribute("users", userRepository.findAll());
    return "/users";
  }

  @GetMapping("/about")
  public String about() {
    return "/about";
  }

  @GetMapping("/login")
  public String login() {
    return "/login";
  }

}