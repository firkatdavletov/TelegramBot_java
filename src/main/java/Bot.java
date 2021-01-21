import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Bot extends TelegramLongPollingBot {
    public static void main(String[] args) {
        ApiContextInitializer.init();
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
        try {
            telegramBotsApi.registerBot(new Bot());
        } catch (TelegramApiRequestException e) {
            e.printStackTrace();
        }
    }

    public void sendMsg(Message message, String text) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(message.getChatId().toString());
        sendMessage.setReplyToMessageId(message.getMessageId());
        sendMessage.setText(text);
        try {
            setButtons(sendMessage);
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public void onUpdateReceived(Update update) {
        Model model = new Model(); //создаём модель, которая будет хранить данные
        Message message = update.getMessage(); //инициализируем сообщение
        if (message != null && message.hasText()) {
            switch (message.getText()) {
                case "/help":
                    sendMsg(message, "Привет! Я бот, созданный Фиркатом."+
                            "Пока что я умею только узнавать погоду в разных городах." +
                            "Напише мне название города и я отвечу:)");
                    break;
                case "/settings":
                    sendMsg(message, "Что будем настраивать?");
                    break;
                default:
                    try {
                        sendMsg(message, Weather.getWeather(message.getText(), model));
                    } catch (IOException e) {
                        sendMsg(message, "Такой город не найден!");
                    }
            }
        }
    }

    public void setButtons(SendMessage sendMessage) {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        sendMessage.setReplyMarkup(replyKeyboardMarkup);
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true); //подстраиватель размера клавиатуры
        replyKeyboardMarkup.setOneTimeKeyboard(false); //скрывать клавиатуру или не скрывать

        List<KeyboardRow> keyBoardRowList = new ArrayList<>(); //создадим кнопки
        KeyboardRow keyboardFirstRow = new KeyboardRow(); //инициализируем первую строку клавиатуры

        keyboardFirstRow.add(new KeyboardButton("/help")); //Добавим кнопки
        keyboardFirstRow.add(new KeyboardButton("/settings"));

        keyBoardRowList.add(keyboardFirstRow); //добавляем строку в список
        replyKeyboardMarkup.setKeyboard(keyBoardRowList); //устанавливаем список в клавиатуру
    }

    public String getBotUsername() {
        return "Firkats_bot";
    }

    public String getBotToken() {
        return "1590085566:AAGZPvUtMm906O-ZYtK74wo7fUdV6f35wXs";
    }
}
