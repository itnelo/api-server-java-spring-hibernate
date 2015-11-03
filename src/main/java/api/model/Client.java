package api.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import javax.persistence.*;

@Entity
@Table(name="clients")
@ToString
public class Client {

    @Getter @Setter
    @Column(name = "client_id", unique = true)
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long clientId;

    @Getter @Setter
    @Column(name = "client_key", nullable = false, length = 32)
    private String clientKey;

    @Getter @Setter
    @Column(name = "plugin_api_key", nullable = false, length = 32)
    private String accessKey;

    @Getter @Setter
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "database_id")
    private Database database;

    @Getter @Setter
    @Transient
    private EntityManager entityManager;

//    @Getter @Setter private Long client_type_id;
//    @Getter @Setter private String client_title;
//    @Getter @Setter private Long database_id;
//    @Getter @Setter private boolean status;
//    @Getter @Setter private String activate_dt;
}
