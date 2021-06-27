package ca.iconish.notification.rest.controller;

import ca.iconish.notification.rest.model.notification.SendNotificationRequestModel;
import ca.iconish.notification.rest.model.notification.SendNotificationResponseModel;
import ca.iconish.notification.service.NotificationSenderService;
import javassist.NotFoundException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.http.ResponseEntity;
import io.swagger.annotations.ApiOperation;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

@RestController
@RequestMapping("/api")
public class NotifSenderController {
    private Logger logger = LoggerFactory.getLogger(NotifSenderController.class);
    final
    NotificationSenderService notificationSenderService;

    public NotifSenderController(NotificationSenderService notificationSenderService) {
        this.notificationSenderService = notificationSenderService;
    }

    @ApiOperation(value = "sending notification by FCM", httpMethod = "POST")
    @PostMapping("/notification")
    public ResponseEntity<SendNotificationResponseModel> sendNotification(@RequestBody SendNotificationRequestModel requestModel) throws NotFoundException {
       logger.info("__called url: /notification, posted data: {}", requestModel);
        SendNotificationResponseModel responseModel=  notificationSenderService.sendNotification(requestModel);
        return ResponseEntity.ok(responseModel);
    }

}
