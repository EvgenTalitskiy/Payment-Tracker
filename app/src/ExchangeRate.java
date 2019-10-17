/**
 * Класс обменного курса указанной валюты по отношению к рублю
 */
public class ExchangeRate {
    /**
     * Валюта курса
     */
    private String currency;
    /**
     * Курс
     */
    private float value;

    /**
     * Регистрация курса
     *
     * @param currency - валюта
     * @param value    - курс обмена по отношению к рублю
     */
    public void setRate(String currency, float value) {
        this.currency = currency;
        this.value = value;
    }

    /**
     * Метод получения текущей валюты
     *
     * @return валюта
     */
    public String getCurrency() {
        return currency;
    }

    /**
     * Метод получения курса
     *
     * @return курс
     */
    public float getValue() {
        return value;
    }
}
