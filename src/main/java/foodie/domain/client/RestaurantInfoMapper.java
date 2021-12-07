package foodie.domain.client;

import foodie.domain.model.RestaurantInfo;
import foodie.domain.model.RestaurantInfoExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface RestaurantInfoMapper {
    long countByExample(RestaurantInfoExample example);

    int deleteByExample(RestaurantInfoExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(RestaurantInfo record);

    int insertSelective(RestaurantInfo record);

    List<RestaurantInfo> selectByExampleWithRowbounds(RestaurantInfoExample example, RowBounds rowBounds);

    List<RestaurantInfo> selectByExample(RestaurantInfoExample example);

    RestaurantInfo selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") RestaurantInfo record, @Param("example") RestaurantInfoExample example);

    int updateByExample(@Param("record") RestaurantInfo record, @Param("example") RestaurantInfoExample example);

    int updateByPrimaryKeySelective(RestaurantInfo record);

    int updateByPrimaryKey(RestaurantInfo record);
}