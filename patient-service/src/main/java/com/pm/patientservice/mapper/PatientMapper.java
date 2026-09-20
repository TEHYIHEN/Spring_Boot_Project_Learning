package com.pm.patientservice.mapper;

import com.pm.patientservice.dto.PatientRequestDto;
import com.pm.patientservice.dto.PatientResponseDto;
import com.pm.patientservice.model.Patient;

import java.time.LocalDate;

public class PatientMapper {

    //static mean can call toDTO() using the class itself,
    //without creating an object of that class in another file
    //
    //without static
    //PatientMapper mapper = new PatientMapper()；
    //PatientResponseDto dto = mapper.toDTO(patient);
    //
    //with static
    //PatientResponseDto dto = PatientMapper.toDTO(patient);
    //
    public static PatientResponseDto toDTO(Patient patient){
        PatientResponseDto patientDTO = new PatientResponseDto();
        patientDTO.setId(patient.getId().toString());
        patientDTO.setName(patient.getName());
        patientDTO.setAddress(patient.getAddress());
        patientDTO.setEmail(patient.getEmail());
        patientDTO.setDateOfBirth(patient.getDateOfBirth().toString());
        return patientDTO;
    }

    public static Patient toModel(PatientRequestDto patientRequestDto){
        Patient patient = new Patient();
        patient.setName(patientRequestDto.getName());
        patient.setAddress(patientRequestDto.getAddress());
        patient.setEmail(patientRequestDto.getEmail());
        //if set the requestDto to :
        //private LocalDate dateOfBirth
        //then no need LocalDate.parse()
        patient.setDateOfBirth(LocalDate.parse(patientRequestDto.getDateOfBirth()));
        patient.setRegisteredDate(LocalDate.parse(patientRequestDto.getRegisteredDate()));

        return patient;
    }


}
