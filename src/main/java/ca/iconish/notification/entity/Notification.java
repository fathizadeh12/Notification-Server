package ca.iconish.notification.entity;

import ca.iconish.notification.constants.NotificationStatusEnum;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "notification")
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "body")
    private String body;

    @Column(name = "image")
    private String image;

    @Column(name="Send_Date")
    private Date sendDate;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private NotificationStatusEnum status;

    @ManyToOne
    @JoinColumn(name = "client_id", nullable = true)
    private Client client;
}