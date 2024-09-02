package kr.sparta.khs.delivery.domain.user.repository;

import kr.sparta.khs.delivery.domain.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByUsername(String username);

    /*@Query("""
        SELECT user
          FROM kr.sparta.khs.delivery.domain.user.entity.User user
         WHERE user.name = :name
            OR user.contact = :contact
    """)*/
    Page<User> findByNameStartingWithOrContactStartingWith(String name, String contact, PageRequest pageRequest);
}
