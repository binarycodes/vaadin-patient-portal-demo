package io.binarycodes.vaadin.demo.patient;

import java.time.Instant;

import jakarta.annotation.Nullable;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Formula;

@Getter
@Setter
@Entity
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Patient {
    public static final int AGE_GROUP_PERIOD = 10;

    @Id
    @GeneratedValue
    @EqualsAndHashCode.Include
    private Long id;

    @Nullable
    private String avatarUrl;
    @NotNull
    private String firstName;
    @NotNull
    private String lastName;
    @NotNull
    private String gender;
    @NotNull
    private String ssn;
    @NotNull
    private String doctor;
    @NotNull
    private Long medicalRecordNumber;
    @NotNull
    private Instant dateOfBirth;
    @Nullable
    private Instant lastVisitedOn;

    @Formula(" concat(first_name, ' ', last_name) ")
    private String name;

    @Formula(" timestampdiff(YEAR,date_of_birth,curdate()) ")
    private Integer age;

    public String ageGroup() {
        int group = Math.floorDiv(age, AGE_GROUP_PERIOD) * AGE_GROUP_PERIOD;
        return "%d - %d".formatted(group, group + AGE_GROUP_PERIOD);
    }
}
