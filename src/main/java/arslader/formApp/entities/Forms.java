package arslader.formApp.entities;

import arslader.formApp.Views.Views;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonView;
import com.sun.istack.NotNull;

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

    public Forms(String formName, List<Questions> questions, Users author) {
        this.formName=formName;
        this.questions = questions;
        this.author = author;
    }

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)

    private long id;

    private String formName;

    @OneToMany(mappedBy = "forms", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Questions> questions = new ArrayList<>();

//    @OneToMany(mappedBy = "form", cascade=CascadeType.ALL, orphanRemoval = true)
//    private List<Questions> questions = new ArrayList<>();


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="user_id")
    private Users author;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFormName() {
        return formName;
    }

    public void setFormName(String formName) {
        this.formName = formName;
    }

    public List<Questions> getQuestions() { return questions; }

    public void setQuestions(List<Questions> questions) { this.questions = questions; }

    public Users getAuthor() { return author; }

    public void setAuthor(Users author) { this.author = author; }


}

