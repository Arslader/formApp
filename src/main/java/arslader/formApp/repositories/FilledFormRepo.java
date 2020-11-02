package arslader.formApp.repositories;

import arslader.formApp.entities.FilledForms;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FilledFormRepo extends JpaRepository<FilledForms,Long> {

}
