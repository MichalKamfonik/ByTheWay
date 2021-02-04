package pl.kamfonik.bytheway.converter;

import org.springframework.core.convert.converter.Converter;

import java.time.Duration;

public class DurationConverter implements Converter<String, Duration> {
    @Override
    public Duration convert(String s) {
        return Duration.ofDays(Long.parseLong(s));
    }
}
