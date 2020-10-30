package arslader.formApp.entities;

import arslader.formApp.Views.Views;
import com.fasterxml.jackson.annotation.JsonView;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@JsonView(Views.UI.class)
public class Questions {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private long id;

    private String question;

    private boolean multiple;


    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "question_id")
    private List<Answers> answers;

//    @OneToMany(mappedBy = "questions")
//    private List<Answers> answers;


//    @ManyToOne(fetch = FetchType.EAGER)
//    @JoinColumn(name="forms_id")
//    private Forms form;



    public Questions() {};

    public Questions(String question, List<Answers> answers, boolean multiple) {
        this.question = question;
        this.answers = answers;
        this.multiple = multiple;
    }


    public long getId() { return id; }

    public void setId(long id) { this.id = id; }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public List getAnswers() {
        return answers;
    }

    public void setAnswers(List<Answers> answers) {
        this.answers = answers;
    }

    public boolean isMultiple() { return multiple; }

    public void setMultiple(boolean multiple) { this.multiple = multiple; }

}
