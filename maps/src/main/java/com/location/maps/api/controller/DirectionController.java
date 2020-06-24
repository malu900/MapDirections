package com.location.maps.api.controller;

import com.location.maps.api.payload.*;
import com.location.maps.api.payload.response.ApiResponse;
import com.location.maps.exception.AppException;
import com.location.maps.model.Direction;
import com.location.maps.model.User;
import com.location.maps.respository.DirectionRepository;
import com.location.maps.respository.UserRepository;
import com.location.maps.security.CurrentUser;
import com.location.maps.security.UserPrincipal;
import com.location.maps.service.DirectionService;
import com.location.maps.service.WaypointService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.ControllerLinkBuilder;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import org.springframework.hateoas.Link;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


@RestController
@RequestMapping("/api/directions")
public class DirectionController {
    private final DirectionRepository directionRepository;

    private final UserRepository userRepository;

    private final DirectionService directionService;

    final
    WaypointService waypointService;

    public DirectionController(DirectionRepository directionRepository, UserRepository userRepository, DirectionService directionService, WaypointService waypointService) {
        this.directionRepository = directionRepository;
        this.userRepository = userRepository;
        this.directionService = directionService;
        this.waypointService = waypointService;
    }

    @GetMapping
    public ResponseEntity<List<DirectionResponse>> getAllEmployees() {
        List<DirectionResponse> list = directionService.getAllDirections();

        return new ResponseEntity<>(list, HttpStatus.OK);
    }


    @PostMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> createDirection(@CurrentUser UserPrincipal currentUser, @RequestBody DirectionRequest directionRequest) {
        Direction direction = directionService.createDirection(currentUser, directionRequest);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{directionId}")
                .buildAndExpand(direction.getId()).toUri();

        direction.setCreatedBy(currentUser.getId());
        return ResponseEntity.created(location)
                .body(new ApiResponse(true, "direction created!"));
    }

    @GetMapping(path="/{directionId}/waypoints/{waypointId}")
    public ResponseEntity<?> getWaypointById(@PathVariable Long directionId, @PathVariable Long waypointId) {
        WaypointResource waypointResponse = waypointService.getWaypointById(directionId, waypointId);

        return new ResponseEntity<>(waypointResponse, HttpStatus.OK);
    }

    @GetMapping(value="/{directionId}/waypoints", produces = "application/json")
    public ResponseEntity<?> getWaypointsFromDirection(@PathVariable Long directionId) {
        DirectionResource directionResponse = waypointService.getWaypointsByDirectionId(directionId);

        for (WaypointResource waypointResponse : directionResponse.getWaypoints()) {
            Link selfLink = linkTo(
                    methodOn(DirectionController.class).getWaypointById(directionId, waypointResponse.getId())).withSelfRel();
            waypointResponse.add(selfLink);
        }

        Link link = linkTo(methodOn(DirectionController.class).getWaypointsFromDirection(directionId)).withSelfRel();
        EntityModel<DirectionResource> result = new EntityModel<>(directionResponse, link);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

}
