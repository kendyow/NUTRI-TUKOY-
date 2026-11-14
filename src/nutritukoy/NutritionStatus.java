package nutritukoy;

/**
 * Screening result for ONE child. These are screening levels, NOT diagnoses.
 */
public enum NutritionStatus {
    NORMAL(0, "Normal"),
    AT_RISK(1, "At risk - monitor"),
    NEEDS_FOLLOW_UP(2, "Needs follow-up"),
    URGENT_REFERRAL(3, "Urgent - refer to RHU");

    private final int severity;
    private final String label;

    NutritionStatus(int severity, String label) {
        this.severity = severity;
        this.label = label;
    }

    public int getSeverity() {
        return severity;
    }

    public String getLabel() {
        return label;
    }

    /** A child "needing nutritional follow-up" = NEEDS_FOLLOW_UP or worse. */
    public boolean needsFollowUp() {
        return severity >= NEEDS_FOLLOW_UP.severity;
    }
}