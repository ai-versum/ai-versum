package aiversum.backend.service;

import aiversum.backend.model.User;
import aiversum.backend.model.UserConfig;
import aiversum.backend.repository.UserConfigRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserConfigService {


    private final UserConfigRepository userConfigRepository;

    public UserConfigService(UserConfigRepository userConfigRepository) {
        this.userConfigRepository = userConfigRepository;
    }

    public Optional<UserConfig> getConfigByEmail(Long id) {
        return userConfigRepository.findById(id);
    }

    public UserConfig updateConfig(User authorizedUser, UserConfig newConfig) {
        UserConfig userConfig = userConfigRepository
                .findById(authorizedUser.getId())
                .orElseGet(() -> {
                    UserConfig newUserConfig = new UserConfig();
                    newUserConfig.setId(authorizedUser.getId());
                    return newUserConfig;
                });
        userConfig.setApiKey(newConfig.getApiKey());
        userConfig.setImageSize(newConfig.getImageSize());
        userConfig.setImageStyle(newConfig.getImageStyle());
        return userConfigRepository.save(userConfig);
    }
}
