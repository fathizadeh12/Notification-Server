# Notification-Server

Description: 
This service sends notifications to registered client apps (IOS and Android)  by Firebase Cloud Messaging (FCM)  and logs all notification requests.

![alt text](https://i.pinimg.com/originals/9e/c5/74/9ec574313781ce5da9f343e2c4272951.jpg)

Sending notification steps: 

1.	client (every application) registers himself in FCM and gets a specific token.

2.	Client send himself token and opttId(user identifier) to notification server and register himself in the notification system.

3.	Every third-party application that wants to send a notification to its clients, should make a request to the notification server.

4.	The notification server sends notifications through FCM to the specific client.

5.	The notification server saves all of the transactions(notification requests) information and the third-party applications can get reports and track his notifications

