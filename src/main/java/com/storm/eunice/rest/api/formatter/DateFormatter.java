package com.storm.eunice.rest.api.formatter;

import com.storm.eunice.rest.api.controller.WeatherController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateFormatter {

    private static final Logger log = LoggerFactory.getLogger(DateFormatter.class);

    private final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");

    public Date stringToDate(String date) {

        try {
            return simpleDateFormat.parse(date);

        } catch (Exception e) {
            log.error(String.valueOf(e));
        }

        return null;
    }

}