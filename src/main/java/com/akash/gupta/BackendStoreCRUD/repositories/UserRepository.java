package com.akash.gupta.BackendStoreCRUD.repositories;

import com.akash.gupta.BackendStoreCRUD.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

//                                                             id jis type ki hai
public interface UserRepository extends JpaRepository<User, String> {

          Optional<User> findByEmail(String email);
//          iski implementaion runtime me sb iski implemnetaion likh dega
              // he thinks he has to put where clause as it is find
       Optional <User> findByemailAndPassword(String email , String Password);
       List<User> findByNameContaining(String keyword);

}
