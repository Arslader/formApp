package arslader.formApp.entities;

import arslader.formApp.Views.Views;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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


    @OneToMany(mappedBy = "questions", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Answers> answers = new ArrayList<>();

//    @OneToMany(mappedBy = "questions")
//    private List<Answers> answers;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "forms_id")
    @JsonBackReference
    private Forms forms;



    public Questions() {};

    public Questions(String question, Forms forms) {
        this.question = question;
        this.forms = forms;
    }


    public long getId() { return id; }

    public void setId(long id) { this.id = id; }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public List<Answers> getAnswers() {
        return answers;
    }

    public void setAnswers(List<Answers> answers) {
        this.answers = answers;
    }

    public boolean isMultiple() { return multiple; }

    public void setMultiple(boolean multiple) { this.multiple = multiple; }


}
