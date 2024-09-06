package org.proway.student.hospitalsys.service;

import org.proway.student.hospitalsys.domain.Role;
import org.proway.student.hospitalsys.domain.User;
import org.proway.student.hospitalsys.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<User> getDoctors(){
        return userRepository.findAllByRole(Role.DOCTOR);
    }

    public List<User> getReceptionists(){
        return userRepository.findAllByRole(Role.RECEPTIONIST);
    }

    public List<User> getPatients(){
        return userRepository.findAllByRole(Role.PATIENT);
    }

    public List<User> getUsers(){
        return userRepository.findAll();
    }
}
