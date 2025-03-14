package io.binarycodes.vaadin.demo;

import java.time.ZoneOffset;
import java.util.function.Consumer;

import io.binarycodes.vaadin.demo.patient.Patient;
import io.binarycodes.vaadin.demo.patient.PatientService;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.avatar.Avatar;
import com.vaadin.flow.component.avatar.AvatarVariant;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Section;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.converter.IntegerToLongConverter;
import com.vaadin.flow.theme.lumo.LumoUtility;
import com.vaadin.flow.theme.lumo.LumoUtility.Background;
import com.vaadin.flow.theme.lumo.LumoUtility.BoxShadow;
import com.vaadin.flow.theme.lumo.LumoUtility.Display;
import com.vaadin.flow.theme.lumo.LumoUtility.FlexDirection;
import com.vaadin.flow.theme.lumo.LumoUtility.Overflow;
import com.vaadin.flow.theme.lumo.LumoUtility.Position;
import com.vaadin.flow.theme.lumo.LumoUtility.Transition;
import com.vaadin.flow.theme.lumo.LumoUtility.ZIndex;

public class PatientInfoSection extends Section {
    private final PatientService service;
    private final Consumer<Patient> updateCallback;

    public PatientInfoSection(PatientService service, Consumer<Patient> updateCallback) {
        this.service = service;
        this.updateCallback = updateCallback;

        addClassNames(Display.FLEX, FlexDirection.COLUMN, Background.BASE, BoxShadow.MEDIUM, Overflow.HIDDEN, LumoUtility.Padding.XLARGE,
                Position.FIXED, Position.Bottom.NONE, Position.End.NONE, Position.Top.NONE, Transition.ALL, ZIndex.XSMALL);

        setMaxWidth(100, Unit.PERCENTAGE);
        setWidth(1024, Unit.PIXELS);

        close();
    }

    public void close() {
        getStyle().set("translate", "100%");
        setEnabled(false);
        getElement().setAttribute("inert", true);
    }

    public void open(Patient patient) {
        getStyle().set("translate", "0");
        setEnabled(true);
        getElement().removeAttribute("inert");
        reinit(patient);
    }

    private void reinit(Patient patient) {
        removeAll();
        init(patient);
    }

    private void init(Patient patient) {
        var headerWrapper = new Div();
        headerWrapper.addClassNames(Display.FLEX, FlexDirection.ROW, LumoUtility.JustifyContent.BETWEEN, LumoUtility.Padding.Bottom.XLARGE);

        var headerTitle = new H2(patient.getName());
        var closeButton = new Button(VaadinIcon.CLOSE.create(), e -> close());
        closeButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY_INLINE, ButtonVariant.LUMO_ICON);
        headerWrapper.add(headerTitle, closeButton);

        var avatar = new Avatar(patient.getName(), patient.getAvatarUrl());
        avatar.addThemeVariants(AvatarVariant.LUMO_XLARGE);

        var formLayout = createFormLayout(patient, avatar);

        add(headerWrapper, formLayout);
    }

    private Component createFormLayout(Patient patient, Avatar avatar) {
        var firstNameField = new TextField("First Name");
        var lastNameField = new TextField("Last Name");
        var genderField = new RadioButtonGroup<String>("Gender");
        genderField.setItems("Male", "Female");

        var ssnField = new TextField("SSN");
        var doctorField = new TextField("Doctor");
        var medicalRecordField = new IntegerField("Medical Record");
        var dateOfBirthField = new DatePicker("Date of Birth");

        var binder = new Binder<>(Patient.class);
        binder.forField(firstNameField).bind(Patient::getFirstName, Patient::setFirstName);
        binder.forField(lastNameField).bind(Patient::getLastName, Patient::setLastName);
        binder.forField(genderField).bind(Patient::getGender, Patient::setGender);
        binder.forField(ssnField).bind(Patient::getSsn, Patient::setSsn);
        binder.forField(doctorField).bind(Patient::getDoctor, Patient::setDoctor);
        binder.forField(medicalRecordField).withConverter(new IntegerToLongConverter()).bind(Patient::getMedicalRecordNumber, Patient::setMedicalRecordNumber);
        binder.forField(dateOfBirthField).withConverter(new LocalDateToInstantConverter(ZoneOffset.UTC)).bind(Patient::getDateOfBirth, Patient::setDateOfBirth);

        binder.setBean(patient);

        var formLayout = new FormLayout(firstNameField, lastNameField, genderField, dateOfBirthField, ssnField, doctorField, medicalRecordField);
        formLayout.setColspan(ssnField, 2);

        var saveButton = new Button("Save", buttonClickEvent -> {
            if (binder.validate().isOk()) {
                var updatedPatient = service.save(binder.getBean());
                updateCallback.accept(updatedPatient);
                close();
            }
        });
        saveButton.getStyle().setWidth("max-content");
        saveButton.addClassNames(LumoUtility.Margin.Vertical.LARGE);
        saveButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        var wrappedForm = new Div(avatar, formLayout, saveButton);
        wrappedForm.addClassNames(Display.FLEX, FlexDirection.COLUMN, LumoUtility.Gap.MEDIUM);
        return wrappedForm;
    }

}