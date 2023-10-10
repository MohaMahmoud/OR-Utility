package model;
public enum OptimizationDirection {
    MAX("(MAX)"),
    MIN("(MIN)");

    private final String representaion;

    OptimizationDirection(String representation) {
        this.representaion = representation;
    }

    @Override
    public String toString() {
        return representaion;
    }
}