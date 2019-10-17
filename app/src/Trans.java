/**
 * Класс реализации проводки
 */
public class Trans {
    /**
     * Валюта
     */
    private String currency;
    /**
     * Сумма
     */
    private double amount;

    /**
     * Регистрация проводки
     *
     * @param currency - валюта проводки
     * @param amount   - сумма проводки в указанной валюте
     */
    public void setTrans(String currency, double amount) {
        this.currency = currency;
        this.amount = amount;
    }

    /**
     * Метод получения текущей валюты проводки
     *
     * @return валюта проводки
     */
    public String getCurrency() {
        return currency;
    }

    /**
     * Метод получения суммы проводки
     *
     * @return сумма проводки
     */
    public double getAmount() {
        return amount;
    }

    /**
     * Возвращает значение проводки в виде строки
     *
     * @return значение проводки вида "USD 100"
     */
    @Override
    public String toString() {
        if (!currency.equals("")) {
            return String.format("%s %.4f", this.currency, this.amount);
        }
        return super.toString();
    }

    /**
     * Метод перекрыт с целью добавления сравнения проводки по коду проводки
     *
     * @param obj - проводка (Trans)
     * @return результат соответствия
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
