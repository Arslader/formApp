package arslader.formApp.controller;

import arslader.formApp.entities.Answers;
import arslader.formApp.entities.Forms;
import arslader.formApp.entities.Questions;
import arslader.formApp.repositories.FormRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

        if(lastFormName.equals(forms.getFormName())) { return forms; }
        else {lastFormName= forms.getFormName(); }

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
