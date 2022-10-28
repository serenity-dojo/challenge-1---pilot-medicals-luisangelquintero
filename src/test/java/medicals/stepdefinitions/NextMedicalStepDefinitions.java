package medicals.stepdefinitions;

import io.cucumber.java.ParameterType;
import io.cucumber.java.en.*;

import medicals.domain.MedicalsService;
import medicals.domain.Pilot;
import medicals.domain.PilotClass;
import net.serenitybdd.core.Serenity;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoUnit;
import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;


public class NextMedicalStepDefinitions {

    Pilot pilot;
    final MedicalsService medicalsService = new MedicalsService();

    @ParameterType(".*")
    public PilotClass pilotClass(String label) {
        return PilotClass.withLabel(label);
    }

    private final static DateTimeFormatter MONTH_DAY_YEAR_FORMAT = new DateTimeFormatterBuilder()
            .parseCaseInsensitive()
            .appendPattern("dd-MMM-yyyy")
            .toFormatter(Locale.ENGLISH);

    @ParameterType(".*")
    public LocalDate readableDate(String value) {
        return LocalDate.parse(value, MONTH_DAY_YEAR_FORMAT);
    }

    @Given("{string} is a {pilotClass} pilot {int} years old")
    public void pilot_category(String name, PilotClass pilotClass, int age) {
        pilot = Pilot.builder().pilotName(name).pilotClass(pilotClass).age(age).build();
    }

    @When("They got his certificate on the {readableDate}")
    public void certificate_expedition_date(LocalDate dateOfExpedition) {
        medicalsService.recordDateOfMedical(pilot, dateOfExpedition);
    }

    @Then("their medical certificate should be valid until {}")
    public void certificate_expiration_date(String dateOfExpiration) {
        if (pilot.getPilotClass().equals(PilotClass.EXPIRED)) {
            assertThat(true).isTrue();
        } else {
            LocalDate expirationDateFormatted = LocalDate.parse(dateOfExpiration, MONTH_DAY_YEAR_FORMAT);
            LocalDate nextMedical = medicalsService.findDateLimitForNextMedical(pilot);
            assertThat(nextMedical).isEqualTo(expirationDateFormatted);
        }
    }

    @When("the date {readableDate} arrives")
    public void set_current_date(LocalDate currentDate) {
        medicalsService.degreePilot(pilot, currentDate);
    }

    @When("there were passed {int} months after the certificate's expedition")
    public void months_with_certificate(int durationMonths) {
        LocalDate dateOfExpedition = LocalDate.now();
        System.out.println("local date" + dateOfExpedition);
        medicalsService.recordDateOfMedical(pilot, dateOfExpedition);

        LocalDate date = medicalsService.dateOfMedicalFor(pilot).plusMonths(durationMonths);

        medicalsService.degreePilot(pilot, date);
    }

    @Then("The license should be dropped to the {pilotClass} category")
    public void license_dropped(PilotClass followingClass) {
        assertThat(pilot.getPilotClass()).isEqualTo(followingClass);
    }

    @Given("{string} is a pilot with a medical certificate since {readableDate}")
    public void medical_certificate_expedition_date(String name, LocalDate dateOfExpedition) {
        pilot = Pilot.builder().pilotName(name).build();
        medicalsService.recordDateOfMedical(pilot, dateOfExpedition);
    }

    @When("they check how old is their Medical Certificate on the day of {readableDate}")
    public void how_old_is_medical_certificate_on_current_date(LocalDate currentDate) {
        Serenity.setSessionVariable("current date").to(currentDate);
    }

    @Then("their certificate should have {int} months old")
    public void certificate_should_Have(int months) {
        LocalDate currentDate = Serenity.sessionVariableCalled("current date");
        LocalDate expeditionDate = medicalsService.dateOfMedicalFor(pilot);

        long monthsDifference = ChronoUnit.MONTHS.between(expeditionDate, currentDate);
        assertThat(monthsDifference).isEqualTo(months);
    }
}


