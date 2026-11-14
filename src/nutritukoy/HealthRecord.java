package nutritukoy;

import java.time.LocalDate;
import java.time.YearMonth;

/**
 * ABSTRACTION: the common structure of every screening record.
 * Each age group will be a subclass that decides how to compute the status.
 *
 * NOTE: thresholds in the subclasses are SIMPLIFIED screening rules for the demo.
 * A real deployment should use the WHO Child Growth Standards.
 */
public abstract class HealthRecord {
    private final Child child;
    private final LocalDate date;
    private final double weightKg;
    private final double heightCm;
    private final double muacCm;   // mid-upper arm circumference; 0 = not measured

    protected HealthRecord(Child child, LocalDate date,
                           double weightKg, double heightCm, double muacCm) {
        if (child == null || date == null) {
            throw new IllegalArgumentException("Child and date are required.");
        }
        if (weightKg < 1 || weightKg > 80) {
            throw new IllegalArgumentException("Weight must be between 1 and 80 kg.");
        }
        if (heightCm < 40 || heightCm > 170) {
            throw new IllegalArgumentException("Height must be between 40 and 170 cm.");
        }
        if (muacCm < 0 || muacCm > 30) {
            throw new IllegalArgumentException("MUAC must be between 0 and 30 cm (0 = not measured).");
        }
        // Data-entry safety net: catches typos (e.g., 85 cm for an 8-year-old) that would
        // otherwise be silently scored as "normal". Low weight is NOT rejected, because
        // low weight is exactly what the system is meant to detect.
        int months = child.ageInMonths(date);
        if (months >= 0 && months < 132) {
            double expected = expectedHeightCm(months);
            if (heightCm < expected * 0.75 || heightCm > expected * 1.25) {
                throw new IllegalArgumentException(String.format(
                        "Height %.1f cm looks wrong for a %d-month-old child (expected about %.0f cm). Please re-measure.",
                        heightCm, months, expected));
            }
        }
        this.child = child;
        this.date = date;
        this.weightKg = weightKg;
        this.heightCm = heightCm;
        this.muacCm = muacCm;
    }

    /** Rough median height (cm) by age in months, used only for the entry sanity check. */
    public static double expectedHeightCm(int ageMonths) {
        if (ageMonths <= 12) return 50 + 2.1 * ageMonths;
        if (ageMonths < 24) return 75 + 1.1 * (ageMonths - 12);
        if (ageMonths < 60) return 87 + 0.64 * (ageMonths - 24);
        return 110 + 5.5 * (ageMonths / 12.0 - 5);
    }

    /** Each age group overrides this. */
    public abstract NutritionStatus computeStatus();

    /** Each age group names itself. */
    public abstract String getAgeGroupLabel();

    public Child getChild() { return child; }
    public LocalDate getDate() { return date; }
    public YearMonth getMonth() { return YearMonth.from(date); }
    public double getWeightKg() { return weightKg; }
    public double getHeightCm() { return heightCm; }
    public double getMuacCm() { return muacCm; }

    protected int ageMonths() {
        return child.ageInMonths(date);
    }
}