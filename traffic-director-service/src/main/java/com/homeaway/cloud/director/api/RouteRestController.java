/*
 * Copyright 2018 Expedia, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.homeaway.cloud.director.api;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.homeaway.cloud.director.model.Route;
import com.homeaway.cloud.director.model.RouteEntry;

import static org.springframework.util.StringUtils.isEmpty;

@RestController
@RequestMapping("/api/v1/routes")
public class RouteRestController {

    @Autowired
    private RouteDao dao;

    @GetMapping
    public List<Route> getRoutes() {
        return dao.stream().collect(Collectors.toList());
    }

    @PostMapping
    public ResponseEntity<Route> createRoute(@RequestBody Route route) {
        // TODO more robust solution to reject duplicates
        if (!isEmpty(route.getId()) && dao.read(route.getId()) != null) {
            return new ResponseEntity<Route>(dao.read(route.getId()), HttpStatus.NOT_MODIFIED);
        }

        final Route created = dao.create(route);
        return new ResponseEntity<Route>(created, HttpStatus.OK);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<Route> getRoute(@PathVariable String id) {
        final Route route = dao.read(id);
        HttpStatus status = HttpStatus.OK;
        if (route == null) {
            status = HttpStatus.NOT_FOUND;
        }
        return new ResponseEntity<Route>(route, status);
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<Route> updateRoute(@PathVariable String id, @RequestBody Route route) {
        final Route created = dao.update(route);
        return new ResponseEntity<Route>(created, HttpStatus.OK);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> deleteRoute(@PathVariable String id) {
        dao.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(path = "/entries")
    public List<RouteEntry> getRouteEntries(@RequestParam(required = false) String host) {
        return dao.stream()
            .filter((r)->{
                // ignore filter is no host value is provided
                if (StringUtils.isEmpty(host)) {
                    return true;
                } else {
                    return host.equalsIgnoreCase(r.getHost());
                }
            })
            .map((r)->RouteEntry.fromRoute(r))
            .collect(Collectors.toList());
    }
}
