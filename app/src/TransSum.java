/**
 * ����� �������� �������������� ����� �� ������ ��������� � ������������ �� ���� ������
 */
public class TransSum extends Trans {

    /**
     * ����� ��������� �������/�������� �������� ����������� �������� �� ��������� ������� ��������
     *
     * @param trans
     */
    public void setTrans(Trans trans) {
        super.setTrans(trans.getCurrency(), trans.getAmount());
    }
}
