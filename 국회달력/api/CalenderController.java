package com.example.nac.controller;

import com.example.nac.Mapper.*;
import com.example.nac.Mapper.*;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping(value = "/calender")
public class CalenderController {
    //달력 정보를 넘겨주는 달력 컨트롤러
    private bonschamapper bonmapper;
    private commschemapper commapper;
    private CommKongMapper commKongMapper;
    private CommMainMapper commMainMapper;
    private CommSmallMapper commSmallMapper;
    private SeminarMapper seminarMapper;

    public CalenderController(bonschamapper bonmapper, commschemapper commapper,
                              CommKongMapper commKongMapper, CommMainMapper commMainMapper,
                              CommSmallMapper commSmallMapper, SeminarMapper seminarMapper) {
        this.bonmapper = bonmapper;
        this.commapper = commapper;
        this.commKongMapper = commKongMapper;
        this.commMainMapper = commMainMapper;
        this.commSmallMapper = commSmallMapper;
        this.seminarMapper = seminarMapper;
    }


    //날자를 알려주면 해당 날자의 일정을 돌려줌
    @GetMapping("/date/{date}")
    public Map<String,Object> GetCalenderDate(@PathVariable("date") String date){

        Map<String, Object> map = new HashMap<>();
        map.put("bonsche",bonmapper.GetbonscheDate(date));
        map.put("commKong",commKongMapper.GetCommSche_KongDate(date)); //일반회의 - 공청회 정보
        map.put("commMain",commMainMapper.GetCommSche_MainDate(date)); //일반회의 - 위원회
        map.put("commSmall",commSmallMapper.GetCommSche_SmallDate(date)); //일반회의 - 위원회
        map.put("seminar",seminarMapper.GetSeminarDate(date)); //세미나
        return map;
    }

    //날자를 알려주면 해당 한달간의 일정을 돌려줌
    @GetMapping("/month/{date}")
    public Map<String,Object> GetCalenderMonth(@PathVariable("date") String date){
        String month = date.substring(0, 7); //년도와 달로 자름 2022-09-28 -> 2022-09

        //DB에서 가져온 Scadule객체 LIST를 MAP에 대입해 JSON형식으로 출력
        Map<String, Object> map = new HashMap<>();
        map.put("bonsche",bonmapper.GetbonscheMonth(month));
        map.put("commKong",commKongMapper.GetCommSche_KongMonth(month)); //일반회의 - 공청회 정보
        map.put("commMain",commMainMapper.GetCommSche_MainMonth(month)); //일반회의 - 위원회
        map.put("commSmall",commSmallMapper.GetCommSche_SmallMonth(month)); //일반회의 - 위원회
        map.put("seminar",seminarMapper.GetSeminarMonth(month)); //세미나
        return map;
    }

    @GetMapping("/month/{date}/{type}")
    public Map<String,Object> GetCalenderfilter(@PathVariable("date") String date, @PathVariable("type") String type){
        String month = date.substring(0, 7); //년도와 달로 자름 2022-09-28 -> 2022-09
        String[] data_type = type.split(",");
        Map<String, Object> map = new HashMap<>();

        map.put("bonsche",null);
        map.put("commKong",null);
        map.put("commMain",null);
        map.put("commSmall",null);
        map.put("seminar",null);

        for (String item: data_type)
        {
                switch (item){
                    case "bonsche": map.put("bonsche",bonmapper.GetbonscheMonth(month));
                        break;
                    case "commKong": map.put("commKong",commKongMapper.GetCommSche_KongMonth(month)); //일반회의 - 공청회 정보
                        break;
                    case "commMain": map.put("commMain",commMainMapper.GetCommSche_MainMonth(month)); //일반회의 - 위원회
                        break;
                    case "commSmall": map.put("commSmall",commSmallMapper.GetCommSche_SmallMonth(month)); //일반회의 - 위원회
                        break;
                    case "seminar": map.put("seminar",seminarMapper.GetSeminarMonth(month)); //세미나
                        break;
                }
        }
        return map;
    }

   

}