package aiversum.backend.model;

import dev.langchain4j.web.search.WebSearchEngine;
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

    private boolean ollamaEnabled;
    private String ollamaBaseUrl;

    private boolean openaiEnabled;
    private String openaiApiKey;

    private boolean vertexaiEnabled;
    private String vertexaiApiKey;
    private String vertexaiProjectName;
    private String vertexaiLocation;

    private SearchEngine searchEngine;
    private String searchEngineApiKey;

    private String imageSize;
    private String imageStyle;

}
