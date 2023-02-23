package com.scalablescripts.auth.repository;

import com.scalablescripts.auth.model.user1;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface UserRepo extends CrudRepository<user1,Long> {
    //select 1  from user1 u where u.email= :email
    Optional<user1> findByEmail(String email);

    @Query("""
            select u.* from user1 u inner join token t on u.id = t.user1
            where u.id = :id and t.refresh_token = :refreshToken and t.expired_at >= :exoiredAt
            """)
    Optional<user1> findByIdAndTokenRefreshTokenAndTokenExpiredAtGreaterThan(Long id, String refreshToken, LocalDateTime expiredAt);
}
