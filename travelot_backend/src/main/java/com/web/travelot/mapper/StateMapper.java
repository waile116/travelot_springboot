package com.web.travelot.mapper;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import com.web.travelot.po.State;

@Mapper
public interface StateMapper {
    @Select("select * from state order by id")
    public List<State> listState();
    @Select("select * from state where id=#{stateId}")
    public State getStateById(Integer stateId);
}