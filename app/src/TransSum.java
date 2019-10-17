/**
 * Класс проводки аккумулирующий сумму по другим проводкам с группировкой по коду валюты
 */
public class TransSum extends Trans {

    /**
     * Метод позволяет создать/обновить значение суммирующей проводки на основании обычной проводки
     *
     * @param trans
     */
    public void setTrans(Trans trans) {
        super.setTrans(trans.getCurrency(), trans.getAmount());
    }
}
