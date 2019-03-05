package MybatisExc.Enum;

public enum Status {

    NEW("新建","new"),
    APPROVING("审批中","approving"),
    APPROVED("审批完成","approved");

    private String statusCn;

    private String statusEn;

    Status(String statusCn, String statusEn) {
        this.statusCn = statusCn;
        this.statusEn = statusEn;
    }

    public String getStatusCn() {
        return statusCn;
    }

    public void setStatusCn(String statusCn) {
        this.statusCn = statusCn;
    }

    public String getStatusEn() {
        return statusEn;
    }

    public void setStatusEn(String statusEn) {
        this.statusEn = statusEn;
    }
}
