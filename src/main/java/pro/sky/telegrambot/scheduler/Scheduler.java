package pro.sky.telegrambot.scheduler;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import pro.sky.telegrambot.repository.NotificationTaskRepository;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Component
public class Scheduler {

    private final NotificationTaskRepository notificationTaskRepository;
    private final TelegramBot telegramBot;

    public Scheduler(NotificationTaskRepository notificationTaskRepository, TelegramBot telegramBot) {
        this.notificationTaskRepository = notificationTaskRepository;
        this.telegramBot = telegramBot;
    }
@Scheduled(fixedDelay = 60000)
    public void sendNotification() {
        notificationTaskRepository.findAllByNotificationTime(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES)).stream()
                .forEach(notificationTask -> {
                    SendMessage message = new SendMessage(notificationTask.getChatId(), String.format("Настало время выполнить задачу: %s", notificationTask.getNotificationMessage()));
                    telegramBot.execute(message);
                });

    }
}
