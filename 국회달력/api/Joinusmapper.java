package com.example.nac.Mapper;

import com.example.nac.model.bonsche;
import org.apache.ibatis.annotations.*;

import java.util.List;

//회원정보를 DB와 맵핑해줌
@Mapper
public interface Joinusmapper {
    /*-------------------------- Select --------------------------*/

    //해당하는 ID가 있을 경우 true를 반환 없을경우 false를 반환
    @Select("Select count(*) as cn from JOINUS where id=#{id}")
    boolean ExistsID(@Param("id") String id);

    @Select("Select count(*) as cn from JOINUS where EMAIL=#{EMAIL}")
    boolean ExistsEmail(@Param("EMAIL") String EMAIL);

    @Select("Select PASSWORD from JOINUS where id=#{id}")
    String LoginPassword(@Param("id") String id);

    @Select("Select ID from JOINUS where name=#{name} and email=#{email} ")
    String FindID(@Param("name") String name, @Param("email") String email);

    @Select("Select count(*) as cn from JOINUS where ID=#{ID} and SECURITY=#{SECURITY} ")
    boolean FindPasswordcheck(@Param("ID") String ID, @Param("SECURITY") String SECURITY);

    @Select("Select PASSWORD from JOINUS where ID=#{ID} and SECURITY=#{SECURITY} ")
    String FindPassword(@Param("ID") String ID, @Param("SECURITY") String SECURITY);

    /*-------------------------- Insert --------------------------*/
    //로그인정보 저장
    @Insert("INSERT INTO project2022.JOINUS " +
            "(ID, EMAIL, PASSWORD, NAME, SECURITY) " +
            "VALUES (#{ID}, #{EMAIL}, #{PASSWORD}, #{NAME}, #{SECURITY});")
    boolean SignUp(@Param("ID") String ID,
                @Param("EMAIL") String EMAIL,
                @Param("PASSWORD") String PASSWORD,
                @Param("NAME") String NAME,
                @Param("SECURITY") String SECURITY);

    /*-------------------------- Update --------------------------*/
    //개인정보 수정
    //비밀번호를 수정
    @Update("UPDATE project2022.JOINUS SET PASSWORD = #{PASSWORD} WHERE (ID = #{ID});")
    boolean ChangePassword(@Param("ID") String ID,
                           @Param("PASSWORD") String PASSWORD);
    //ID를 수정
    @Update("UPDATE project2022.JOINUS SET ID = #{ID} WHERE (ID = #{curid});")
    boolean ChangeId(@Param("ID") String ID,@Param("curid") String curid);

    @Update("UPDATE project2022.JOINUS SET EMAIL = #{EMAIL}, NAME = #{NAME}, SECURITY = #{SECURITY} WHERE ID = #{ID};")
    boolean changeAccount(@Param("ID") String ID,
                          @Param("EMAIL") String EMAIL,
                          @Param("NAME") String NAME,
                          @Param("SECURITY") String SECURITY);


}
