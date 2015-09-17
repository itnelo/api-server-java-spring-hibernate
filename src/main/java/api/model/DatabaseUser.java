package api.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name="databases_users")
@ToString
public class DatabaseUser {

    @Getter @Setter
    @Column(name = "database_user_id", unique = true)
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long databaseUserId;

    @Getter @Setter
    @Column(name = "database_user_name", nullable = false)
    private String databaseUserName;

    @Getter @Setter
    @Column(name = "database_user_pass", nullable = false)
    private String databaseUserPass;

}
