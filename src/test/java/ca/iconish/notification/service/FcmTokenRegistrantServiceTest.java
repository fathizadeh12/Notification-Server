package ca.iconish.notification.service;

import ca.iconish.notification.rest.model.tokenRegistration.TokenRegistrationResponseModel;
import ca.iconish.notification.rest.model.tokenRegistration.TokenRegistrationRequestModel;
import ca.iconish.notification.repository.ClientRepository;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import ca.iconish.notification.entity.Client;
import org.mockito.junit.MockitoJUnitRunner;
import static org.mockito.Mockito.when;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.junit.Test;



@RunWith(MockitoJUnitRunner.class)
public class FcmTokenRegistrantServiceTest {
    @InjectMocks
    FcmTokenRegistrantService fcmTokenRegistrantService;
    @Mock
    ClientRepository clientRepository;

    @Test
    public void when_request_input_is_not_exist_client_should_insert_to_database() {
        Client client = new Client();
        client.setId(1L);
        client.setOpttId("1");
        client.setFcmToken("teken1");

        TokenRegistrationRequestModel requestModel = new TokenRegistrationRequestModel();
        requestModel.setToken("token2");
        requestModel.setUserId("2");

//        mock repository layer
        when(clientRepository.findClientByOpttId("1")).thenReturn(client);
        when(clientRepository.save(any(Client.class))).thenReturn(new Client());

        TokenRegistrationResponseModel result = fcmTokenRegistrantService.registerFcmToken(requestModel);

        assertThat(result.getMessage()).isEqualTo("client has been registered");
    }


    @Test
    public void when_request_input_is_exist_client_should_update_in_database() {
        Client client = new Client();
        client.setId(1L);
        client.setOpttId("1");
        client.setFcmToken("teken1");

        TokenRegistrationRequestModel requestModel = new TokenRegistrationRequestModel();
        requestModel.setToken("token");
        requestModel.setUserId("1");

//        mock repository layer
        when(clientRepository.findClientByOpttId("1")).thenReturn(client);
        when(clientRepository.save(any(Client.class))).thenReturn(new Client());

        TokenRegistrationResponseModel result = fcmTokenRegistrantService.registerFcmToken(requestModel);

        assertThat(result.getMessage()).isEqualTo("client token has been updated");
    }

    @Test(expected = IllegalArgumentException.class)
    public void when_token_is_null_should_return_IllegalArgumentException() {
        Client client = new Client();
        client.setId(1L);
        client.setOpttId("1");
        client.setFcmToken("teken1");

 //        token in request model is null
        TokenRegistrationRequestModel requestModel = new TokenRegistrationRequestModel();
        requestModel.setToken(null);
        requestModel.setUserId("1");

//        mock repository layer
        when(clientRepository.findClientByOpttId("1")).thenReturn(client);
        when(clientRepository.save(any(Client.class))).thenReturn(new Client());

         fcmTokenRegistrantService.registerFcmToken(requestModel);

    }

    @Test(expected = IllegalArgumentException.class)
    public void when_userId_is_null_should_return_IllegalArgumentException() {
        Client client = new Client();
        client.setId(1L);
        client.setOpttId("1");
        client.setFcmToken("teken1");

//        userId in request model is null
        TokenRegistrationRequestModel requestModel = new TokenRegistrationRequestModel();
        requestModel.setToken("ticken");
        requestModel.setUserId(null);

//        mock repository layer
        when(clientRepository.findClientByOpttId("1")).thenReturn(client);
        when(clientRepository.save(any(Client.class))).thenReturn(new Client());

        fcmTokenRegistrantService.registerFcmToken(requestModel);

    }
}
