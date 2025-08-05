package com.beyond.basic.temporary;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum TicketType {

    UNLIMITED_REGULAR("TK001", "무제한듣기 정기", "기기제한 없음, 무제한 스트리밍", true, 7700, 1, ChronoUnit.MONTHS,-1),
    UNLIMITED_MONTHLY("TK002", "무제한듣기 한달", "기기제한 없음, 무제한 스트리밍", false, 9000, 1,ChronoUnit.MONTHS, -1),
    LIMITED_REGULAR("TK003", "300회 듣기 정기", "기기제한 없음, 횟수 제한", true, 4900, 1, ChronoUnit.MONTHS,300),
    LIMITED_MONTHLY("TK004", "300회 듣기 한달", "기기제한 없음, 횟수 제한", false, 5000, 1, ChronoUnit.MONTHS,300);

    private final String codeValue;
    private final String codeName;
    private final String description;
    private final boolean autoRenew;
    private final int ticketPrice;
    private final int durationAmount;
    private final ChronoUnit durationUnit;
    private final int usageLimit;

    public static TicketType fromCode(String code) {
        return Arrays.stream(values())
                .filter(v -> v.codeValue.equals(code))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Unknown Ticket code: " + code));
    }

    public LocalDate calculateExpirationDate(LocalDate startDate) {
        return startDate.plus(durationAmount, durationUnit);
    }
}