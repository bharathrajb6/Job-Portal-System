package com.example.user_service.repo;

import com.example.user_service.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

    /**
     * This method is used to get the user by username
     *
     * @param username
     * @return
     */
    Optional<User> findByUsername(String username);

    /**
     * This method is used to get the user by email
     *
     * @param email
     * @return
     */
    Optional<User> findByEmail(String email);

    /**
     * This method is used to get the user by contact number
     *
     * @param contactNumber
     * @return
     */
    Optional<User> findByContactNumber(String contactNumber);

    /**
     * This method is used to update the user details
     *
     * @param firstName
     * @param lastName
     * @param email
     * @param contactNumber
     * @param username
     */
    @Modifying
    @Transactional
    @Query("UPDATE User u SET u.firstName=?1,u.lastName=?2,u.email=?3,u.contactNumber=?4 where u.username=?5")
    void updateUserDetails(String firstName, String lastName, String email, String contactNumber, String username);

    /**
     * This method is used to update the password
     *
     * @param password
     * @param username
     */
    @Modifying
    @Transactional
    @Query("UPDATE User u SET u.password = ?1 where u.username = ?2")
    void updatePassword(String password, String username);
}
