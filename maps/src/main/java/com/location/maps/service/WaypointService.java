package com.location.maps.service;

import com.location.maps.api.payload.DirectionResource;
import com.location.maps.api.payload.DirectionResponse;
import com.location.maps.api.payload.WaypointResource;
import com.location.maps.api.payload.WaypointResponse;
import com.location.maps.model.Direction;
import com.location.maps.model.Waypoint;
import com.location.maps.respository.DirectionRepository;
import com.location.maps.respository.WaypointRepository;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.TreeSet;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toCollection;

@Service
public class WaypointService {

    final DirectionRepository directionRepository;

    final WaypointRepository waypointRepository;

    public WaypointService(DirectionRepository directionRepository, WaypointRepository waypointRepository) {
        this.directionRepository = directionRepository;
        this.waypointRepository = waypointRepository;
    }

    public DirectionResource getWaypointsByDirectionId(Long directionId) {
        Direction direction = directionRepository.findById(directionId).get();
        DirectionResource directionResponse = new DirectionResource();
        directionResponse.setId(direction.getId());

        List<Waypoint> waypoints = direction.getWaypoints();
        List<WaypointResource> waypointResponses = waypoints.stream().map(waypoint -> {
            WaypointResource waypointResponse = new WaypointResource();
            waypointResponse.setId(waypoint.getId());
            waypointResponse.setName(waypoint.getName());
            waypointResponse.setLatitude(waypoint.getLatitude());
            waypointResponse.setLongitude(waypoint.getLongitude());
            return waypointResponse;
        }).collect(Collectors.toList());
        List<WaypointResource> waypointResponseList = waypointResponses.stream()
                .collect(collectingAndThen(toCollection(() -> new TreeSet<>(Comparator.comparingLong(WaypointResource::getId))),
                        ArrayList::new));
        directionResponse.setWaypoints(waypointResponseList);

        return directionResponse;
    }

    public WaypointResource getWaypointById(Long directionId, Long id) {
        Direction direction = directionRepository.findById(directionId).get();
        direction.getWaypoints();
        WaypointResource waypointResponse = new WaypointResource();
        for( Waypoint waypoint : direction.getWaypoints()) {
            if(waypoint.getId() == id) {
                waypointResponse.setId(waypoint.getId());
                waypointResponse.setName(waypoint.getName());
                waypointResponse.setLatitude(waypoint.getLatitude());
                waypointResponse.setLongitude(waypoint.getLongitude());
            }
        }
        return waypointResponse;
    }
}
