/**
 * ����� ���������� ��������
 */
public class Trans {
    /**
     * ������
     */
    private String currency;
    /**
     * �����
     */
    private double amount;

    /**
     * ����������� ��������
     *
     * @param currency - ������ ��������
     * @param amount   - ����� �������� � ��������� ������
     */
    public void setTrans(String currency, double amount) {
        this.currency = currency;
        this.amount = amount;
    }

    /**
     * ����� ��������� ������� ������ ��������
     *
     * @return ������ ��������
     */
    public String getCurrency() {
        return currency;
    }

    /**
     * ����� ��������� ����� ��������
     *
     * @return ����� ��������
     */
    public double getAmount() {
        return amount;
    }

    /**
     * ���������� �������� �������� � ���� ������
     *
     * @return �������� �������� ���� "USD 100"
     */
    @Override
    public String toString() {
        if (!currency.equals("")) {
            return String.format("%s %.4f", this.currency, this.amount);
        }
        return super.toString();
    }

    /**
     * ����� �������� � ����� ���������� ��������� �������� �� ���� ��������
     *
     * @param obj - �������� (Trans)
     * @return ��������� ������������
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Trans) {
            if (((Trans) obj).getCurrency().equals(this.getCurrency())) {
                return true;
            }
        }
        return super.equals(obj);
    }
}
