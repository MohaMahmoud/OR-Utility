package edu.kit.model;
public enum ComparisonOperator {
    LEQ("<="),
    EQ("="),
    GEQ(">=");

    private final String representation;

    ComparisonOperator(String representation) {
        this.representation = representation;
    }

    public static ComparisonOperator parse(String operator) {
        switch (operator) {
            case "<=":
                return ComparisonOperator.LEQ;
            case "=":
                return ComparisonOperator.EQ;
            case ">=":
                return ComparisonOperator.GEQ;
            default:
                return null;
        }
    }

    @Override
    public String toString() {
        return representation;
    }
}

