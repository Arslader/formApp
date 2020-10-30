package arslader.formApp.repositories;

import arslader.formApp.entities.Forms;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FormRepo extends JpaRepository<Forms, Long> {
}
