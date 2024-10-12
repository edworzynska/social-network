import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Entity
@Table(name="Accounts")

public class Account {
    @Getter
    @Id
    @GeneratedValue
    private Long id;

    @OneToMany(mappedBy="account")
    Set<Post> posts;

    @Getter
    @Setter
    @Column(name="email_address")
    private String emailAddress;

    @Getter
    @Setter
    @Column(name="username")
    private String username;

    public Account(){}

}