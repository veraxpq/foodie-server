package foodie.domain.client;

import foodie.domain.model.BusinessInfo;
import foodie.domain.model.BusinessInfoExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface BusinessInfoMapper {
    long countByExample(BusinessInfoExample example);

    int deleteByExample(BusinessInfoExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(BusinessInfo record);

    int insertSelective(BusinessInfo record);

    List<BusinessInfo> selectByExampleWithRowbounds(BusinessInfoExample example, RowBounds rowBounds);

    List<BusinessInfo> selectByExample(BusinessInfoExample example);

    BusinessInfo selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") BusinessInfo record, @Param("example") BusinessInfoExample example);

    int updateByExample(@Param("record") BusinessInfo record, @Param("example") BusinessInfoExample example);

    int updateByPrimaryKeySelective(BusinessInfo record);

    int updateByPrimaryKey(BusinessInfo record);
}