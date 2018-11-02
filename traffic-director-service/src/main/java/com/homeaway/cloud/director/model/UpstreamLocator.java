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

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

/**
 * Collection of attributes used to locate a remote upstream
 */
@Data
@Builder
@JsonInclude(Include.NON_NULL)
public class UpstreamLocator {

    private String region;

    @NonNull
    private String service;

    private String tag;

    /**
     * @param region
     * @param service
     * @param tag
     */
    private UpstreamLocator(String region, String service, String tag) {
        super();
        this.region = region;
        this.service = service;
        this.tag = tag;
    }

    public UpstreamLocator() {}
}
