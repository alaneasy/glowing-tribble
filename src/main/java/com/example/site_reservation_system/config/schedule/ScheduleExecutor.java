package com.example.site_reservation_system.config.schedule;

import com.example.site_reservation_system.model.SysConfig;
import com.example.site_reservation_system.model.slot.record.Record;
import com.example.site_reservation_system.service.SysConfigService;
import com.example.site_reservation_system.service.field.FieldService;
import com.example.site_reservation_system.service.slot.SlotService;
import com.example.site_reservation_system.service.slot.record.SlotRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.time.LocalDate;
import java.time.ZonedDateTime;

@Component
public class ScheduleExecutor {

    @Autowired
    private SlotRecordService slotRecordService;
    @Autowired
    private FieldService fieldService;
    @Autowired
    private SlotService slotService;
    @Autowired
    private SysConfigService sysConfigService;

    @Async
    @Scheduled(cron = "0 0 1 * * ?")
    public void task() {
        SysConfig sysConfig = sysConfigService.queryById("slot");
        LocalDate now = ZonedDateTime.now().toLocalDate();
        for (var i = Integer.parseInt(sysConfig.getSysValue()); i > 0; --i) {
            Date date = Date.valueOf(now.plusDays(i));
            fieldService.queryAll().forEach(e -> slotService.queryListByCountAndFId(slotService.queryMaxCount(e.getId()), e.getId()).stream().filter(o -> o.getDay().equals(date.getDay())).forEach(p -> slotRecordService.insert(Record.builder().date(date).status(0).fId(e.getId()).sId(p.getId()).build())));
        }
    }
}