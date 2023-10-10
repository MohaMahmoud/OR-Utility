package ui;

public abstract class Operation {
    protected static final String SUCCESS = "OPERATION SUCCESSFUL";

    protected final String name; 
    protected final String description;

    public Operation(String name, String description) {
        this.name = name;
        this.description = description;
    }

    @Override
    public String toString() {
        return name + " - " + description;
    }

    public abstract String execute(String[] args) throws OperationException;
}
