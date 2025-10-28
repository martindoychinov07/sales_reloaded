package sales.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldNameConstants;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@FieldNameConstants
@Entity
@Table(name = "\"user\"")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_id_gen")
//    @SequenceGenerator(name = "product_id_gen", sequenceName = "product_sequence", allocationSize = 1)
    @Getter
    private long id;

    @NonNull
    @Column(nullable = false, unique = true)
    @Getter @Setter
    private String username;

    @NonNull
    @Column(nullable = false)
    @Getter @Setter
    private String password;

    @NonNull
    @Column(nullable = false)
    @Getter @Setter
    private String role = "user";

    @NonNull
    @Column(nullable = false)
    @Getter @Setter
    private long code;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }
}