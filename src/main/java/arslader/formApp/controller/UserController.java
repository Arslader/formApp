package arslader.formApp.controller;

import arslader.formApp.Views.Views;
import arslader.formApp.entities.Users;
import arslader.formApp.repositories.UserRepo;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("userEdit")
@PreAuthorize("hasAuthority('ADMIN')")
public class UserController {

    @Autowired
    private UserRepo userRepo;

    @GetMapping
    public String users() {
        return "userEdit";
    }

    @GetMapping("forms")
    @ResponseBody
    @JsonView(Views.UI.class)
    public Iterable<Users> usersList() {

        return userRepo.findAll();
    }

    @PutMapping("forms")
    @ResponseBody
    @JsonView(Views.UI.class)
    public Iterable<Users> submitRole(@RequestBody Iterable<Users> users) {


        for (Users user: users) {
            Users userFromRepo=userRepo.getOne(user.getId());
            userFromRepo.setRoles(user.getRoles());
            userRepo.save(userFromRepo);
        }

        return users;
    }
}
