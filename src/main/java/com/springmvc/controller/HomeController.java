package com.springmvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

@Controller
public class HomeController {
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String home(Locale locale, Model model) {
        // Locale = 지역(타임존)
        // Model = 데이터를 주고받을 수 있도록 한 표준 객체

        // 서버의 시스템 시간
        Date data = new Date();
        // 날짜 타입을 지정
        DateFormat dateformat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
        // 문자열로 변환
        String formattedDate = dateformat.format(data);

        // model 객체에 key:serverTime,value:formattedDate 정보를  추가함.
        model.addAttribute("serverTime", formattedDate);

        return "home";
    }
}
