package ca.iconish.notification.rest.model.tokenRegistration;

import lombok.Data;

@Data
public class TokenRegistrationRequestModel {
   private String userId;
   private String token;
}
