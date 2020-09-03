package sb;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sb.domain.User;
import sb.service.UserService;

import java.util.List;

@SpringBootApplication
@RestController
public class Application {

    @Autowired
    private UserService service;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @RequestMapping("/")
    public List<User> hello(){
        return service.getAll();
    }
}
