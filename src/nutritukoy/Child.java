package nutritukoy;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

/**
 * ENCAPSULATION: all fields are private. Children are identified by a code,
 * not a real name, so the demo never needs real personal data.
 */
public class Child {
    private final String code;          // e.g. "P3-012"
    private final LocalDate birthDate;
    private final char sex;             // 'M' or 'F'
    private String purok;               // e.g. "Purok 3"

    public Child(String code, LocalDate birthDate, char sex, String purok) {
        if (code == null || code.isBlank()) {
            throw new IllegalArgumentException("Child code is required.");
        }
        if (sex != 'M' && sex != 'F') {
            throw new IllegalArgumentException("Sex must be 'M' or 'F'.");
        }
        this.code = code;
        this.birthDate = birthDate;
        this.sex = sex;
        this.purok = purok;
    }

    public String getCode() { return code; }
    public LocalDate getBirthDate() { return birthDate; }
    public char getSex() { return sex; }
    public String getPurok() { return purok; }

    /** Controlled way to change the purok (family moved). */
    public void transferTo(String newPurok) {
        if (newPurok == null || newPurok.isBlank()) {
            throw new IllegalArgumentException("Purok is required.");
        }
        this.purok = newPurok;
    }

    /** Age in completed months on the given date. */
    public int ageInMonths(LocalDate onDate) {
        return (int) ChronoUnit.MONTHS.between(birthDate, onDate);
    }
}