package sb.domain;

import java.sql.Date;

public class Order {

    private int id;
    private boolean shipped;
    private int submittedBy;
    private Date submittedAt;

    public Order() {
    }

    public Order(int id, boolean shipped, int submittedBy, Date submittedAt) {
        this.id = id;
        this.shipped = shipped;
        this.submittedBy = submittedBy;
        this.submittedAt = submittedAt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isShipped() {
        return shipped;
    }

    public void setShipped(boolean shipped) {
        this.shipped = shipped;
    }

    public int getSubmittedBy() {
        return submittedBy;
    }

    public void setSubmittedBy(int submittedBy) {
        this.submittedBy = submittedBy;
    }

    public Date getSubmittedAt() {
        return submittedAt;
    }

    public void setSubmittedAt(Date submittedAt) {
        this.submittedAt = submittedAt;
    }
}
