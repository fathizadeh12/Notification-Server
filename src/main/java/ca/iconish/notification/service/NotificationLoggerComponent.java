package ca.iconish.notification.service;

import ca.iconish.notification.repository.NotificationRepository;
import ca.iconish.notification.entity.Notification;
import org.springframework.stereotype.Component;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

@Component
public class NotificationLoggerComponent {
    private Logger logger = LoggerFactory.getLogger(NotificationLoggerComponent.class);
    final NotificationRepository notificationRepository;

    public NotificationLoggerComponent(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    public void logNotif(Notification notification){
        logger.info("__logNotif(), input: {}", notification);
        notificationRepository.save(notification);

    }
}
