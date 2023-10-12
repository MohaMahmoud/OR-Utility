package edu.kit.ui.logic;

import edu.kit.ui.exceptions.OperationException;

public abstract class Operation {
    protected static final String SUCCESS = "OPERATION SUCCESSFUL";

    protected final String name; 
    protected final String description;

    public Operation(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return name + " - " + description;
    }

    public abstract String execute() throws OperationException;
}
