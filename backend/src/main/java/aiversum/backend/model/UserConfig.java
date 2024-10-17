package aiversum.backend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "userConfigs")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserConfig {

    @Id
    private Long id;

    @Column
    private String imageSize;

    @Column
    private String imageStyle;

    @Column
    private String apiKey;

    @Column
    private boolean enabled;

    @Column
    private String projectName;

    @Column
    private String location;
}
