package dev.vetyutnev.sorokinschoolspringbootmvc.dto;

import java.time.LocalTime;

public record ErrorMessageResponse(
        String message,
        LocalTime time
) {
}
