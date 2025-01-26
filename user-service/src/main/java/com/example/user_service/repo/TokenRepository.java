package com.example.user_service.repo;

import com.example.user_service.model.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TokenRepository extends JpaRepository<Token, String> {

    /**
     * This method is used to get all the tokens by username
     *
     * @param username
     * @return
     */
    @Query("""
            select t from Token t inner join User u on t.user.username= u.username where t.user.username=?1 and t.isLoggedOut = false
            """)
    List<Token> findAllTokens(String username);

    /**
     * This method is used to get the token by jwt token
     *
     * @param token
     * @return
     */
    @Query("""
            select t from Token t where t.jwtToken = ?1
            """)
    Token findJWTToken(String token);

}
