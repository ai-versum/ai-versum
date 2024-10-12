package aiversum.backend.rest;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth/")
public class AuthController {
    @GetMapping("check-session")
    public ResponseEntity<String> checkSession(HttpServletRequest request) {
        HttpSession session = request.getSession(false);

        if (session != null) {
            return ResponseEntity.ok().body("Session is valid");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Session is not valid");
        }
    }
}
