package MybatisExc.Enum.EnumTypeHandler;

import MybatisExc.Enum.Status;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description 单据状态类型处理器
 * @Author pengrj
 * @CreateDate 2018-05-17 上午 11:17
 * @Version 1.0
 */
public class StatusTypeHandler implements TypeHandler<Status> {

    private final Map<String, Status> statusMap = new HashMap<String, Status>();

    public StatusTypeHandler() {
        for(Status status : Status.values()){
            statusMap.put(status.getStatusEn(), status);
        }
    }

    public StatusTypeHandler(Class<?> type) {
        this();
    }
    @Override
    public void setParameter(PreparedStatement ps, int i, Status parameter, JdbcType jdbcType) throws SQLException {
        ps.setString(i,parameter.getStatusEn());

    }

    @Override
    public Status getResult(ResultSet rs, String columnName) throws SQLException {
        String value = rs.getString(columnName);
        return statusMap.get(value);
    }

    @Override
    public Status getResult(ResultSet rs, int columnIndex) throws SQLException {
        String value = rs.getString(columnIndex);
        return statusMap.get(value);
    }

    @Override
    public Status getResult(CallableStatement cs, int columnIndex) throws SQLException {
        String value = cs.getString(columnIndex);
        return statusMap.get(value);
    }
}
