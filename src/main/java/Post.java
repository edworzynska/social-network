import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Table(name="Posts")

public class Post {
    @Id
    @GeneratedValue
    private Long id;

    @Getter
    @Setter
    @Column(name="title")
    private String title;

    @Getter
    @Setter
    @Column(name="contents")
    private String contents;

    @Getter
    @Setter
    @Column(name="views")
    private long views;

    @Getter
    @Setter
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name="account_id")
    private Account account;

    public Post(){}

}
