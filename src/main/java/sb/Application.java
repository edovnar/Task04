package sb;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sb.domain.Order;
import sb.domain.User;
import sb.persistence.dao.OrderDAO;
import sb.persistence.dao.UserDAO;


@SpringBootApplication
@RestController
public class Application {

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private OrderDAO orderDAO;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @RequestMapping("/")
    public Order hello(){
        return orderDAO.get(2);
    }
}
