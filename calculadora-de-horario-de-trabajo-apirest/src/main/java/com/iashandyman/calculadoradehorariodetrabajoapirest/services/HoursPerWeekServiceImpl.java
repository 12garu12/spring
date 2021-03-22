package com.iashandyman.calculadoradehorariodetrabajoapirest.services;

import com.iashandyman.calculadoradehorariodetrabajoapirest.model.dao.IHoursPerWeekDao;
import com.iashandyman.calculadoradehorariodetrabajoapirest.model.entity.HoursPerWeek;
import com.iashandyman.calculadoradehorariodetrabajoapirest.model.entity.ServicesReport;
import com.iashandyman.calculadoradehorariodetrabajoapirest.model.entity.Technician;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class HoursPerWeekServiceImpl implements IHoursPerWeekServices {

    @Autowired
    private IHoursPerWeekDao hoursPerWeekDao;

    @Override
    public HoursPerWeek workTime(List<ServicesReport> reports, Technician technician) {
        String weekNumber = null;
        Long id = technician.getId();
        for (ServicesReport report: reports) { weekNumber = report.getWeek(); break;}

        HoursPerWeek hoursPerWeek = findByIdTechnicianAndWeek(id, weekNumber);

        if (hoursPerWeek == null){
            hoursPerWeek = new HoursPerWeek();
        }

        for (ServicesReport report: reports){
            String week = report.getWeek();
            LocalDateTime startDate = dateAndHour(report.getStartDate());
            LocalDateTime finishDate = dateAndHour(report.getFinishDate());
            String dayOfWeek = startDate.getDayOfWeek().toString();
            int startTime = startDate.getHour();
            int startMinute = startDate.getMinute();
            int endTime = finishDate.getHour();
            int endMinute = finishDate.getMinute();

            Float workHours = ChronoUnit.SECONDS.between(startDate, finishDate) / 3600.0f;

            if (startTime >= 7 && startTime <= 20 && endTime >= 7 && endTime <= 20
                    && hoursPerWeek.getNormalHours() <= 48 && !dayOfWeek.equals("SUNDAY")) {
                hoursPerWeek.setNormalHours(hoursPerWeek.getNormalHours() + workHours);
            } else if (startTime >= 7 && startTime <= 20 && endTime > 20 && endTime <= 23
                    && hoursPerWeek.getNightHours() <= 48 && !dayOfWeek.equals("SUNDAY")) {

                if (startMinute > 0) {
                    workHours = (startMinute / 60f) + (20 - startTime - 1);
                    hoursPerWeek.setNormalHours(hoursPerWeek.getNormalHours() + workHours);
                } else {
                    workHours = 20f - startTime;
                    hoursPerWeek.setNormalHours(hoursPerWeek.getNormalHours() + workHours);
                }

                if (endMinute > 0) {
                    workHours = endTime - 20 + (endMinute / 60f);
                    hoursPerWeek.setNightHours(hoursPerWeek.getNightHours() + workHours);
                } else {
                    workHours = endTime - 20f;
                    hoursPerWeek.setNightHours(hoursPerWeek.getNightHours() + workHours);
                }

            } else if (startTime > 20 && startTime <= 23 && endTime > 20 && endTime <= 23 && !dayOfWeek.equals("SUNDAY")) {
                hoursPerWeek.setNightHours(hoursPerWeek.getNightHours() + workHours);

            } else if (startTime >= 0 && startTime < 6 && endTime > 0 && endTime <= 6 && !dayOfWeek.equals("SUNDAY")) {
                hoursPerWeek.setNightHours(hoursPerWeek.getNightHours() + workHours);

            } else if (startTime > 20 && startTime <= 6 && endTime > 20 && endTime <= 6 && !dayOfWeek.equals("SUNDAY")) {
                hoursPerWeek.setNightHours(hoursPerWeek.getNightHours() + workHours);

            } else if (hoursPerWeek.getNormalHours() >= 48) {
                hoursPerWeek.setNightOvertime(hoursPerWeek.getNightHours());
                hoursPerWeek.setNightHours(0f);

            } else if (hoursPerWeek.getNightHours() >= 48) {
                hoursPerWeek.setExtraHours(hoursPerWeek.getNormalHours());
                hoursPerWeek.setNormalHours(0f);

            } else if (dayOfWeek.equals("SUNDAY")) {

                if (startTime >= 7 && startTime <= 20 && endTime >= 7 && endTime <= 20) {
                    hoursPerWeek.setSundayHours(hoursPerWeek.getSundayHours() + workHours);
                } else if (startTime >= 7 && startTime <= 20 && endTime > 20 && endTime <= 23) {

                    if (startMinute > 0) {
                        workHours = (startMinute / 60f) + (20 - startTime - 1);
                        hoursPerWeek.setSundayHours(hoursPerWeek.getNormalHours() + workHours);
                    } else {
                        workHours = 20f - startTime;
                        hoursPerWeek.setSundayOvertime(hoursPerWeek.getSundayOvertime() + workHours);
                    }

                } else if (startTime > 20 && startTime <= 23 && endTime > 20 && endTime <= 23) {
                    hoursPerWeek.setSundayOvertime(hoursPerWeek.getSundayOvertime() + workHours);

                } else if (startTime >= 0 && startTime < 6 && endTime > 0 && endTime <= 6) {
                    hoursPerWeek.setSundayOvertime(hoursPerWeek.getSundayOvertime() + workHours);

                } else if (startTime > 20 && startTime <= 6 && endTime > 20 && endTime <= 6) {
                    hoursPerWeek.setSundayOvertime(hoursPerWeek.getSundayOvertime() + workHours);
                }
            }
        }

        hoursPerWeek.setWeek(weekNumber);
        hoursPerWeek.setTechnician(technician);
        hoursPerWeek.setTotalTime(
                hoursPerWeek.getNormalHours()
                        + hoursPerWeek.getNightHours()
                        + hoursPerWeek.getExtraHours()
                        + hoursPerWeek.getNightOvertime()
                        + hoursPerWeek.getSundayHours()
                        + hoursPerWeek.getSundayOvertime()
        );

        return hoursPerWeekDao.save(hoursPerWeek);
    }

    public HoursPerWeek findByIdTechnicianAndWeek(Long id, String week){
        return hoursPerWeekDao.findByTechnician_IdAndWeek(id, week);
    }


    /**
     * Convierte una fecha de tipo String a una fecha de tipo LocalDateTime.
     *
     * @param dateAndTime es un String con la fecha y hora del servicio.
     * @return la fecha de tipo LocalDateTime.
     */

    private LocalDateTime dateAndHour(String dateAndTime) {
        String[] dateYTime = dateAndTime.split(" ");
        String[] date = dateYTime[0].split("-");
        String[] time = dateYTime[1].split(":");
        int year = Integer.parseInt(date[0]);
        int month = Integer.parseInt(date[1]);
        int day = Integer.parseInt(date[2]);
        int hour = Integer.parseInt(time[0]);
        int minute = Integer.parseInt(time[1]);
        int second = Integer.parseInt(time[2]);

        return LocalDateTime.of(year, month, day, hour, minute, second);
    }


}
