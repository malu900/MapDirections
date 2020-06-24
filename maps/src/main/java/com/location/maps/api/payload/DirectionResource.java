package com.location.maps.api.payload;

import org.springframework.hateoas.RepresentationModel;

import java.util.Date;
import java.util.List;

public class DirectionResource  extends RepresentationModel<DirectionResource> {
    private Long id;
    private String name;
    private List<WaypointResource> waypoints;
    private UserSummary createdBy;
    private Date creationDateTime;

    public DirectionResource() {

    }

    public DirectionResource(Long id, List<WaypointResource> waypoints) {
        this.id = id;
        this.waypoints = waypoints;
    }

    public DirectionResource(DirectionResponse directionResponse) {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<WaypointResource> getWaypoints() {
        return waypoints;
    }

    public void setWaypoints(List<WaypointResource> waypoints) {
        this.waypoints = waypoints;
    }

    public UserSummary getCreatedBy() {
        return createdBy;
    }

    public void setUserCreatedBy(UserSummary createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreationDateTime() {
        return creationDateTime;
    }

    public void setCreationDateTime(Date creationDateTime) {
        this.creationDateTime = creationDateTime;
    }

    public void addWaypointResponse(WaypointResource waypointResponse) {
        waypoints.add(waypointResponse);
    }
}
