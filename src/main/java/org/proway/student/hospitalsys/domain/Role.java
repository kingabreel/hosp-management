package org.proway.student.hospitalsys.domain;

public enum Role {
    ADMIN("admin"),
    DOCTOR("doctor"),
    RECEPTIONIST("receptionist"),
    PATIENT("patient");

    private String role;

    Role(String role){
        this.role = role;
    }

    String getRole(){
        return this.role;
    }
}
