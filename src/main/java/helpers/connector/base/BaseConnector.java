package helpers.connector.base;

import helpers.HTTPClient;
import helpers.connector.base.exception.BaseConnectorException;
import helpers.connector.base.exception.EmptyResponseException;
import models.CurrencyType;

import java.io.IOException;
import java.math.BigDecimal;

public abstract class BaseConnector {

    private HTTPClient httpClient;

    public BaseConnector() {
        this.httpClient = new HTTPClient();
    }

    public BigDecimal getCurrentExchangeRateFor(CurrencyType currency) throws BaseConnectorException {
        String exchangeRates;
        try {
            exchangeRates = httpClient.get(getExchangeRateUrl(currency));
            System.out.println(exchangeRates);

            if(exchangeRates == null || exchangeRates.isEmpty()) {
                throw new EmptyResponseException();
            }

            return getRate(exchangeRates, currency);
        } catch (IOException e) {
            e.printStackTrace();
            throw new BaseConnectorException("Сервис временно не доступен.");
        } catch (EmptyResponseException e) {
            throw new BaseConnectorException("Ошибка на стороне сервиса. Пустой ответ");
        } catch (Exception e) {
            e.printStackTrace();
            throw new BaseConnectorException("Произошла внутренняя ошибка.");
        }
    }

    protected abstract BigDecimal getRate(String response,  CurrencyType currency);

    protected abstract String getExchangeRateUrl(CurrencyType currencyType);

}
