package src;

public enum ComparisonOperator {
    LEQ("<="),
    EQ("="),
    GEQ(">=");

    private final String sign;

    ComparisonOperator(String sign) {
        this.sign = sign;
    }

    public String getSign() {
        return sign;
    }
}

