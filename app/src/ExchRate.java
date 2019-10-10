
public class ExchRate {
    private String currency;
    private float value;

    public void setRate(String _currency, float _value) {
        this.currency = _currency;
        this.value = _value;
    } 

    public String getCurrency() {
        return currency;
    }

    public float getValue() {
        return value;
    }
}
