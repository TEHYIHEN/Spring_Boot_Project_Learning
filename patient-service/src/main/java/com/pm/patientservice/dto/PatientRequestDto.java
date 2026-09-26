package com.pm.patientservice.dto;

import com.pm.patientservice.dto.validators.CreatePatientValidationGroup;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class PatientRequestDto {
    //
    //Take a note
    //We can use public record PatientRequestDTO
    //see example at bottom
    //
    @NotBlank(message = "Name is required")
    @Size(max = 100, message = "Name cannot exceed 100 characters")
    private String name;

    @NotBlank(message = "Email is required")
    @Email(message = "Email should be valid")
    private String email;

    @NotBlank(message = "Address is required")
    private String address;

    @NotBlank(message = "Date of birth is required")
    private String dateOfBirth;

    @NotBlank(groups = CreatePatientValidationGroup.class ,message = "Registered date is required")
    private String registeredDate;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getRegisteredDate() {
        return registeredDate;
    }

    public void setRegisteredDate(String registeredDate) {
        this.registeredDate = registeredDate;
    }


}

//more cleaner version
//as we see, no need to right click and generate getter and setter.
//package com.pm.patientservice.dto;
//
//import com.pm.patientservice.dto.validators.CreatePatientValidationGroup;
//import jakarta.validation.constraints.Email;
//import jakarta.validation.constraints.NotBlank;
//import jakarta.validation.constraints.Size;
//
//public record PatientRequestDto(
//
//        @NotBlank(message = "Name is required")
//        @Size(max = 100, message = "Name cannot exceed 100 characters")
//        String name,
//
//        @NotBlank(message = "Email is required")
//        @Email(message = "Email should be valid")
//        String email,
//
//        @NotBlank(message = "Address is required")
//        String address,
//
//        @NotBlank(message = "Date of birth is required")
//        String dateOfBirth,
//
//        @NotBlank(
//                groups = CreatePatientValidationGroup.class,
//                message = "Registered date is required"
//        )
//        String registeredDate
//
//) {
//}
