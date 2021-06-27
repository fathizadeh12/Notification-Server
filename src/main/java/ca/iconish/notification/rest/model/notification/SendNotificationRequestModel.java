package ca.iconish.notification.rest.model.notification;

import lombok.Data;

import java.util.Map;

@Data
public class SendNotificationRequestModel {
    private String opttId;
    private String subject;
    private String content;
    private Map<String, String> data;
    private String image;
}
