package arslader.formApp.controller;

import arslader.formApp.Views.Views;
import arslader.formApp.entities.FilledForms;
import arslader.formApp.repositories.FilledFormRepo;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/fillForm")
@PreAuthorize("hasAuthority('ADMIN')")
public class FillFormController {

    @Autowired
    private FilledFormRepo filledFormRepo;

    @GetMapping
    @JsonView(Views.UI.class)
    public String fill() {

        return "fillForm";
    }

    @GetMapping("/forms")
    @ResponseBody
    @JsonView(Views.UI.class)
    public Iterable<FilledForms> getForms() {

        return filledFormRepo.findAll();
    }


}
