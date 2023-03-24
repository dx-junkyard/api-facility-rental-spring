package com.dxjunkyard.rental.domain.dto;


import com.dxjunkyard.rental.domain.Rental;
import com.dxjunkyard.rental.domain.RentalFlatten;
import com.dxjunkyard.rental.domain.request.ReservationRequest;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class RentalDto {
    public static List<RentalFlatten> reserve(ReservationRequest request) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime startDate = LocalDateTime.parse(request.getStartDate(), formatter).toLocalDate().atStartOfDay();
        LocalDateTime endDate = LocalDateTime.parse(request.getEndDate(), formatter).toLocalDate().atStartOfDay();

        List<RentalFlatten> flattenList = new ArrayList<>();
        try {
            for (Integer facilityId: request.getFacilityIdList()) {
                flattenList.add(RentalFlatten.builder()
                        .facilityId(facilityId)
                        .eventId(request.getEventId())
                        .renterId(request.getRenterId())
                        .startDate(startDate)
                        .endDate(endDate)
                        .comment(request.getComment())
                        .build());
            }
            return flattenList;
        } catch (Exception e) {
            return flattenList;
        }
    }


    public static Rental rental(List<RentalFlatten> rentalList) {
        List<Integer> facilityIdList = new ArrayList<>();
        try {
            for (RentalFlatten flatten : rentalList) {
                facilityIdList.add(flatten.getFacilityId());
            }
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd 0:00:00");
            return Rental.builder()
                    .reservationId(rentalList.get(0).getReservationId())
                    .facilityIdList(facilityIdList)
                    .eventId(rentalList.get(0).getReservationId())
                    .renterId(rentalList.get(0).getRenterId())
                    .startDate(rentalList.get(0).getStartDate().format(formatter))
                    .endDate(rentalList.get(0).getEndDate().format(formatter))
                    .comment(rentalList.get(0).getComment())
                    .build();
        } catch (Exception e) {
            return null;
        }
    }
}