package model;
public enum ComparisonOperator {
    LEQ("<="),
    EQ("="),
    GEQ(">=");

    private final String representation;

    ComparisonOperator(String representation) {
        this.representation = representation;
    }

    @Override
    public String toString() {
        return representation;
    }
}

