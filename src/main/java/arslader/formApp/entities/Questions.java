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



    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "forms_id")
    @JsonBackReference(value="forms")
    private Forms forms;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "filledForms_id")
    @JsonBackReference(value="filledForms")
    private FilledForms filledForms;


    public Questions() {};

    public Questions(String question, boolean multiple, FilledForms forms) {
        this.question = question;
        this.multiple = multiple;
        this.filledForms = forms;

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

    public Forms getForms() { return forms; }

    public void setForms(Forms forms) { this.forms = forms; }

    public FilledForms getFilledForms() { return filledForms; }

    public void setFilledForms(FilledForms filledForms) { this.filledForms = filledForms; }


}
