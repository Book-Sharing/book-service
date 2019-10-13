package com.zuovx.book.controller;

import com.zuovx.book.model.Desire;
import com.zuovx.book.service.bus.JsonResult;
import com.zuovx.book.service.wish.WishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

/**
 * @description:
 * @author: wang.jianwen
 * @create: 2019-10-13 20:20
 **/
@RestController
@RequestMapping("/wish")
public class WishController {

    @Autowired
    WishService wishService;

    @PostMapping("/sel")
    public JsonResult selWish(@RequestParam(value = "start", required = false) String start,
                              @RequestParam(value = "end", required = false) String end) {
        JsonResult resp = new JsonResult();
        List<Desire> desires;
        if (StringUtils.isEmpty(start) || StringUtils.isEmpty(end)) {
            // 默认一天
            ZoneId zoneId = ZoneId.systemDefault();
            LocalDateTime now = LocalDateTime.now();
            LocalDateTime yesterday = now.minusDays(1);
            long nowMill = now.atZone(zoneId).toInstant().toEpochMilli();
            long yesMill = yesterday.atZone(zoneId).toInstant().toEpochMilli();
            desires = wishService.selAll(yesMill, nowMill);
        } else {
            desires = wishService.selAll(Long.valueOf(start), Long.valueOf(end));
        }
        resp.setData(desires);
        return resp;
    }

    @PostMapping("/add")
    public JsonResult addWish(@Validated Desire desire) {
        JsonResult resp = new JsonResult();
        wishService.add(desire);
        return resp;
    }

    @PostMapping("/del")
    public JsonResult delWish(@RequestParam("id") String id) {
        JsonResult resp = new JsonResult();
        boolean result = wishService.del(id);
        resp.setData(result);
        return resp;
    }

    @PostMapping("/mod")
    public JsonResult modifyWish(@Validated Desire desire) {
        JsonResult resp = new JsonResult();
        boolean result = wishService.modify(desire);
        if (result) {
            resp.setData(desire);
        }
        return resp;
    }
}
