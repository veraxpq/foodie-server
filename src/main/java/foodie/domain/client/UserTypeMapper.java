package foodie.domain.client;

import foodie.domain.model.UserType;
import foodie.domain.model.UserTypeExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface UserTypeMapper {
    long countByExample(UserTypeExample example);

    int deleteByExample(UserTypeExample example);

    int insert(UserType record);

    int insertSelective(UserType record);

    List<UserType> selectByExampleWithRowbounds(UserTypeExample example, RowBounds rowBounds);

    List<UserType> selectByExample(UserTypeExample example);

    int updateByExampleSelective(@Param("record") UserType record, @Param("example") UserTypeExample example);

    int updateByExample(@Param("record") UserType record, @Param("example") UserTypeExample example);
}