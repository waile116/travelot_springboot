package com.web.travelot.mapper;

import com.web.travelot.po.Attraction;
import com.web.travelot.po.Ticket;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface AttractionMapper {
    @Select("select * from attraction order by rand()")
    public List<Attraction> listAttractionRandom();
    @Select("select * from attraction where state_id=#{stateId} order by id")
    public List<Attraction> listAttractionById(Integer stateId);
    @Select("select * from attraction where id=#{attractionId}")
    public Attraction getAttractionById(Integer attractionId);
    @Insert("insert into attraction (state_id, name, description, location, price, open_t, rating, attr_img, create_t, update_t) values " +
            "(#{stateId}, #{name}, #{desc}, #{location}, #{price}, #{openTime}, #{rating}, #{attractionImg}, NOW(), NOW())")
    public int saveAttraction(Attraction attraction);
    @Update("UPDATE attraction SET state_id=#{stateId}, name=#{name}, description=#{desc}, location=#{location}, " +
            "price=#{price}, open_t=#{openTime}, rating=#{rating}, attr_img=#{attractionImg}, map_img=#{mapImg}, map_link=#{mapLink}, " +
            "update_t=NOW() WHERE id=#{attractionId}")
    public int updateAttraction(Attraction attraction);
    @Select("select * from ticket")
    public List<Ticket> listTicket();
    @Select("select * from ticket where attraction_id=#{attractionId} order by price")
    public List<Ticket> listTicketById(Integer attractionId);
    @Insert("insert into ticket (attraction_id, name, description, price, amount, create_t, update_t) values " +
            "(#{attractionId}, #{name}, #{desc}, #{price}, #{amount}, NOW(), NOW())")
    public int saveTicket(Ticket ticket);
    @Update("UPDATE ticket SET attraction_id=#{attractionId}, name=#{name}, description=#{desc}, price=#{price}, " +
            "amount=#{amount}, update_t=NOW() WHERE id=#{ticketId}")
    public int updateTicket(Ticket ticket);
}