package me.joshua.hello;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.convert.DurationUnit;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

@Component
@ConfigurationProperties("time")
public class DurationProperties {

    private Duration firstDuration = Duration.ofMinutes(3);
    private Duration secondDuration;

    public Duration getFirstDuration() {
        return firstDuration;
    }

    public void setFirstDuration(Duration firstDuration) {
        this.firstDuration = firstDuration;
    }

    public Duration getSecondDuration() {
        return secondDuration;
    }

    public void setSecondDuration(Duration secondDuration) {
        this.secondDuration = secondDuration;
    }
}
