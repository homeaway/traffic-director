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

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import static org.springframework.util.StringUtils.isEmpty;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

/**
 * Upstream destination, either derived from a URL or discoverable using a Service Discovery mechanism and a Service Mesh or Discovery-aware
 * Load Balancer
 */
@Data
@Builder
@JsonInclude(Include.NON_NULL)
public class Upstream {

    @NonNull
    private String uri;

    private UpstreamLocator locator;

    /**
     * @param uri
     * @param locator
     */
    private Upstream(String uri, UpstreamLocator locator) {
        super();
        this.uri = uri;
        this.locator = locator;
    }

    public Upstream() {}

    public String getUri() {
        if (isEmpty(uri) && locator != null) {
            final StringBuilder builder = new StringBuilder();
            if (!isEmpty(locator.getRegion())) {
                builder.append("/");
                builder.append(locator.getRegion());
            }

            if (!isEmpty(locator.getTag())) {
                builder.append("/");
                builder.append(locator.getTag());
            }

            builder.append("/");
            builder.append(locator.getService());

            this.uri = builder.toString();
        }
        return uri;
    }
}
