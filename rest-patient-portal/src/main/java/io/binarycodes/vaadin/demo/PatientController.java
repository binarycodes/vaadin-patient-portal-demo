package io.binarycodes.vaadin.demo;

import java.util.List;
import java.util.Map;

import io.binarycodes.vaadin.demo.patient.Patient;
import io.binarycodes.vaadin.demo.patient.PatientService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/patient")
public class PatientController {

    private final PatientService patientService;

    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @GetMapping("/all")
    public List<Patient> getAllPatients() {
        return patientService.getAllPatients();
    }

    @GetMapping("/count")
    public long getPatientsCount() {
        return patientService.count();
    }

    @GetMapping("/count/groupByAge")
    public Map<String, Long> getPatientsCountByAgeGroup() {
        return patientService.ageGroupData();
    }

    @GetMapping("/count/groupByGender")
    public Map<String, Long> getPatientsCountByGender() {
        return patientService.genderGroupData();
    }

    @PostMapping("/save")
    public Patient savePatient(@RequestBody Patient patient) {
        return patientService.save(patient);
    }
}
