package ca.iconish.notification.service;

import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.FirebaseMessaging;
import ca.iconish.notification.rest.model.NotifModel;
import com.google.firebase.messaging.Notification;
import com.google.firebase.messaging.Message;
import org.springframework.stereotype.Service;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

@Service
public class FirebaseMessagingService {
    private Logger logger = LoggerFactory.getLogger(FirebaseMessagingService.class);
    private final FirebaseMessaging firebaseMessaging;

    public FirebaseMessagingService(FirebaseMessaging firebaseMessaging) {
        this.firebaseMessaging = firebaseMessaging;
    }


    public String sendNotification(NotifModel notifModel, String fcmToken) throws FirebaseMessagingException {
        logger.info("__sendNotification(), input: notifModel: {}, fcmToken: {}", notifModel,fcmToken);
        Notification notification = Notification
                .builder()
                .setTitle(notifModel.getSubject())
                .setBody(notifModel.getContent())
                .build();

        Message message = Message
                .builder()
                .setToken(fcmToken)
                .setNotification(notification).putAllData(notifModel.getData())
                .build();

        return firebaseMessaging.send(message);
    }

}