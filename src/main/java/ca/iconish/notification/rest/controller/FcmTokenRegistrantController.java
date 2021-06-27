package ca.iconish.notification.rest.controller;

import ca.iconish.notification.rest.model.tokenRegistration.TokenRegistrationResponseModel;
import ca.iconish.notification.rest.model.tokenRegistration.TokenRegistrationRequestModel;
import ca.iconish.notification.service.FcmTokenRegistrantService;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import io.swagger.annotations.ApiOperation;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import sun.awt.SunHints;


@RestController
@RequestMapping("/api")
public class FcmTokenRegistrantController {
    private Logger logger = LoggerFactory.getLogger(FcmTokenRegistrantController.class);
    FcmTokenRegistrantService fcmTokenRegistrantService;

    public FcmTokenRegistrantController(FcmTokenRegistrantService fcmTokenRegistrantService) {
        this.fcmTokenRegistrantService = fcmTokenRegistrantService;
    }

    @ApiOperation(value = "update or insert new client to database", httpMethod = "POST")
    @PostMapping("/fcm-token")
    public ResponseEntity<TokenRegistrationResponseModel> registerClientFcmToken(@RequestBody TokenRegistrationRequestModel requestModel) {
        logger.info("__called url: /fcm-token ");
        TokenRegistrationResponseModel responseModel = fcmTokenRegistrantService.registerFcmToken(requestModel);
        return ResponseEntity.ok(responseModel);
    }


}
