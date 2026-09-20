package com.pm.patientservice.controller;

import com.pm.patientservice.dto.PatientRequestDto;
import com.pm.patientservice.dto.PatientResponseDto;
import com.pm.patientservice.dto.validators.CreatePatientValidationGroup;
import com.pm.patientservice.service.PatientService;
import jakarta.validation.Valid;
import jakarta.validation.groups.Default;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

//In Nestjs, it was @Controller("/patients")
@RestController
@RequestMapping("/patients") //http://localhost:4000/patients
public class PatientController {
    private final PatientService patientService;


    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    //In Nestjs
    //@Get()
    //  async getPatients(): Promise<PatientResponseDto[]> {
    //    // NestJS default was return status 200 OK and covert to JSON，
    //    not like Java, using ResponseEntity to response
    //    return this.patientService.getPatients();
    //  }
    @GetMapping
    public ResponseEntity<List<PatientResponseDto>> getPatient() {
        List<PatientResponseDto> patients = patientService.getPatients();
        return ResponseEntity.ok().body(patients);
    }

    @PostMapping
    public ResponseEntity<PatientResponseDto> createPatient(
            //@Valid //ValidationPipe , validate the dto entity
            @Validated({Default.class, CreatePatientValidationGroup.class})
            @RequestBody //@Body , read JSON from body ，convert to DTO
            PatientRequestDto patientRequestDto
    ){
        PatientResponseDto patientResponseDto = patientService.createPatient(patientRequestDto);
        return ResponseEntity.ok().body(patientResponseDto);
    }

    //localhost:4000/patients/id
    @PutMapping("/{id}")
    public ResponseEntity<PatientResponseDto> updatePatient(
            @PathVariable UUID id,
            @Validated({Default.class})  //here if change to @Valid also can, see more in below explain
            @RequestBody PatientRequestDto patientRequestDto
    ){
        PatientResponseDto patientResponseDto = patientService.updatePatient(id, patientRequestDto);
        return ResponseEntity.ok().body(patientResponseDto);
    }
}


//@Valid and @Validated({Default.class}) is same
//so, when to use @Validated()??
//example:
//public class PatientRequestDto {
//
//    // 1. 没有指定 groups，自动归为 Default.class 组
//    @NotBlank(message = "姓名不能为空")
//    private String name;
//
//    // 2. 只有在显式指定 groups 时，它才属于自定义组
//    @NotNull(groups = UpdateGroup.class, message = "更新时ID不能为空")
//    private UUID id;
//}
//
// if we use @Valid or @Validated({Default.class}) in this case.
// only the name will be run validation.
// so, @Validated({Default.class, UpdateGroup.class}) will run full validation