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
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.stereotype.Component;

import com.homeaway.cloud.director.model.Route;
import com.homeaway.cloud.director.model.RouteRole;

import static java.lang.String.valueOf;

import static org.springframework.util.StringUtils.isEmpty;

/**
 * Simple PoC DAO for Routes
 */
@Component
public class RouteDaoInMemoryImpl implements RouteDao {

    private final Map<String, Route> map = new ConcurrentHashMap<>();

    /* (non-Javadoc)
     * @see com.homeaway.cloud.director.api.RouteDao#stream()
     */
    @Override
    public Stream<Route> stream() {
        return map.values().stream();
    }

    /* (non-Javadoc)
     * @see com.homeaway.cloud.director.api.RouteDao#create(com.homeaway.cloud.director.model.Route)
     */
    @Override
    public Route create(Route route) {
        // FIXME ensure that id assignment is consistent. This check ensures that Postman suites can use a pre-defined id value
        if (isEmpty(route.getId())) {
            route.setId(UUID.randomUUID().toString());
        }

        if (isEmpty(route.getRole())) {
            // default role to PRIMARY
            RouteRole role = RouteRole.PRIMARY;
            // if there are other matching Routes for a given host and path then assign as VARIANT
            if (!findByHostAndPath(route).isEmpty()) {
                role = RouteRole.VARIANT;
            }
            route.setRole(role.name());
        }
        map.put(route.getId(), route);
        return route;
    }

    /* (non-Javadoc)
     * @see com.homeaway.cloud.director.api.RouteDao#delete(java.lang.String)
     */
    @Override
    public void delete(String id) {
        // TODO when a PRIMARY is deleted we would need a way to delegate a new primary
        map.remove(id);
    }

    /* (non-Javadoc)
     * @see com.homeaway.cloud.director.api.RouteDao#read(java.lang.String)
     */
    @Override
    public Route read(String id) {
        return map.get(id);
    }

    /* (non-Javadoc)
     * @see com.homeaway.cloud.director.api.RouteDao#update(com.homeaway.cloud.director.model.Route)
     */
    @Override
    public Route update(Route route) {
        map.put(route.getId(), route);
        return route;
    }

    private List<Route> findByHostAndPath(Route candidate) {
        return stream()
            .filter((r)->r.getPath().equalsIgnoreCase(candidate.getPath())
                && valueOf(r.getHost()).equalsIgnoreCase(valueOf(candidate.getHost())))
            .collect(Collectors.toList());
    }
}
