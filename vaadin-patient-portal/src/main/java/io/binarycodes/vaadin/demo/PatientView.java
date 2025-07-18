package io.binarycodes.vaadin.demo;

import java.util.Comparator;
import java.util.Map;

import io.binarycodes.vaadin.demo.patient.Patient;
import io.binarycodes.vaadin.demo.patient.PatientService;
import jakarta.annotation.security.PermitAll;

import com.vaadin.flow.component.charts.Chart;
import com.vaadin.flow.component.charts.model.ChartType;
import com.vaadin.flow.component.charts.model.DataLabels;
import com.vaadin.flow.component.charts.model.DataSeries;
import com.vaadin.flow.component.charts.model.DataSeriesItem;
import com.vaadin.flow.component.charts.model.HorizontalAlign;
import com.vaadin.flow.component.charts.model.LayoutDirection;
import com.vaadin.flow.component.charts.model.ListSeries;
import com.vaadin.flow.component.charts.model.PlotOptionsPie;
import com.vaadin.flow.component.charts.model.VerticalAlign;
import com.vaadin.flow.component.charts.model.XAxis;
import com.vaadin.flow.component.charts.model.YAxis;
import com.vaadin.flow.component.charts.model.style.SolidColor;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.TabSheet;
import com.vaadin.flow.component.tabs.TabSheetVariant;
import com.vaadin.flow.router.Route;

@Route(value = "", layout = MainLayout.class, autoLayout = false)
@PermitAll
public class PatientView extends VerticalLayout {

    private final PatientService service;
    private final PatientInfoSection patientInfoSection;
    private Grid<Patient> grid;

    public PatientView(PatientService service) {
        this.service = service;
        setSizeFull();

        this.patientInfoSection = new PatientInfoSection(service, this::updateGridData);
        add(this.patientInfoSection);

        init();
    }

    private void init() {
        add(createTabs());
    }

    private TabSheet createTabs() {
        var tabSheet = new TabSheet();
        tabSheet.setSizeFull();
        tabSheet.add("Patients", patientGrid());
        tabSheet.add("Analytics", patientAnalytics());
        return tabSheet;
    }

    private TabSheet patientAnalytics() {
        var tabSheet = new TabSheet();
        tabSheet.addThemeVariants(TabSheetVariant.LUMO_TABS_CENTERED);
        tabSheet.setSizeFull();
        tabSheet.add("Age Group", ageGroupChart());
        tabSheet.add("Gender", genderChart());
        return tabSheet;
    }

    private Chart ageGroupChart() {
        var chart = new Chart(ChartType.COLUMN);

        var conf = chart.getConfiguration();
        conf.setTitle("Patients by age group");

        var xAxis = new XAxis();
        var series = new ListSeries("Age Group");

        service.ageGroupData()
                .entrySet()
                .stream().sorted(Comparator.comparing(Map.Entry::getKey))
                .forEach(entry -> {
                    xAxis.addCategory(entry.getKey());
                    series.addData(entry.getValue());
                });
        conf.addSeries(series);
        conf.addxAxis(xAxis);

        var yAxis = new YAxis();
        yAxis.setTitle("Number of patients");
        conf.addyAxis(yAxis);

        return chart;
    }

    private Chart genderChart() {
        var chart = new Chart(ChartType.PIE);

        var dataLabels = new DataLabels();
        dataLabels.setEnabled(false);

        var options = new PlotOptionsPie();
        options.setShowInLegend(true);
        options.setInnerSize("90%");
        options.setSize("100%");  // Default
        options.setCenter("50%", "50%"); // Default
        options.setDataLabels(dataLabels);

        var conf = chart.getConfiguration();
        conf.setPlotOptions(options);

        var legend = conf.getLegend();
        legend.getTitle().setText("Status");
        legend.setEnabled(true);
        legend.setLayout(LayoutDirection.VERTICAL);
        legend.setAlign(HorizontalAlign.RIGHT);
        legend.setVerticalAlign(VerticalAlign.MIDDLE);

        var series = new DataSeries();

        service.genderGroupData()
                .entrySet()
                .stream().sorted(Comparator.comparing(Map.Entry::getKey))
                .forEach(entry -> {
                    var item = new DataSeriesItem(entry.getKey(), entry.getValue());
                    item.setColor("Female".equalsIgnoreCase(entry.getKey()) ? SolidColor.DEEPPINK : SolidColor.BLUE);
                    series.add(item);
                });

        conf.addSeries(series);
        return chart;
    }

    private Grid<Patient> patientGrid() {
        grid = new Grid<>(Patient.class, false);
        grid.setSizeFull();
        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER, GridVariant.LUMO_ROW_STRIPES);

        grid.addColumn(Patient::getName).setHeader("Name");
        grid.addColumn(Patient::getId).setHeader("Id");
        grid.addColumn(Patient::getMedicalRecordNumber).setHeader("Medical Record");
        grid.addColumn(Patient::getDoctor).setHeader("Doctor");
        grid.addColumn(Patient::getAge).setHeader("Age");
        grid.addColumn(Patient::getLastVisitedOn).setHeader("Last Visit");

        grid.setItemsPageable(service::getAllPatients);

        grid.addItemDoubleClickListener(event -> this.patientInfoSection.open(event.getItem()));

        return grid;
    }

    private void updateGridData(Patient patient) {
        grid.getLazyDataView().refreshItem(patient);
    }

}
