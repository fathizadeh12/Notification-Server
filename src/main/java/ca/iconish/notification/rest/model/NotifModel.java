package ca.iconish.notification.rest.model;

import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Data
public class NotifModel {
    private String subject;
    private String content;
    private Map<String, String> data;
    private String image;
}
