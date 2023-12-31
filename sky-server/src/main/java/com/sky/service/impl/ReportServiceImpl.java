package com.sky.service.impl;

import com.github.pagehelper.PageHelper;
import com.sky.entity.Orders;
import com.sky.mapper.OrderMapper;
import com.sky.service.ReportService;
import com.sky.vo.TurnoverReportVO;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Service
public class ReportServiceImpl implements ReportService {

    @Autowired
    private OrderMapper orderMapper;

    @Override
    public TurnoverReportVO getTurnoverStatistics(LocalDate begin, LocalDate end) {
        ArrayList<LocalDate> list = new ArrayList<>();
        list.add(begin);
        while (!list.equals(end)) {
            begin = begin.plusDays(1);
            list.add(begin);
        }

        ArrayList<Double> localDates = new ArrayList<>();

        for (LocalDate localDate : list) {
            LocalDateTime beginTime = LocalDateTime.of(localDate, LocalTime.MIN);
            LocalDateTime endTime = LocalDateTime.of(localDate, LocalTime.MAX);
            Map map = new HashMap<>();
            map.put("begin", beginTime);
            map.put("end", endTime);
            map.put("status", Orders.COMPLETED);
            Double turnover = orderMapper.sumByMap(map);
            turnover = turnover == null ? 0.0 : turnover;
            localDates.add(turnover);
        }

        return TurnoverReportVO
                .builder()
                .dateList(StringUtils.join(list, ","))
                .turnoverList(StringUtils.join(localDates, ","))
                .build();
    }
}
