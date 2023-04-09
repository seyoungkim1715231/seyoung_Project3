package com.example.nac.Mapper;

import com.example.nac.model.MEMO;
import org.apache.ibatis.annotations.*;

import java.util.List;

//DB에서 본회의 정보를 읽어와 본회의 모델 객체에 맵핑
@Mapper
public interface Memomapper {
    @Insert("INSERT INTO project2022.MEMO " +
            "(ID, Contents, DATE) " +
            "VALUES (#{id}, #{Contents}, #{date});")
    boolean PushMemo(
            @Param("id") String id,
            @Param("Contents") String Contents,
            @Param("date") String date);

    @Delete("DELETE FROM project2022.MEMO WHERE (num = #{num});")
    boolean PopMemo(@Param("num") String num);

    @Select("SELECT * FROM project2022.MEMO where ID = #{id};")
    List<MEMO> GetMemo( @Param("id") String id);

    @Select("SELECT * FROM project2022.MEMO where (DATE like '%${date}%' and ID like #{id});")
    List<MEMO> GetMemoDate(@Param("date") String date, @Param("id") String id);

}