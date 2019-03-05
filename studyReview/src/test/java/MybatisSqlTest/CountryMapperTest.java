package MybatisSqlTest;

import MybatisExc.Util.SqlsessionUtil;
import MybatisExc.entity.Country;
import MybatisExc.mapper.CountryMapper;
import org.junit.Test;
import java.io.IOException;

public class CountryMapperTest {

	@Test
	public void selectByPrimaryKey() throws IOException {
		try {
			CountryMapper countryMapper = SqlsessionUtil.getMapperByClass(CountryMapper.class);
			Country country = countryMapper.selectByPrimaryKey(1);
			System.out.println(country.getCountryname());
		} finally {
			SqlsessionUtil.closeSession();
		}
	}

}
