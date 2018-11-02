Traffic Director
================
Interface Definition Language (IDL) for Global Multicloud Traffic Direction. This provides an interface between a Control Plane for Traffic Direction and a Management Plane (API, User Interface and Command Line Interface)

**Version:** 0.1.0

**Contact information:**  
HomeAway Cloud Engineering  
cloud-oss@groups.homeawaycorp.com  

### /routes
---
##### ***GET***
**Summary:** Get Routes

**Description:** Simple List of all Routes for demonstration purposes. A production-grade solution might use GraphQL instead: https://graphql.org/learn/serving-over-http/

**Parameters**

| Name | Located in | Description | Required | Schema |
| ---- | ---------- | ----------- | -------- | ---- |

**Responses**

| Code | Description | Schema |
| ---- | ----------- | ------ |
| 200 | Status 200 | [ [Route](#route) ] |

##### ***POST***
**Summary:** Create Route

**Parameters**

| Name | Located in | Description | Required | Schema |
| ---- | ---------- | ----------- | -------- | ---- |
| body | body |  | Yes | [Route](#route) |

**Responses**

| Code | Description | Schema |
| ---- | ----------- | ------ |
| 200 | Status 200 | [Route](#route) |

### /routes/{id}
---
##### ***GET***
**Summary:** Get Route

**Description:** Get a single Route by Route.id

**Parameters**

| Name | Located in | Description | Required | Schema |
| ---- | ---------- | ----------- | -------- | ---- |
| id | path | Identifier for Route | Yes | string |

**Responses**

| Code | Description | Schema |
| ---- | ----------- | ------ |
| 200 | Status 200 | [Route](#route) |
| 404 | Route Not Found |  |

##### ***PUT***
**Summary:** Update Route

**Parameters**

| Name | Located in | Description | Required | Schema |
| ---- | ---------- | ----------- | -------- | ---- |
| id | path | Identifier for Route | Yes | string |
| body | body |  | Yes | [Route](#route) |

**Responses**

| Code | Description | Schema |
| ---- | ----------- | ------ |
| 200 | Status 200 | [Route](#route) |

##### ***DELETE***
**Summary:** Delete Route

**Description:** Delete a Route record based on the provided Route.id value.

**Parameters**

| Name | Located in | Description | Required | Schema |
| ---- | ---------- | ----------- | -------- | ---- |
| id | path | Identifier for Route | Yes | string |

**Responses**

| Code | Description |
| ---- | ----------- |
| 204 | Route Deleted |

### /routes/entries
---
##### ***GET***
**Description:** Read Only Resource for accessing RouteEntry records based on a provided set of filters as query parameters

**Parameters**

| Name | Located in | Description | Required | Schema |
| ---- | ---------- | ----------- | -------- | ---- |
| host | query | Host header value | No | string |

**Responses**

| Code | Description | Schema |
| ---- | ----------- | ------ |
| 200 | Status 200 | [ [RouteEntry](#routeentry) ] |

### /regions
---
##### ***GET***
**Summary:** GET all regions

**Description:** Read Only Array of all regions. This is configured within the service and cannot be updated via an API.

**Parameters**

| Name | Located in | Description | Required | Schema |
| ---- | ---------- | ----------- | -------- | ---- |

**Responses**

| Code | Description | Schema |
| ---- | ----------- | ------ |
| 200 | Status 200 | [ [Region](#region) ] |

### Models
---

### Region  

Regional Upstream

| Name | Type | Description | Required |
| ---- | ---- | ----------- | -------- |
| id | string | UUID to locate a specific Region record. This is typically assigned by a Data Access Layer so it is not accepted during record creation and required for updates. | No |
| alias | string | Alias used to identify a Region for Upstream routing. This is necessary to decouple the specific details of the Regional Upstream when defining Routes. A lookup is required as part of the resolution to a region. | Yes |
| uri | string | Uniform Resource Identifier (URI) that defines the location of the region e.g. fully qualified URL | Yes |

### Route  

| Name | Type | Description | Required |
| ---- | ---- | ----------- | -------- |
| id | string | UUID to locate a specific Route record. This is typically assigned by a Data Access Layer so it is not accepted during Route record creation and required for updates | Yes |
| host | string | HTTP Host header value that support this Route. If absent then this Route will map to any Host header value. | No |
| path | string | Route Matchers will assert whether an HTTP path starts with this value. A valid path String starts with "/". Typically matchers will be evaluated in reverse alphabetical order to ensure the most-specific match is found first. | Yes |
| upstream | [Upstream](#upstream) |  | Yes |
| role | string | Specifies the role of this Route. This is used to determine which Route should handle traffic when there are multiple Routes that map to the same combination of host and path. | Yes |

### Upstream  

Upstream destination, either derived from a URL or discoverable using a Service Discovery mechanism and a Service Mesh or Discovery-aware Load Balancer

| Name | Type | Description | Required |
| ---- | ---- | ----------- | -------- |
| uri | string | Uniform Resource Identifier (URI) that defines the Service location e.g. fully qualified URL or a path that maps to a downstream Service Discovery solution. | Yes |
| locator | [UpstreamLocator](#upstreamlocator) |  | No |

### UpstreamLocator  

Collection of attributes used to locate a remote upstream

| Name | Type | Description | Required |
| ---- | ---- | ----------- | -------- |
| region | string | Regional Upstream Alias | No |
| service | string | Service name used for Service Discovery | No |
| tag | string | Service tag used for Service Discovery | No |

### RouteEntry  

Pair of a Route concatenated path String and a Route Upstream uri String

| Name | Type | Description | Required |
| ---- | ---- | ----------- | -------- |
| path | string | Concatenated path of a Route - must be unique and is derived from the Route model. | No |
| uri | string | Upstream uri | No |