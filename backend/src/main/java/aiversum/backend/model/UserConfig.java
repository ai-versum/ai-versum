package aiversum.backend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "UserConfig")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserConfig {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String imageSize;

    @Column
    private String imageStyle;

    @Column
    private String apiKey;

    @ManyToOne()
    @JoinColumn(name = "user_id")
    private User user;
}
