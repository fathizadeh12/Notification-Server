package ca.iconish.notification.service;

import ca.iconish.notification.rest.model.notification.SendNotificationResponseModel;
import ca.iconish.notification.rest.model.notification.SendNotificationRequestModel;
import ca.iconish.notification.repository.NotificationRepository;
import com.google.firebase.messaging.FirebaseMessagingException;
import ca.iconish.notification.repository.ClientRepository;
import static org.assertj.core.api.Assertions.assertThat;
import ca.iconish.notification.rest.model.NotifModel;
import ca.iconish.notification.entity.Notification;
import static org.mockito.ArgumentMatchers.any;
import ca.iconish.notification.entity.Client;
import org.mockito.junit.MockitoJUnitRunner;
import static org.mockito.Mockito.when;
import javassist.NotFoundException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import lombok.SneakyThrows;
import org.mockito.Mock;
import org.junit.Test;


@RunWith(MockitoJUnitRunner.class)
public class NotificationSenderServiceTest {

    @InjectMocks
    NotificationSenderService notificationSenderService;

    @Mock
    FirebaseMessagingService firebaseMessagingService;
    @Mock
    NotificationRepository notificationRepository;
    @Mock
    ClientRepository clientRepository;

    @SneakyThrows
    @Test(expected = NotFoundException.class)
    public void when_notification_client_not_exist_throw_exception() {

        when(clientRepository.findClientByOpttId(any(String.class))).thenReturn(null);
        when(notificationRepository.save(any(Notification.class))).thenReturn(new Notification());
        when(firebaseMessagingService.sendNotification(new NotifModel(), "")).thenReturn("");

        SendNotificationRequestModel sendNotificationRequestModel = new SendNotificationRequestModel();
        sendNotificationRequestModel.setOpttId("22");
        sendNotificationRequestModel.setSubject("subject");
        sendNotificationRequestModel.setContent("content");

        notificationSenderService.sendNotification(sendNotificationRequestModel);

    }

    @SneakyThrows
    @Test
    public void when_notification_client_is_exist_and_FcmToken_is_valid() {

        Client client = new Client();
        client.setFcmToken("token");
        client.setOpttId("ottId");
        when(clientRepository.findClientByOpttId(any(String.class))).thenReturn(client);
        when(notificationRepository.save(any(Notification.class))).thenReturn(new Notification());
        when(firebaseMessagingService.sendNotification(any(NotifModel.class), any(String.class))).thenReturn("");

        SendNotificationRequestModel sendNotificationRequestModel = new SendNotificationRequestModel();
        sendNotificationRequestModel.setOpttId("22");
        sendNotificationRequestModel.setSubject("subject");
        sendNotificationRequestModel.setContent("content");

        SendNotificationResponseModel responseModel = notificationSenderService.sendNotification(sendNotificationRequestModel);
        assertThat(responseModel.getMessage()).isEqualTo("notification has been sent");
    }


    @SneakyThrows
    @Test(expected = IllegalArgumentException.class)
    public void when_notification_client_is_exist_and_FcmToken_is_not_valid() {

        Client client = new Client();
        client.setFcmToken("token");
        client.setOpttId("ottId");
        when(clientRepository.findClientByOpttId(any(String.class))).thenReturn(client);
        when(notificationRepository.save(any(Notification.class))).thenReturn(new Notification());
        when(firebaseMessagingService.sendNotification(any(NotifModel.class), any(String.class))).thenThrow(FirebaseMessagingException.class);

        SendNotificationRequestModel sendNotificationRequestModel = new SendNotificationRequestModel();
        sendNotificationRequestModel.setOpttId("22");
        sendNotificationRequestModel.setSubject("subject");
        sendNotificationRequestModel.setContent("content");

         notificationSenderService.sendNotification(sendNotificationRequestModel);
    }
}
