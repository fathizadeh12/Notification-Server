package ca.iconish.notification.entity;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;


@Setter
@Getter
@Entity
@Table(name = "client")
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "optt_id")
    private String opttId;

    @Column(name = "fcm_token")
    private String fcmToken;

    @Column(name = "registration_date")
    private Date registrationDate;
    @OneToMany(mappedBy = "client")
    private Set<Notification> notificationSet;

}
