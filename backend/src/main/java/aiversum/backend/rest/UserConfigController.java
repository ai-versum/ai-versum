package aiversum.backend.rest;

import aiversum.backend.model.UserConfig;
import aiversum.backend.rest.dto.UserConfigDto;
import aiversum.backend.rest.mapper.UserConfigMapper;
import aiversum.backend.rest.mapper.UserConfigMapperImpl;
import aiversum.backend.service.UserConfigService;
import aiversum.backend.service.UserContextService;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/config")
public class UserConfigController {
    private final UserContextService userContextService;
    private final UserConfigService userConfigService;
    private final UserConfigMapper userConfigMapper = new UserConfigMapperImpl();

    public UserConfigController(UserConfigService userConfigService,
                                UserContextService userContextService) {
        this.userConfigService = userConfigService;
        this.userContextService = userContextService;
    }

    @GetMapping
    public Optional<UserConfigDto> getUserConfig() {
        var authorizedUser = userContextService.getAuthorizedUser();
        Optional<UserConfig> configByEmail = userConfigService.getConfigByEmail(authorizedUser.getId());
        return configByEmail.map(userConfigMapper::toDto);
    }

    @PostMapping
    public UserConfigDto updateUserConfig(@ModelAttribute UserConfig userConfig) {
        var authorizedUser = userContextService.getAuthorizedUser();
        UserConfig updatedUserConfig = userConfigService.updateConfig(authorizedUser, userConfig);
        return userConfigMapper.toDto(updatedUserConfig);
    }

}
