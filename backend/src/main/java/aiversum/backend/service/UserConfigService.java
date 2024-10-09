package aiversum.backend.service;

import aiversum.backend.model.User;
import aiversum.backend.model.UserConfig;
import aiversum.backend.repository.UserConfigRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserConfigService {


    private final UserConfigRepository userConfigRepository;

    public UserConfigService(UserConfigRepository userConfigRepository) {
        this.userConfigRepository = userConfigRepository;
    }

    public UserConfig getConfigByEmail(String email) {
        return userConfigRepository.findByEmail(email)
                .orElse(new UserConfig());
    }

    public UserConfig updateConfig(String email, UserConfig newConfig){
        Optional<UserConfig> optionalConfig = userConfigRepository.findByEmail(email);
        UserConfig config = optionalConfig.orElse(new UserConfig());
        config.setId(newConfig.getId());
        config.setImageSize(newConfig.getImageSize());
        config.setImageStyle(newConfig.getImageStyle());
        config.setApiKey(newConfig.getApiKey());
        return userConfigRepository.save(config);
    }

    public User getCurrentUser(){
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
