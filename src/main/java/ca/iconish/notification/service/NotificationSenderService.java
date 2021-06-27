package ca.iconish.notification.service;

import ca.iconish.notification.rest.model.notification.SendNotificationRequestModel;
import ca.iconish.notification.rest.model.notification.SendNotificationResponseModel;
import ca.iconish.notification.constants.NotificationStatusEnum;
import ca.iconish.notification.repository.ClientRepository;
import ca.iconish.notification.rest.model.NotifModel;
import ca.iconish.notification.entity.Notification;
import org.springframework.stereotype.Service;
import ca.iconish.notification.entity.Client;
import javassist.NotFoundException;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import java.util.Date;


@Service
public class NotificationSenderService {
    private final Logger logger = LoggerFactory.getLogger(NotificationSenderService.class);

    final ClientRepository clientRepository;
    final FirebaseMessagingService firebaseMessagingService;
    final NotificationLoggerComponent notificationLoggerComponent;

    public NotificationSenderService(ClientRepository clientRepository, FirebaseMessagingService firebaseMessagingService, NotificationLoggerComponent notificationLoggerComponent) {
        this.clientRepository = clientRepository;
        this.firebaseMessagingService = firebaseMessagingService;
        this.notificationLoggerComponent = notificationLoggerComponent;
    }

    public SendNotificationResponseModel sendNotification(SendNotificationRequestModel requestModel) throws  NotFoundException {
        logger.info("__SendNotificationRequestModel(), input:{}", requestModel);
        String fcmToken = findFcmTokenByOpttId(requestModel.getOpttId());

//        prepare notifModel for passing to sendNotification method
        NotifModel notifModel = new NotifModel();
        notifModel.setSubject(requestModel.getSubject());
        notifModel.setContent(requestModel.getContent());
        notifModel.setData(requestModel.getData());
        notifModel.setImage(null);

//        prepare notification object for logging
        Notification notification = new Notification();
        notification.setClient(clientRepository.findClientByOpttId(requestModel.getOpttId()));
        notification.setTitle(requestModel.getSubject());
        notification.setImage(requestModel.getImage());
        notification.setSendDate(new Date());
        notification.setBody(requestModel.getContent());

        if (fcmToken != null) {
            logger.info("__sending notification...");

            try{
                firebaseMessagingService.sendNotification(notifModel, fcmToken);
            }catch (Exception e){
                notification.setStatus(NotificationStatusEnum.FAILED);
                notificationLoggerComponent.logNotif(notification);
                throw new IllegalArgumentException();
            }

            logger.info("__logging sent notification... ");
            notification.setStatus(NotificationStatusEnum.SENT);
            notificationLoggerComponent.logNotif(notification);

            return new SendNotificationResponseModel("notification has been sent");
        }
        throw new NotFoundException("client by this opttId: " + requestModel.getOpttId() + " is not exist");

    }

    String findFcmTokenByOpttId(String opttId) {
        Client client = clientRepository.findClientByOpttId(opttId);
        if (client != null) {
            return client.getFcmToken();
        }
        return null;
    }
}



