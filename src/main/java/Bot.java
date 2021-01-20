import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class Bot extends TelegramLongPollingBot {
    @Override
    public void onUpdateReceived(Update update) {
        update.getUpdateId();
        SendMessage sendMessage = new SendMessage().setChatId(update.getMessage().getChatId());

        if (update.getMessage().getText().equals("Привет")) {
            String text = "Привет!";
            sendMessage.setText(text);
            try {
                execute(sendMessage);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public String getBotUsername() {
        return "Firkat_D_Bot";
    }

    @Override
    public String getBotToken() {
        return "1576856059:AAG6KYjUNmWqDcHp_zYpIyh0U0aD85T1buw";
    }
}
