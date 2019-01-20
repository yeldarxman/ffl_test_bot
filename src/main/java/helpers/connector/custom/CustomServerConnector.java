package helpers.connector.custom;

import com.google.gson.Gson;
import helpers.connector.base.BaseConnector;
import models.CurrencyType;

import java.math.BigDecimal;

public class CustomServerConnector extends BaseConnector {

    @Override
    protected BigDecimal getRate(String response,  CurrencyType currency) {
        CustomExchangeRate customExchangeRate = parseResponse(response);
        return customExchangeRate.getRate();
    }

    @Override
    protected String getExchangeRateUrl(CurrencyType currencyType) {
        return "http://localhost:8081/exchange-rates/" + currencyType.toString();
    }

    private CustomExchangeRate parseResponse(String response) {
        Gson gson = new Gson();
        return gson.fromJson(response, CustomExchangeRate.class);
    }

}
