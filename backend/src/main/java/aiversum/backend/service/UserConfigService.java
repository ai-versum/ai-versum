package aiversum.backend.service;

import aiversum.backend.config.properties.PropertiesConfig;
import aiversum.backend.model.User;
import aiversum.backend.model.UserConfig;
import aiversum.backend.repository.UserConfigRepository;
import aiversum.backend.rest.dto.UserConfigDto;
import aiversum.backend.rest.mapper.UserConfigMapper;
import aiversum.backend.rest.mapper.UserConfigMapperImpl;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserConfigService {
    private final UserConfigRepository userConfigRepository;
    private final UserContextService userContextService;
    private final PropertiesConfig propertiesConfig;
    private final UserConfigMapper userConfigMapper = new UserConfigMapperImpl();

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
        return userConfig.orElseGet(() -> createConfig(authorizedUser));
    }

    public UserConfig updateConfig(UserConfigDto newConfig) {
        var authorizedUser = userContextService.getAuthorizedUser();

        UserConfig userConfig = userConfigMapper.fromDto(newConfig);
        userConfig.setId(authorizedUser.getId());

        return userConfigRepository.save(userConfig);
    }

    private UserConfig createConfig(User user) {
        UserConfig newUserConfig = new UserConfig();
        newUserConfig.setId(user.getId());
        newUserConfig.setImageSize(propertiesConfig.defaultOptions().imageSize());
        newUserConfig.setImageStyle(propertiesConfig.defaultOptions().imageStyle());
        newUserConfig.setOllamaBaseUrl("http://localhost:11434");
        return userConfigRepository.save(newUserConfig);
    }
}
