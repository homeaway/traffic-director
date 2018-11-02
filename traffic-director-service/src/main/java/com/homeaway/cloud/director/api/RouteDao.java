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

import java.util.stream.Stream;

import com.homeaway.cloud.director.model.Route;

/**
 * Simple Data Access Object for Route records
 */
public interface RouteDao {

    /**
     * @return Stream of all Route records
     */
    Stream<Route> stream();

    /**
     * create a new Route record
     * @param route Route to create
     * @return Route record with updated entity tag and id assignment
     */
    Route create(Route route);

    /**
     * delete an existing Route record
     * @param id Identifier of Route to delete
     */
    void delete(String id);

    /**
     * read an existing Route record
     * @param id Identifier of Route to read
     * @return Route record with corresponding id or null if not found
     */
    Route read(String id);

    /**
     * update an existing Route record
     * @param route Route to update
     * @return Route record with updated entity tag
     */
    Route update(Route route);

}
