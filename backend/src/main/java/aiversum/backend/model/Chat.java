package aiversum.backend.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "chats")
public class Chat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String userId;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime lastMessageAt;

    public Chat(String userId, String title) {
        this.userId = userId;
        this.title = title;
        this.createdAt = LocalDateTime.now();
        this.lastMessageAt = this.createdAt;
    }

    public Chat() {}

    // Getters and setters
    // ... standard getters and setters for all fields
}
