package arslader.formApp.controller;

import arslader.formApp.entities.Forms;
import arslader.formApp.entities.Users;
import arslader.formApp.repositories.FormRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.AuthenticatedPrincipal;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@RequestMapping("/createForm")
@PreAuthorize("hasAuthority('ADMIN')")
public class CreateFormController {

    @Autowired
    private FormRepo formRepo;

    private String lastFormName = "";

    @GetMapping
    public String createForm() {
       return "/createForm";

    }

    @PostMapping("/forms")
    @ResponseBody
    public Forms createForm(@RequestBody Forms forms) {

        if(lastFormName.equals(forms.getFormName())) { return forms; }
        else {lastFormName= forms.getFormName(); }


        return formRepo.save(forms);
    }
}
