package arslader.formApp.repositories;

import arslader.formApp.entities.Questions;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepo extends JpaRepository<Questions, Long> {
}
