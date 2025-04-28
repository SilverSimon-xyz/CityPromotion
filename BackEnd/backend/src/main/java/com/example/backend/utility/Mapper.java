package com.example.backend.utility;

import com.example.backend.dto.PointOfInterestDto;
import com.example.backend.entities.PointOfInterest;
import com.example.backend.entities.User;
import com.example.backend.dto.Account;
import org.springframework.stereotype.Component;

@Component
public class Mapper {

    public Account mapUserToAccount(User user) {
        return Account.build(user);
    }

    public PointOfInterestDto mapPOIToDto(PointOfInterest pointOfInterest) {
        PointOfInterestDto pointOfInterestDTO = new PointOfInterestDto();
        pointOfInterestDTO.setId(pointOfInterest.getId());
        pointOfInterestDTO.setName(pointOfInterest.getName());
        pointOfInterestDTO.setDescription(pointOfInterest.getDescription());
        pointOfInterestDTO.setAuthor(pointOfInterest.getAuthor().getName());
        pointOfInterestDTO.setStatus(pointOfInterest.getStatus());
        pointOfInterestDTO.setLatitude(pointOfInterest.getLatitude());
        pointOfInterestDTO.setLongitude(pointOfInterest.getLongitude());
        pointOfInterestDTO.setOpenTime(pointOfInterest.getOpenTime());
        pointOfInterestDTO.setCloseTime(pointOfInterest.getCloseTime());
        pointOfInterestDTO.setType(pointOfInterest.getType());

        return pointOfInterestDTO;
    }
}
