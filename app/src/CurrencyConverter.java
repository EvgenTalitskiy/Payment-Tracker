/**
 * Класс интерфейс описывающий базовое поведение конвертера валюты
 */
public interface CurrencyConverter {
    /**
     * Метод определения курса перевода из одной валюты в другую
     * @param currencyFrom - валюта из
     * @param currencyTo - валюта в
     * @return возвращает курс обмена валют
     * @throws Exception
     */
    public float convert(String currencyFrom, String currencyTo) throws Exception;
}
 