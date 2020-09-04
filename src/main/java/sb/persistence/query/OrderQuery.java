package sb.persistence.query;

public class OrderQuery {
    public static final String GET_BY_STATUS = "SELECT o FROM Order o WHERE o.shipped = :status";
}
