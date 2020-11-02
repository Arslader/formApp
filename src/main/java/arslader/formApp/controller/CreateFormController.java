package arslader.formApp.controller;

import arslader.formApp.entities.Answers;
import arslader.formApp.entities.Forms;
import arslader.formApp.entities.Questions;
import arslader.formApp.entities.Users;
import arslader.formApp.helpers.BidirectionalCreation;
import arslader.formApp.repositories.FormRepo;
import arslader.formApp.repositories.QuestionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.AuthenticatedPrincipal;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/createForm")
@PreAuthorize("hasAuthority('ADMIN')")
public class CreateFormController {

    @Autowired
    private FormRepo formRepo;

 //   private BidirectionalCreation createNewForm = new BidirectionalCreation();

    private String lastFormName = "";

    @GetMapping
    public String createForm() {
       return "/createForm";

    }

    @PostMapping("/forms")
    @ResponseBody
    public Forms createForm(@RequestBody Forms forms) {

//        if(lastFormName.equals(forms.getFormName())) { return forms; }
//        else {lastFormName= forms.getFormName(); }

 //       Forms newForm = createNewForm.createForm(forms);

        List<Questions> questions = forms.getQuestions();
        for (Questions question: questions) {
            question.setForms(forms);
            List<Answers> answers = question.getAnswers();
            for (Answers answer: answers) {
                answer.setQuestions(question);
            }
        }

        return formRepo.save(forms);
    }

}
