package com.example.nac.Mapper;

import com.example.nac.model.CommSche_Kong;
import com.example.nac.model.bonsche;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

//DB에서 정기회의 정보를 읽어와 정기회의 모델 객체에 맵핑
@Mapper
public interface CommKongMapper {
    @Select("SELECT * FROM project2022.COMMSCHE_KONG where MEETING_DATE = #{date}")
    List<CommSche_Kong> GetCommSche_KongDate(@Param("date") String date);

    @Select("SELECT * FROM project2022.COMMSCHE_KONG where MEETING_DATE like '%${month}%'")
    List<CommSche_Kong> GetCommSche_KongMonth(@Param("month") String month);

    @Select("SELECT * FROM project2022.COMMSCHE_KONG where TITLE like '%${title}%'")
    List<bonsche> SearchCommSche_KongTitle(@Param("title") String title);
}


CommMainMapper.java
package com.example.nac.Mapper;

import com.example.nac.model.CommSche_Main;
import com.example.nac.model.bonsche;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

//DB에서 정기회의 정보를 읽어와 정기회의 모델 객체에 맵핑
@Mapper
public interface CommMainMapper {
    @Select("SELECT * FROM project2022.COMMSCHE_MAIN where MEETING_DATE = #{date}")
    List<CommSche_Main> GetCommSche_MainDate(@Param("date") String date);

    @Select("SELECT * FROM project2022.COMMSCHE_MAIN where MEETING_DATE like '%${month}%'")
    List<CommSche_Main> GetCommSche_MainMonth(@Param("month") String month);

    @Select("SELECT * FROM project2022.COMMSCHE_MAIN where TITLE like '%${title}%'")
    List<bonsche> SearchCOMMSCHE_MAINTitle(@Param("title") String title);
}

CommSmallMapper.java 

package com.example.nac.Mapper;

import com.example.nac.model.CommSche_Small;
import com.example.nac.model.bonsche;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

//DB에서 정기회의 정보를 읽어와 정기회의 모델 객체에 맵핑
@Mapper
public interface CommSmallMapper {
    @Select("SELECT * FROM project2022.COMMSCHE_SMALL where MEETING_DATE = #{date}")
    List<CommSche_Small> GetCommSche_SmallDate(@Param("date") String date);

    @Select("SELECT * FROM project2022.COMMSCHE_SMALL where MEETING_DATE like '%${month}%'")
    List<CommSche_Small> GetCommSche_SmallMonth(@Param("month") String month);

    @Select("SELECT * FROM project2022.COMMSCHE_SMALL where TITLE like '%${title}%'")
    List<bonsche> SearchCOMMSCHE_SMALLTitle(@Param("title") String title);
}

