package me.joshua.day_30;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Person {
    @Id @GeneratedValue
    private Long id;

    private String email;
    private String name;

    @Temporal(TemporalType.TIMESTAMP)
    private Date joined;
}
