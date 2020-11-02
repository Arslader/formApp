package arslader.formApp.helpers;

import arslader.formApp.entities.Answers;
import arslader.formApp.entities.FilledForms;
import arslader.formApp.entities.Forms;
import arslader.formApp.entities.Questions;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.List;

public class BidirectionalCreation {

    public BidirectionalCreation() {}

    public FilledForms createForm(FilledForms forms) {

        FilledForms newForm = new FilledForms(forms.getFormName());

        List<Questions> questions = new ArrayList<>();

        List<Answers> answers = new ArrayList<>();


        for ( int i=0; i < forms.getQuestions().size(); i++) {

            Questions question = new Questions(forms.getQuestions().get(i).getQuestion(),
                    forms.getQuestions().get(i).isMultiple(), newForm);
                 //   question.setForms(form);

            for ( int j=0; j < forms.getQuestions().get(i).getAnswers().size(); j++) {

                Answers answer = new Answers(forms.getQuestions().get(i).getAnswers().get(j).getAnswer(), question,
                        forms.getQuestions().get(i).getAnswers().get(j).isFilled());

                answers.add(answer);

            }
            question.setAnswers(answers);
            questions.add(question);
        }


        newForm.setQuestions(questions);

        return newForm;

    }
}
