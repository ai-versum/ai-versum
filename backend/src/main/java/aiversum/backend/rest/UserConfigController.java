package aiversum.backend.rest;

import aiversum.backend.model.UserConfig;
import aiversum.backend.rest.dto.UserConfigDto;
import aiversum.backend.rest.mapper.UserConfigMapper;
import aiversum.backend.rest.mapper.UserConfigMapperImpl;
import aiversum.backend.service.UserConfigService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/config")
public class UserConfigController {
    private final UserConfigService userConfigService;
    private final UserConfigMapper userConfigMapper = new UserConfigMapperImpl();

    public UserConfigController(UserConfigService userConfigService) {
        this.userConfigService = userConfigService;
    }

    @GetMapping
    public UserConfigDto getUserConfig() {
        UserConfig configByEmail = userConfigService.getConfig();
        return userConfigMapper.toDto(configByEmail);
    }

    @PostMapping
    public UserConfigDto updateUserConfig(UserConfigDto userConfig) {
        UserConfig updatedUserConfig = userConfigService.updateConfig(userConfig);
        return userConfigMapper.toDto(updatedUserConfig);
    }

}
