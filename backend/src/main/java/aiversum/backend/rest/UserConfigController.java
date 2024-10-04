package aiversum.backend.rest;

import aiversum.backend.model.UserConfig;
import aiversum.backend.service.UserConfigService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/config")
public class UserConfigController {

    private final UserConfigService userConfigService;

    public UserConfigController(UserConfigService userConfigService) {
        this.userConfigService = userConfigService;
    }

    @GetMapping("/{email}")
    public UserConfig getUserConfig(@PathVariable String email){
        return userConfigService.getConfigByEmail(email);
    }

    @PostMapping("/{email}")
    public UserConfig updateUserConfig(@RequestBody UserConfig config){
        String email = userConfigService.getCurrentEmail();
        return userConfigService.updateConfig(email, config);
    }

}
