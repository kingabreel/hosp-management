package org.proway.student.hospitalsys.repository;

import org.proway.student.hospitalsys.domain.Role;
import org.proway.student.hospitalsys.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    UserDetails findByEmail(String email);
    List<User> findAllByRole(Role role);
}
