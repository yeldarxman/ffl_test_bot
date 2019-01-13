import helpers.connector.EgovCurrencyConnector;
import helpers.connector.exception.EgovConnectorException;
import models.CurrencyType;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class FFLTestBot extends TelegramLongPollingBot {

    private EgovCurrencyConnector connector;
    private static String START = "/start";
    private static String GET_EXCHANGE_RATE = "/get_exchange_rate";


    public FFLTestBot() {
        connector = new EgovCurrencyConnector();
    }

    public void onUpdateReceived(Update update) {
        if (shouldShowCurrentExchangeRate(update)) {

            SendMessage message = new SendMessage().setChatId(this.getChatId(update));
            message = message.setText("Текущий курс доллара: " + this.getUSDExchangeRate());

            InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
            List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
            List<InlineKeyboardButton> rowInline = new ArrayList<>();
            rowInline.add(new InlineKeyboardButton().setText("Получить текущий курс доллара к тенге").setCallbackData(GET_EXCHANGE_RATE));
            rowsInline.add(rowInline);
            markupInline.setKeyboard(rowsInline);
            message.setReplyMarkup(markupInline);

            try {
                execute(message);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }

    public String getBotUsername() {
        return "ffl_test_bot";
    }

    public String getBotToken() {
        return "661534912:AAE7mF_CFzyUdn2Z5f8MDQsGNzFg8uIfQzI";
    }

    private String getUSDExchangeRate() {
        String result;
        try {
            BigDecimal exchangeRate = connector.getCurrentExchangeRateFor(CurrencyType.USD);
            result = exchangeRate.toString();
        } catch (EgovConnectorException e) {
            result = e.getMessage();
        }

        return result;
    }

    private boolean shouldShowCurrentExchangeRate(Update update) {
        if(update != null) {
            if (update.hasMessage() &&
                    update.getMessage().hasText() &&
                    update.getMessage().getText().equals(START)) {
                return true;
            } else {
                return update.hasCallbackQuery() &&
                        update.getCallbackQuery().getData() != null &&
                        !update.getCallbackQuery().getData().isEmpty() &&
                        update.getCallbackQuery().getData().equals(GET_EXCHANGE_RATE);
            }
        }

        return false;
    }

    private Long getChatId(Update update) {
        if(update != null) {
            if (update.hasMessage() && update.getMessage().getChatId() != null) {
                return update.getMessage().getChatId();
            } else if (update.hasCallbackQuery() &&
                        update.getCallbackQuery().getMessage() != null &&
                            update.getCallbackQuery().getMessage().getChatId() != null){
                return update.getCallbackQuery().getMessage().getChatId();
            }
        }
        return 0L;
    }

}
