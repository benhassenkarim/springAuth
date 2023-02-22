package com.scalablescripts.auth.repository;

import com.scalablescripts.auth.model.user1;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepo extends CrudRepository<user1,Long> {
    //select 1  from user1 u where u.email= :email
    Optional<user1> findByEmail(String email);
}
