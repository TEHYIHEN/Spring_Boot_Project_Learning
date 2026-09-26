package com.pm.patientservice.service;

import com.pm.patientservice.dto.PatientRequestDto;
import com.pm.patientservice.dto.PatientResponseDto;
import com.pm.patientservice.exception.EmailAlreadyExistsException;
import com.pm.patientservice.exception.PatientNotFoundException;
import com.pm.patientservice.mapper.PatientMapper;
import com.pm.patientservice.model.Patient;
import com.pm.patientservice.repository.PatientRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class PatientService {
    //final mean once patientRepository is assigned,
    //cannot assign a different repository, example: this.patientRepository = anotherRepository
    //same as Nestjs , final ≈ readonly
    private final PatientRepository patientRepository;

    public PatientService(PatientRepository patientRepository){
        this.patientRepository = patientRepository;
    }

    //List is a collection that hold multiple values.
    //In Typescript,
    //const patients: Patient[] = [];
    //List<Patient> patients;
    //
    public List<PatientResponseDto> getPatients (){
        List<Patient> patients = patientRepository.findAll();
        //
        //.stream() --> convert list to stream for functional processing and method chaining
        //
        //const patientResponseDTOs: PatientResponseDto[] = [];
        List<PatientResponseDto> patientResponseDTOs;
        patientResponseDTOs = patients.stream()

                //patients.stream().map(patient -> PatientMapper.toDTO(patient)).toList();
                //warning using the lambda
                // :: is called a method reference.
                //.toList() --> collect results into a new List
                //
                .map(PatientMapper::toDTO).toList();

        //without .stream()
        //List<PatientResponseDto> patientResponseDTOs = new ArrayList<>();
        //
        //for (Patient patient : patients) {
        //    PatientResponseDto dto = PatientMapper.toDTO(patient);
        //    patientResponseDTOs.add(dto);
        //}

        return patientResponseDTOs;
    }

    public PatientResponseDto createPatient(PatientRequestDto patientRequestDto) {

        if(patientRepository.existsByEmail(patientRequestDto.getEmail())){
            throw new EmailAlreadyExistsException(
                    "A patient with this email " + "already exists " + patientRequestDto.getEmail());
        }

        Patient newPatient = patientRepository.save(
                PatientMapper.toModel(patientRequestDto)
        );

        return PatientMapper.toDTO(newPatient);
    }

    public PatientResponseDto updatePatient(UUID id,
        PatientRequestDto patientRequestDto) {


        //const patient = await this.patientRepository.findOneBy({ id });
        //if (!patient) {
        //  throw new NotFoundException(`Patient not found with ID: ${id}`);
        //}
        Patient patient = patientRepository.findById(id).orElseThrow(
                () -> new PatientNotFoundException("Patient not found with ID: " + id));

        if(patientRepository.existsByEmailAndIdNot(patientRequestDto.getEmail(), id)){
            throw new EmailAlreadyExistsException(
                    "A patient with this email " + "already exists " + patientRequestDto.getEmail());
        }

        patient.setName(patientRequestDto.getName());
        patient.setAddress(patientRequestDto.getAddress());
        patient.setEmail(patientRequestDto.getEmail());
        patient.setDateOfBirth(LocalDate.parse(patientRequestDto.getDateOfBirth()));

        Patient updatePatient = patientRepository.save(patient);
        return PatientMapper.toDTO(updatePatient);
    }

    //why using void here,
    //because no need to response
    //204 No content
    public void deletePatient(UUID id) {
        patientRepository.deleteById(id);
    }
}
