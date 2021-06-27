package ca.iconish.notification.service;

import ca.iconish.notification.rest.model.tokenRegistration.TokenRegistrationRequestModel;
import ca.iconish.notification.repository.ClientRepository;
import ca.iconish.notification.rest.model.tokenRegistration.TokenRegistrationResponseModel;
import org.springframework.stereotype.Service;
import ca.iconish.notification.entity.Client;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import java.util.Date;

@Service
public class FcmTokenRegistrantService {
    final ClientRepository clientRepository;
    private Logger logger = LoggerFactory.getLogger(FcmTokenRegistrantService.class);

    public FcmTokenRegistrantService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public TokenRegistrationResponseModel registerFcmToken(TokenRegistrationRequestModel requestModel) {

        logger.info("__registerFcmToken(), input {}", requestModel);

        if (requestModel.getToken()==null || requestModel.getUserId()==null){
            throw new IllegalArgumentException("Token and userId can not be null!");
        }

/*
     check client by opttId and then if already exist update fcm token
     and if not exist insert as new row to client table
 */
        Client client = clientRepository.findClientByOpttId(requestModel.getUserId());

        if (client == null) {
            client = new Client();
            client.setOpttId(requestModel.getUserId());
            client.setFcmToken(requestModel.getToken());
            client.setRegistrationDate(new Date());
            clientRepository.save(client);
            return new TokenRegistrationResponseModel("client has been registered");
        }

        client.setFcmToken(requestModel.getToken());
        clientRepository.save(client);
        return new TokenRegistrationResponseModel("client token has been updated");

    }
}
