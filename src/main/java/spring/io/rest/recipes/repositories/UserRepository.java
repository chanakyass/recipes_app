package spring.io.rest.recipes.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import spring.io.rest.recipes.models.entities.User;

public interface UserRepository extends JpaRepository<User, Long> {

}
