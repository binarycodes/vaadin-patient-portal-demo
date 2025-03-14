package io.binarycodes.vaadin.demo.dummy;

import java.time.ZoneOffset;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

import io.binarycodes.vaadin.demo.patient.Patient;
import io.binarycodes.vaadin.demo.patient.PatientService;
import net.datafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FakerService {

    private final Faker faker;
    private final PatientService patientService;

    @Autowired
    public FakerService(PatientService patientService) {
        this.patientService = patientService;
        this.faker = new Faker();
    }

    public void createAndPersistFakePatients(int numberOfPatients, boolean always) {
        if (!always && patientService.count() > 0) {
            return;
        }

        var patients = IntStream.range(0, numberOfPatients).boxed().map(i -> createFakePatient()).toList();
        patientService.saveAll(patients);
    }

    public Patient createFakePatient() {
        var patient = new Patient();
        patient.setFirstName(faker.name().firstName());
        patient.setLastName(faker.name().lastName());
        patient.setGender(faker.gender().binaryTypes());
        patient.setDoctor(faker.name().fullName());
        patient.setMedicalRecordNumber(faker.number().randomNumber(5));
        patient.setSsn(faker.idNumber().ssnValid());
        patient.setDateOfBirth(faker.timeAndDate().birthday(5, 93).atStartOfDay().toInstant(ZoneOffset.UTC));
        patient.setAvatarUrl(faker.internet().image(100, 100, patient.getName()));
        /* at most in the last 6 months */
        patient.setLastVisitedOn(faker.timeAndDate().past(30 * 6, 10, TimeUnit.DAYS));

        return patient;
    }
}
