package helpers.connector;

import com.google.gson.Gson;
import helpers.HTTPClient;
import helpers.connector.exception.EgovConnectorException;
import helpers.connector.exception.EgovEmptyResponseException;
import models.CurrencyType;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class EgovCurrencyConnector {

    private static final String EXCHANGE_RATE_URL = "https://data.egov.kz/api/v2/valutalar_bagamdary4/v485";
    private HTTPClient httpClient;

    public EgovCurrencyConnector() {
        this.httpClient = new HTTPClient();
    }

    public BigDecimal getCurrentExchangeRateFor(CurrencyType currency) throws EgovConnectorException {
        String exchangeRates;
        try {
            exchangeRates = httpClient.get(EXCHANGE_RATE_URL);

            if(exchangeRates == null || exchangeRates.isEmpty()) {
                throw new EgovEmptyResponseException();
            }

            List<EgovCurrency> currencyList = this.parseResponse(exchangeRates);
            Optional<EgovCurrency> usd = currencyList.stream().filter((item) -> item.getKod().equals(currency.toString())).findFirst();

            return usd.map(egovCurrency -> new BigDecimal(egovCurrency.getKurs())).orElseGet(() -> new BigDecimal(0));
        } catch (IOException e) {
            e.printStackTrace();
            throw new EgovConnectorException("Сервис временно не доступен.");
        } catch (EgovEmptyResponseException e) {
            throw new EgovConnectorException("Ошибка на стороне сервиса. Пустой ответ");
        } catch (Exception e) {
            e.printStackTrace();
            throw new EgovConnectorException("Произошла внутренняя ошибка.");
        }
    }

    private List<EgovCurrency> parseResponse(String response) {
        Gson gson = new Gson();
        return Arrays.asList(gson.fromJson(response, EgovCurrency[].class));
    }

}
