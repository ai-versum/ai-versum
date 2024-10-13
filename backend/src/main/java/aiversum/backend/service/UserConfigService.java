package aiversum.backend.service;

import aiversum.backend.config.properties.PropertiesConfig;
import aiversum.backend.model.UserConfig;
import aiversum.backend.repository.UserConfigRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserConfigService {
    private final UserConfigRepository userConfigRepository;
    private final UserContextService userContextService;
    private final PropertiesConfig propertiesConfig;

    public UserConfigService(UserConfigRepository userConfigRepository,
                             UserContextService userContextService,
                             PropertiesConfig propertiesConfig) {
        this.userConfigRepository = userConfigRepository;
        this.userContextService = userContextService;
        this.propertiesConfig = propertiesConfig;
    }

    public UserConfig getConfig() {
        var authorizedUser = userContextService.getAuthorizedUser();
        Optional<UserConfig> userConfig = userConfigRepository.findById(authorizedUser.getId());
        if (userConfig.isPresent()) {
            return userConfig.get();
        } else {
            UserConfig newUserConfig = new UserConfig();
            newUserConfig.setId(authorizedUser.getId());
            newUserConfig.setImageSize(propertiesConfig.defaultOptions().imageSize());
            newUserConfig.setImageStyle(propertiesConfig.defaultOptions().imageStyle());
            return userConfigRepository.save(newUserConfig);
        }
    }

    public UserConfig updateConfig(UserConfig newConfig) {
        var authorizedUser = userContextService.getAuthorizedUser();

        UserConfig userConfig = userConfigRepository
                .findById(authorizedUser.getId())
                .orElseThrow();
        userConfig.setApiKey(newConfig.getApiKey());
        userConfig.setImageSize(newConfig.getImageSize());
        userConfig.setImageStyle(newConfig.getImageStyle());
        return userConfigRepository.save(userConfig);
    }
}
