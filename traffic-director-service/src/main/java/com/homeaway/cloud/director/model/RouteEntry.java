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

import static org.springframework.util.StringUtils.isEmpty;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

/**
 * Pair of a Route concatenated path String and a Route Upstream uri String
 */
@Data
@Builder
public class RouteEntry   {

    @NonNull
    private String path;

    @NonNull
    private String uri;

    public static final RouteEntry fromRoute(Route route) {
        final StringBuilder builder = new StringBuilder();
        if (!isEmpty(route.getHost())) {
            builder.append(route.getHost());
        }

        builder.append(route.getPath());

        if (RouteRole.FALLBACK.name().equalsIgnoreCase(route.getRole())) {
            builder.append("/f/");
            builder.append(route.getId());
        }

        if (RouteRole.VARIANT.name().equalsIgnoreCase(route.getRole())) {
            builder.append("/v/");
            builder.append(route.getId());
        }

        return RouteEntry.builder()
            .path(builder.toString())
            .uri(route.getUpstream().getUri())
            .build();
    }
}
