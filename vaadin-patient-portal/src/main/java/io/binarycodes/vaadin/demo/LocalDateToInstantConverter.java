package io.binarycodes.vaadin.demo;


import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Objects;

import com.vaadin.flow.data.binder.Result;
import com.vaadin.flow.data.binder.ValueContext;
import com.vaadin.flow.data.converter.Converter;

public class LocalDateToInstantConverter implements Converter<LocalDate, Instant> {
    private final ZoneId zoneId;

    public LocalDateToInstantConverter(ZoneId zoneId) {
        this.zoneId = Objects.requireNonNull(zoneId, "Zone id cannot be null");
    }

    public LocalDateToInstantConverter() {
        this(ZoneId.systemDefault());
    }

    public Result<Instant> convertToModel(LocalDate localDate, ValueContext context) {
        return localDate == null ? Result.ok(null) : Result.ok(localDate.atStartOfDay(this.zoneId).toInstant());
    }

    public LocalDate convertToPresentation(Instant date, ValueContext context) {
        return date == null ? null : date.atZone(this.zoneId).toLocalDate();
    }
}