package com.banktransferenceapichallenge.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.banktransferenceapichallenge.domain.user.User;
import com.banktransferenceapichallenge.dtos.NotificationRequestDTO;

@Service
public class NotificationService {
    @Autowired
    private RestTemplate restTemplate;

    public void sendNotification(User user, String message) throws Exception {
        NotificationRequestDTO notificationRequest = new NotificationRequestDTO(user.getEmail(), message);
        ResponseEntity<String> response = restTemplate.postForEntity("http://o4d9z.mocklab.io/notify", notificationRequest, String.class);
        if (response.getStatusCode() != HttpStatus.OK) {
            System.out.println("Error sending notification");
            throw new Exception("Error sending notification");
        }
    }
}
