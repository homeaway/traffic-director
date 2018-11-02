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
package com.homeaway.cloud.director.model;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

/**
 * Route
 */
@Data
@Builder
@JsonInclude(Include.NON_NULL)
public class Route {

    @Builder.Default
    private String id = UUID.randomUUID().toString();

    private String host;

    @NonNull
    private String path;

    @NonNull
    private Upstream upstream;

    @Builder.Default
    private String role = RouteRole.PRIMARY.name();

    public Route() {}

    /**
     * @param id
     * @param host
     * @param path
     * @param upstream
     * @param role
     */
    private Route(String id, String host, String path, Upstream upstream, String role) {
        super();
        this.id = id;
        this.host = host;
        this.path = path;
        this.upstream = upstream;
        this.role = role;
    }
}
