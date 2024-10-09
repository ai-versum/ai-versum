package aiversum.backend.rest;

import aiversum.backend.model.UserConfig;
import aiversum.backend.service.UserConfigService;
import aiversum.backend.service.UserContextService;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/config")
public class UserConfigController {

    private final UserConfigService userConfigService;
    private final UserContextService userContextService;

    public UserConfigController(UserConfigService userConfigService,
                                UserContextService userContextService) {
        this.userConfigService = userConfigService;
        this.userContextService = userContextService;
    }

    @GetMapping
    public Optional<UserConfig> getUserConfig(){
        var authorizedUser = userContextService.getAuthorizedUser();
        return userConfigService.getConfigByEmail(authorizedUser.getId());
    }

    @PostMapping
    public UserConfig updateUserConfig(@RequestBody UserConfig userConfig){
        var authorizedUser = userContextService.getAuthorizedUser();
        return userConfigService.updateConfig(authorizedUser, userConfig);
    }

}
