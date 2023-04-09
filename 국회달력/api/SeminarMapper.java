package com.example.nac.Mapper;

import com.example.nac.model.SEMINAR;
import com.example.nac.model.bonsche;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

//DB에서 정기회의 정보를 읽어와 정기회의 모델 객체에 맵핑
@Mapper
public interface SeminarMapper {
    @Select("SELECT * FROM project2022.SEMINAR where SDATE = #{date}")
    List<SEMINAR> GetSeminarDate(@Param("date") String date);

    @Select("SELECT * FROM project2022.SEMINAR where SDATE like '%${month}%'")
    List<SEMINAR> GetSeminarMonth(@Param("month") String month);

    @Select("SELECT * FROM project2022.SEMINAR where TITLE like '%${title}%'")
    List<bonsche> SearchSEMINARTitle(@Param("title") String title);
}
