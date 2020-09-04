package sb.persistence.query;

public class OrderQuery {
    public static final String GET_BY_STATUS = "SELECT o FROM order WHERE shipped = :status";
    public static final String getByUser = "Select * from orders where submittedBy = :id";
}
