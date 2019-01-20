package helpers.connector.egov;

import com.google.gson.Gson;
import helpers.connector.base.BaseConnector;
import models.CurrencyType;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class EgovCurrencyConnector extends BaseConnector {

    @Override
    protected BigDecimal getRate(String response, CurrencyType currency) {
        List<EgovExchangeRate> exchangeRates = this.parseResponse(response);
        Optional<EgovExchangeRate> usd = exchangeRates.stream().filter((item) -> item.getKod().equals(currency.toString())).findFirst();

        return usd.map(egovExchangeRate -> new BigDecimal(egovExchangeRate.getKurs())).orElseGet(() -> new BigDecimal(0));
    }

    @Override
    protected String getExchangeRateUrl(CurrencyType currencyType) {
        return "https://data.egov.kz/api/v2/valutalar_bagamdary4/v490?size=1000";
    }

    private List<EgovExchangeRate> parseResponse(String response) {
        Gson gson = new Gson();
        return Arrays.asList(gson.fromJson(response, EgovExchangeRate[].class));
    }

}
