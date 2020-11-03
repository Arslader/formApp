package arslader.formApp.entities;

import arslader.formApp.Views.Views;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonView;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@JsonView(Views.UI.class)
public class Forms {

    public Forms() {

    }

    public Forms(String formName) {
        this.formName=formName;
    }

    public Forms(String formName, List<Questions> questions) {
        this.formName=formName;
        this.questions = questions;
    }

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)

    private long id;

    private String formName;

    @OneToMany(mappedBy = "forms", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference(value="forms")
    private List<Questions> questions = new ArrayList<>();


    public long getId() { return id; }

    public void setId(long id) { this.id = id; }

    public String getFormName() { return formName; }

    public void setFormName(String formName) { this.formName = formName; }

    public List<Questions> getQuestions() { return questions; }

    public void setQuestions(List<Questions> questions) { this.questions = questions; }



}

