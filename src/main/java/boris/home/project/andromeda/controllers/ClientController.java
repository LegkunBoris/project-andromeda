package boris.home.project.andromeda.controllers;

import boris.home.project.andromeda.controllers.dto.ClientDto;
import static boris.home.project.andromeda.utils.ErrorsUtils.redirectToError;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;
import java.util.List;

@AllArgsConstructor
@Controller
@RequestMapping("/clients")
public class ClientController {

  private final JdbcClientDetailsService clientDetailsService;

  private final JdbcClientDetailsService jdbcClientDetailsService;

  @PostMapping("/update")
  public RedirectView updateClient(
      @ModelAttribute("client") ClientDto client,
      BindingResult bindingResult,
      RedirectAttributes redirectAttributes
  ) {
    if (bindingResult.hasErrors()) {
      //errors processing
      final List<ObjectError> allErrors = bindingResult.getAllErrors();
      if (allErrors.isEmpty()) {
        redirectAttributes.addAttribute("text", StringUtils.join(allErrors, ";"));
        return new RedirectView("/errors");
      }
    }
    try {
      jdbcClientDetailsService.loadClientByClientId(client.getClientId());
      jdbcClientDetailsService.updateClientDetails(client);
    } catch (Exception e) {
      redirectAttributes.addAttribute("text", e.toString());
      return new RedirectView("/errors");
    }
    return new RedirectView("/clients");
  }

  @PostMapping("/add")
  public String addClient(
      @ModelAttribute("client") ClientDto client,
      BindingResult bindingResult
  ) {
    if (bindingResult.hasErrors()) {
      //errors processing
      final List<ObjectError> allErrors = bindingResult.getAllErrors();
      if (allErrors.isEmpty()) {
        return redirectToError(allErrors);
      }
    }
    try {
      jdbcClientDetailsService.addClientDetails(client);
    } catch (Exception e) {
      return redirectToError(e);
    }
    return "redirect:/clients";
  }

  @GetMapping("/edit/{id}")
  public String updateClient(
      @PathVariable("id") String id,
      Model model
  ) {
    model.addAttribute("client", jdbcClientDetailsService.loadClientByClientId(id));
    return "/client_operations/edit_client";
  }

  @GetMapping
  public String getClients(Model model) {
    model.addAttribute("clients", clientDetailsService.listClientDetails());
    return "/client_operations/clients";
  }

  @GetMapping("/create")
  public String createClient(Model model) {
    model.addAttribute("client", new ClientDto());
    return "/client_operations/create_client";
  }

  @GetMapping("/delete/{id}")
  public String deleteClient(@PathVariable("id") String id) {
    jdbcClientDetailsService.removeClientDetails(id);
    return "redirect:/clients";
  }
}
