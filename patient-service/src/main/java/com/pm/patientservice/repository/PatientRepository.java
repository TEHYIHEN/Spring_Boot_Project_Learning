package com.pm.patientservice.repository;

import com.pm.patientservice.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PatientRepository extends JpaRepository<Patient, UUID> {

    boolean existsByEmail(String email);
    //JPA
    // @Query("SELECT CASE WHEN COUNT(p) > 0 THEN true ELSE false END FROM Patient p WHERE p.email = :email AND p.id != :id")
    //boolean existsByEmailAndIdNot(@Param("email") String email, @Param("id") UUID id);
    boolean existsByEmailAndIdNot(String email, UUID id);
}

//For Example  existsByEmailAndIdNot
//const exists = await prisma.user.findFirst({
//  where: {
//    email,
//    id: {
//      not: id,
//    },
//  },
//}) !== null;