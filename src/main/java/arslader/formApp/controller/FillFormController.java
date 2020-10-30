package arslader.formApp.controller;

import arslader.formApp.Views.Views;
import arslader.formApp.entities.Forms;
import arslader.formApp.entities.Questions;
import arslader.formApp.repositories.FormRepo;
import arslader.formApp.repositories.QuestionRepo;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Controller
@RequestMapping("/fillForm")
@PreAuthorize("hasAuthority('ADMIN')")
public class FillFormController {

    @Autowired
    private FormRepo formRepo;

    @GetMapping
    public String fill() {

        return "fillForm";
    }

    @GetMapping("/forms")
    @ResponseBody
    @JsonView(Views.UI.class)
    public Iterable<Forms> getForms() {

        return formRepo.findAll();
    }


}
