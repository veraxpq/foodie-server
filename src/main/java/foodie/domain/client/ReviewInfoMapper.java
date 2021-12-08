package foodie.domain.client;

import foodie.domain.model.ReviewInfo;
import foodie.domain.model.ReviewInfoExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface ReviewInfoMapper {
    long countByExample(ReviewInfoExample example);

    int deleteByExample(ReviewInfoExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(ReviewInfo record);

    int insertSelective(ReviewInfo record);

    List<ReviewInfo> selectByExampleWithRowbounds(ReviewInfoExample example, RowBounds rowBounds);

    List<ReviewInfo> selectByExample(ReviewInfoExample example);

    ReviewInfo selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") ReviewInfo record, @Param("example") ReviewInfoExample example);

    int updateByExample(@Param("record") ReviewInfo record, @Param("example") ReviewInfoExample example);

    int updateByPrimaryKeySelective(ReviewInfo record);

    int updateByPrimaryKey(ReviewInfo record);
}