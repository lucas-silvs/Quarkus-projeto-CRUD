package com.crudquarkus.service.converter;

import com.crudquarkus.exception.ParseDataNascimentoException;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public  class DateConverter {
    private static final DateFormat formato = new SimpleDateFormat("dd/MM/yyyy");

    public static Date StringToDate(String data) {
        Date dataConverter;
        try {
            dataConverter = formato.parse(data);
        } catch (ParseException e) {
            throw new ParseDataNascimentoException("Não foi possivel converter a data, erro: " + e);
        }
        return dataConverter;
    }
}
