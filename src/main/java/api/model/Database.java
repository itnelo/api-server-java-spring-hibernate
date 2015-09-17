package api.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="[databases]")
@ToString
public class Database {

    @Getter @Setter
    @Column(name = "database_id", unique = true)
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long databaseId;

    @Getter @Setter
    @Column(name = "database_name", nullable = false, length = 50)
    private String databaseName;

    @Getter @Setter
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
        name="dbuser2dbname",
        joinColumns = @JoinColumn(name = "database_id"),
        inverseJoinColumns = @JoinColumn(name = "database_user_id")
    )
    private List<DatabaseUser> databaseUsers;

    public DatabaseUser getDatabaseUser() {
        return databaseUsers.get(0);
    }

}
