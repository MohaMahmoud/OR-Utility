package edu.kit.model;

public enum ProgramForm {
    DEFAULT("Default"),
    STANDARD("Standard"),
    NORMAL("Normal"),
    CANONICAL("Canonical");

    private final String representation;

    ProgramForm(String representation) {
        this.representation = representation;
    }

    @Override
    public String toString() {
        return representation;
    }
}