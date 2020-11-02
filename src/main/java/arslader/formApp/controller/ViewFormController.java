package arslader.formApp.controller;

import arslader.formApp.Views.Views;
import arslader.formApp.entities.*;
import arslader.formApp.helpers.BidirectionalCreation;
import arslader.formApp.repositories.FilledFormRepo;
import arslader.formApp.repositories.FormRepo;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static javafx.scene.input.KeyCode.L;

@Controller
@RequestMapping("/viewForm")
public class ViewFormController {

    @Autowired
    private FormRepo formRepo;

    @Autowired
    private FilledFormRepo filledFormRepo;

    private BidirectionalCreation createNewForm = new BidirectionalCreation();


    @GetMapping
    public String viewForm() {

        return "/viewForm";
    }

    @GetMapping("/forms")
    @ResponseBody
    @JsonView(Views.UI.class)
    public Iterable<Forms> getForm() {


        return formRepo.findAll();
    }


    @PutMapping("/forms")
    @ResponseBody
    @JsonView(Views.UI.class)
    public FilledForms updateForm(@RequestBody FilledForms filledForm, @AuthenticationPrincipal Users user) {

                    List<FilledForms> filledFormsFromDb= filledFormRepo.findAll();

                    Iterable<Forms> formsFromDb= formRepo.findAll();
                    Forms form = formRepo.getOne(filledForm.getId());

                    for (Forms formFromDb: formsFromDb) {
                        if(filledForm.getFormName().equals(formFromDb.getFormName())) form = formFromDb;
                    }

                    if(filledFormsFromDb.isEmpty()){
                        List<Questions> questions = filledForm.getQuestions();
                        for (Questions question: questions) {
                            question.setFilledForms(filledForm);
                            question.setForms(form);
                            List<Answers> answers = question.getAnswers();
                            for (Answers answer: answers) {
                                answer.setQuestions(question);
                            }
                        }
                        filledForm.setAuthor(user);
                        return filledFormRepo.save(filledForm);
                    }

                    for (FilledForms fromDb: filledFormsFromDb) {
                        if(fromDb.getFormName().equals(filledForm.getFormName()) &&
                                fromDb.getAuthor().getUsername().equals(user.getUsername())) {

                            List<Questions> questions = filledForm.getQuestions();
                            for (Questions question: questions) {
                                question.setFilledForms(fromDb);
                                question.setForms(form);
                                List<Answers> answers = question.getAnswers();
                                for (Answers answer: answers) {
                                    answer.setQuestions(question);
                                }
                            }
                            fromDb.setAuthor(user);
                            return filledFormRepo.save(fromDb);
                        }
                    }

                    FilledForms formClone = createNewForm.createForm(filledForm);
                    formClone.setAuthor(user);
                    return filledFormRepo.save(formClone);




//                if(form.getAuthor()==null || form.getAuthor().getUsername().equals(user.getUsername())) {
//                    form.setAuthor(user);
//                    formRepo.save(form);
//                }
//                else{
//                    Forms formClone = createNewForm.createForm(form);
//                    formClone.setAuthor(user);
//                    formRepo.save(formClone);
//                }
        }

}
