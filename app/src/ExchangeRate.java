/**
 * ����� ��������� ����� ��������� ������ �� ��������� � �����
 */
public class ExchangeRate {
    /**
     * ������ �����
     */
    private String currency;
    /**
     * ����
     */
    private float value;

    /**
     * ����������� �����
     *
     * @param currency - ������
     * @param value    - ���� ������ �� ��������� � �����
     */
    public void setRate(String currency, float value) {
        this.currency = currency;
        this.value = value;
    }

    /**
     * ����� ��������� ������� ������
     *
     * @return ������
     */
    public String getCurrency() {
        return currency;
    }

    /**
     * ����� ��������� �����
     *
     * @return ����
     */
    public float getValue() {
        return value;
    }
}
