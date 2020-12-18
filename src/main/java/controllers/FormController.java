package controllers;

import model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import repo.UserRepository;
import services.IUserService;
import services.UserServiceImpl;

import javax.validation.Valid;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Controller
public class FormController {
    @Autowired
    IUserService userService;

    @GetMapping("/users")
    public ModelAndView userList(){
        List<User> userList = userService.findAll();
        ModelAndView modelAndView = new ModelAndView("list");
        modelAndView.addObject("userList",userList);
        return modelAndView;
    }

    @GetMapping("/create")
    public ModelAndView showCreateForm(){
        ModelAndView modelAndView = new ModelAndView("create");
        modelAndView.addObject("user", new User());
        return modelAndView;
    }

    @PostMapping("/create")
    public ModelAndView saveCustomer(@Valid @ModelAttribute("user") User user, BindingResult bindingResult){
        new User().validate(user, bindingResult);
        if (bindingResult.hasErrors()){
            ModelAndView modelAndView = new ModelAndView("create");
            return modelAndView;
        }
        userService.save(user);
        ModelAndView modelAndView = new ModelAndView("create");
        modelAndView.addObject("user", new User());
        return userList();
    }


}
