package project.carsharing.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import project.carsharing.exception.SendingNotificationException;
import project.carsharing.service.NotificationService;

@Service
public class TelegramNotificationServiceImpl
        extends TelegramLongPollingBot
        implements NotificationService {
    @Value("${bot.username}")
    private String botUsername;
    @Value("${bot.token}")
    private String botToken;
    @Value("${bot.target-chat-id}")
    private String chatId;

    @Override
    public void sendNotification(String message) {
        SendMessage sendMessage = new SendMessage(chatId, message);
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            throw new SendingNotificationException("Cannot send notification to chat: "
                    + sendMessage.getChatId());
        }
    }

    @Override
    public void onUpdateReceived(Update update) {
    }

    @Override
    public String getBotUsername() {
        return botUsername;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }
}
