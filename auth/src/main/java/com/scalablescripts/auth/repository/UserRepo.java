package com.scalablescripts.auth.repository;

import com.scalablescripts.auth.model.user1;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends CrudRepository<user1,Long> {
}
