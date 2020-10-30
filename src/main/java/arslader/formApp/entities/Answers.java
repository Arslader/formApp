package arslader.formApp.entities;

import arslader.formApp.Views.Views;
import com.fasterxml.jackson.annotation.JsonView;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@JsonView(Views.UI.class)
public class Answers {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private long id;

    private String answer;

    private boolean filled;

    public Answers() {};

    public Answers(String answer, boolean filled) {
        this.answer = answer;
        this.filled = filled;
    }


//    @ManyToOne(fetch = FetchType.EAGER)
//    @JoinColumn(name = "questions_id")
//    private Questions question;



    public long getId() { return id; }

    public void setId(long id) { this.id = id; }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public boolean isFilled() { return filled; }

    public void setFilled(boolean filled) { this.filled = filled; }



}
