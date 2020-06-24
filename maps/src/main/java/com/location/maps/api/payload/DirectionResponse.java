package com.location.maps.api.payload;

import com.location.maps.model.Direction;
import com.location.maps.model.Waypoint;
import org.springframework.hateoas.RepresentationModel;

import java.util.Date;
import java.util.List;

public class DirectionResponse extends RepresentationModel<DirectionResponse> {
    private Long id;
    private String name;
    private List<WaypointResponse> waypoints;
    private UserSummary createdBy;
    private Date creationDateTime;

    public DirectionResponse() {

    }

    public DirectionResponse(Long id, List<WaypointResponse> waypoints) {
        this.id = id;
        this.waypoints = waypoints;
    }

    public DirectionResponse(DirectionResponse directionResponse) {

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

    public List<WaypointResponse> getWaypoints() {
        return waypoints;
    }

    public void setWaypoints(List<WaypointResponse> waypoints) {
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

    public void addWaypointResponse(WaypointResponse waypointResponse) {
        waypoints.add(waypointResponse);
    }
}
