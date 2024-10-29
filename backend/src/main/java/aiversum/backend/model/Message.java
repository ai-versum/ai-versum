package aiversum.backend.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "messages")
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chat_id", nullable = false)
    private Chat chat;

    @Column(nullable = false, columnDefinition = "TEXT")
    @Lob
    private String content;

    @Column(nullable = false)
    private String sender; // "USER" or "BOT"

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime timestamp;

    // Constructors, getters, and setters
    // ... standard constructors and accessors for all fields
}
