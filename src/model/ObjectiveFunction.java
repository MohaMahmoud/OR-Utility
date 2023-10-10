package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ObjectiveFunction {
    private OptimizationDirection direction;
    private List<Double> coefficients;

    public ObjectiveFunction(OptimizationDirection direction, int variableCount) {
        this.direction = direction;
        this.coefficients = new ArrayList<>(variableCount);
        for (int i = 0; i < variableCount; i++) {
            coefficients.add(0.0);
        }
    }

    public List<Double> getCoefficients() {
        return Collections.unmodifiableList(coefficients);
    }

    public void setCoefficients(List<Double> coefficients) {
        this.coefficients = coefficients;
    }

    public OptimizationDirection getDirection() {
        return direction;
    }

    public void setDirection(OptimizationDirection direction) {
        this.direction = direction;
    }
}
