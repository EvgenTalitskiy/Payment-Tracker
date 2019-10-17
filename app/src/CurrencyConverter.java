/**
 * ����� ��������� ����������� ������� ��������� ���������� ������
 */
public interface CurrencyConverter {
    /**
     * ����� ����������� ����� �������� �� ����� ������ � ������
     * @param currencyFrom - ������ ��
     * @param currencyTo - ������ �
     * @return ���������� ���� ������ �����
     * @throws Exception
     */
    public float convert(String currencyFrom, String currencyTo) throws Exception;
}
 