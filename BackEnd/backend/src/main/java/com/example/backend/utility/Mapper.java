package com.example.backend.utility;

import com.example.backend.dto.response.*;
import com.example.backend.entities.content.MediaFile;
import com.example.backend.entities.content.MultimediaContent;
import com.example.backend.entities.contest.Contest;
import com.example.backend.entities.poi.PointOfInterest;
import com.example.backend.entities.users.Role;
import com.example.backend.entities.users.User;
import org.springframework.stereotype.Component;

@Component
public class Mapper {

    public PointOfInterestResponse mapPOIToResponse(PointOfInterest pointOfInterest) {
        PointOfInterestResponse pointOfInterestResponse = new PointOfInterestResponse();

        pointOfInterestResponse.setId(pointOfInterest.getId());
        pointOfInterestResponse.setName(pointOfInterest.getName());
        pointOfInterestResponse.setDescription(pointOfInterest.getDescription());
        pointOfInterestResponse.setAuthor(pointOfInterest.getAuthor().getName());
        pointOfInterestResponse.setLatitude(pointOfInterest.getLatitude());
        pointOfInterestResponse.setLongitude(pointOfInterest.getLongitude());
        pointOfInterestResponse.setOpenTime(pointOfInterest.getOpenTime());
        pointOfInterestResponse.setCloseTime(pointOfInterest.getCloseTime());
        pointOfInterestResponse.setType(pointOfInterest.getType());
        pointOfInterestResponse.setCreatedAt(pointOfInterest.getCreatedAt());
        pointOfInterestResponse.setUpdatedAt(pointOfInterest.getUpdatedAt());

        return pointOfInterestResponse;
    }

    public RoleResponse mapRoleToResponse(Role role) {
        RoleResponse roleResponse = new RoleResponse();
        roleResponse.setId(role.getId());
        roleResponse.setName(role.getName());
        roleResponse.setDescription(role.getDescription());

        roleResponse.setUsers(role.getUsers()
                .stream()
                .map(User::getEmail)
                .toList());
        roleResponse.setPrivileges(role.getPrivileges()
                .stream()
                .map(privilege -> privilege.getName().name())
                .toList());
        return roleResponse;
    }

    public ContestResponse mapContestToResponse(Contest contest) {
        ContestResponse contestResponse = new ContestResponse();

        contestResponse.setId(contest.getId());
        contestResponse.setName(contest.getName());
        contestResponse.setDescription(contest.getDescription());
        contestResponse.setAuthor(contest.getAuthor().getName());
        contestResponse.setRules(contest.getRules());
        contestResponse.setGoal(contest.getGoal());
        contestResponse.setPrize(contest.getPrize());
        contestResponse.setDeadline(contest.getDeadline());
        contestResponse.setActive(contest.getActive());
        contestResponse.setNumberOfParticipant(contest.getNumberOfParticipant());
        contestResponse.setCreatedAt(contest.getCreatedAt());
        contestResponse.setDataUpdated(contest.getDataUpdated());

        return contestResponse;
    }

    public MultimediaContentResponse mapContentToResponse(MultimediaContent multimediaContent) {
        MultimediaContentResponse multimediaContentResponse = new MultimediaContentResponse();

        multimediaContentResponse.setId(multimediaContent.getId());
        multimediaContentResponse.setTitle(multimediaContent.getTitle());
        multimediaContentResponse.setDescription(multimediaContent.getDescription());
        multimediaContentResponse.setAuthor(multimediaContent.getAuthor().getName());
        multimediaContentResponse.setStatus(multimediaContent.getStatus());

        if(multimediaContent.getMediaFile() != null) {
            MediaFileResponse mediaFileResponse = mapMediaFileToResponse(multimediaContent.getMediaFile());
            multimediaContentResponse.setMediaFile(mediaFileResponse);
        }
        if(multimediaContent.getPoi() != null) {
            PointOfInterestResponse pointOfInterestResponse = mapPOIToResponse(multimediaContent.getPoi());
            multimediaContentResponse.setPOI(pointOfInterestResponse);
        }

        return multimediaContentResponse;
    }

    private MediaFileResponse mapMediaFileToResponse(MediaFile mediaFile) {

        MediaFileResponse mediaFileResponse = new MediaFileResponse();
        mediaFileResponse.setId(mediaFile.getId());
        mediaFileResponse.setName(mediaFile.getName());
        mediaFileResponse.setType(mediaFile.getType());
        mediaFileResponse.setData(mediaFile.getData());

        return mediaFileResponse;

    }

}
