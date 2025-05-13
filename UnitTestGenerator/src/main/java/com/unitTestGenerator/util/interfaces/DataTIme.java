package com.unitTestGenerator.util.interfaces;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public interface DataTIme {
    default String getTime() {
        ZonedDateTime fechaHoraZona = ZonedDateTime.now();
        DateTimeFormatter formateador = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS Z");
        String fechaHoraFormateada = fechaHoraZona.format(formateador);
        return fechaHoraFormateada;
    }
}
