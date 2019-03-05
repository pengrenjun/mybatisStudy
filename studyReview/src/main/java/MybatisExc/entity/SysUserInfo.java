package MybatisExc.entity;

/**
 * @Description 用户详细信息
 * @Author pengrj
 * @CreateDate 2018-05-15 下午 3:45
 * @Version 1.0
 */
public class SysUserInfo {

    private  Long  id;

    private String user_id;

    private String location;

    private String phone;

    private String work;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getWork() {
        return work;
    }

    public void setWork(String work) {
        this.work = work;
    }
}
