package arslader.formApp.controller;

import arslader.formApp.Views.Views;
import arslader.formApp.entities.Forms;
import arslader.formApp.entities.Questions;
import arslader.formApp.entities.Users;
import arslader.formApp.repositories.FormRepo;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/viewForm")
public class ViewFormController {

    @Autowired
    private FormRepo formRepo;

    @GetMapping
    public String viewForm() {

        return "/viewForm";
    }

    @GetMapping("/forms")
    @ResponseBody
    @JsonView(Views.UI.class)
    public Iterable<Forms> getForm() {



//        for (Forms form: forms)  {
//            form.setAuthor(null);
//        }

        return formRepo.findAll();
    }


    @PutMapping("/forms")
    @ResponseBody
    @JsonView(Views.UI.class)
    public void updateForm(@RequestBody Iterable<Forms> forms, @AuthenticationPrincipal Users user) {

        for (Forms form: forms) {
                if(form.getAuthor()==null || form.getAuthor().getUsername().equals(user.getUsername())) {
                    form.setAuthor(user);
                    formRepo.save(form);
                }
                else{
                    Forms formClone = new Forms(form.getFormName(),
                                                form.getQuestions(),
                                                form.getAuthor());
                    formClone.setAuthor(user);
                    formRepo.save(formClone);
                }
        }
    }


}
