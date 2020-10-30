package arslader.formApp.controller;

import arslader.formApp.entities.Role;
import arslader.formApp.entities.Users;
import arslader.formApp.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Collections;
import java.util.List;

@Controller
public class RegistrationController {

    @Autowired
    private UserRepo userRepo;

    @GetMapping("/registration")
    public String registration() {
        return "registration";
    }

    @PostMapping("/registration")
    public String newUser(Users user) {

        List<Users> userFromDB = userRepo.findAll();

        user.setActive(true);

        if(userFromDB.isEmpty()) {
            user.setRoles(Collections.singleton(Role.ADMIN));
        }
        else{user.setRoles(Collections.singleton(Role.USER));}

        userRepo.save(user);

        return "redirect:/login";
    }

}
