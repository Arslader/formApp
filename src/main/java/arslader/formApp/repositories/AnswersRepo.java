package arslader.formApp.repositories;

import arslader.formApp.entities.Answers;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnswersRepo extends JpaRepository<Answers, Long> {
}
