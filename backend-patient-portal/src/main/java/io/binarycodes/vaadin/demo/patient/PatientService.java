package io.binarycodes.vaadin.demo.patient;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class PatientService {

    private final PatientRepository patientRepository;

    @Autowired
    public PatientService(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    public Optional<Patient> getPatient(Long id) {
        return patientRepository.findById(id);
    }

    public List<Patient> getAllPatients() {
        return patientRepository.findAll();
    }

    public Patient save(Patient patient) {
        return patientRepository.save(patient);
    }

    public List<Patient> saveAll(List<Patient> patients) {
        return patientRepository.saveAllAndFlush(patients);
    }

    public List<Patient> getAllPatients(Pageable pageable) {
        return patientRepository.findAll(pageable).getContent();
    }

    public long count() {
        return patientRepository.count();
    }

    public Map<String, Long> genderGroupData() {
        return getAllPatients()
                .stream()
                .collect(Collectors.groupingBy(Patient::getGender, Collectors.counting()));
    }

    public Map<String, Long> ageGroupData() {
        var initialGroupData = IntStream.range(0, 100 / Patient.AGE_GROUP_PERIOD)
                .boxed()
                .map(num -> {
                    var start = num * Patient.AGE_GROUP_PERIOD;
                    var end = start + Patient.AGE_GROUP_PERIOD;
                    return "%d - %d".formatted(start, end);
                })
                .collect(Collectors.toMap(Function.identity(), (key) -> 0L));

        return getAllPatients()
                .stream()
                .collect(Collectors.collectingAndThen(
                        Collectors.groupingBy(Patient::ageGroup, HashMap::new, Collectors.counting()),
                        groupedData -> {
                            initialGroupData.putAll(groupedData);
                            return initialGroupData;
                        }));
    }
}
