package com.example.nac.controller;

import com.example.nac.Mapper.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/Search")
public class ScheduleSearchController {
    //달력 정보를 넘겨주는 달력 컨트롤러
    private bonschamapper bonmapper;
    private commschemapper commapper;
    private CommKongMapper commKongMapper;
    private CommMainMapper commMainMapper;
    private CommSmallMapper commSmallMapper;
    private SeminarMapper seminarMapper;

    public ScheduleSearchController(bonschamapper bonmapper, commschemapper commapper,
                                    CommKongMapper commKongMapper, CommMainMapper commMainMapper,
                                    CommSmallMapper commSmallMapper, SeminarMapper seminarMapper) {
        this.bonmapper = bonmapper;
        this.commapper = commapper;
        this.commKongMapper = commKongMapper;
        this.commMainMapper = commMainMapper;
        this.commSmallMapper = commSmallMapper;
        this.seminarMapper = seminarMapper;
    }


    //제목으로 일정 검색
    @GetMapping("/{title}")
    public Map<String,Object> GetCalenderDate(@PathVariable("title") String title){

        Map<String, Object> map = new HashMap<>();
        map.put("bonsche",bonmapper.SearchbonscheTitle(title));
        map.put("commKong",commKongMapper.SearchCommSche_KongTitle(title)); //일반회의 - 공청회 정보
        map.put("commMain",commMainMapper.SearchCOMMSCHE_MAINTitle(title)); //일반회의 - 위원회
        map.put("commSmall",commSmallMapper.SearchCOMMSCHE_SMALLTitle(title)); //일반회의 - 위원회
        map.put("seminar",seminarMapper.SearchSEMINARTitle(title)); //세미나
        return map;
    }

}