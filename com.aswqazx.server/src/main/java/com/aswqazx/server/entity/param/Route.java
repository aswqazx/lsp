package com.aswqazx.server.entity.param;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import java.io.Serializable;
import java.util.List;

/**
 * @author OMNIS
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Route implements Serializable {

    private String id;
    private String path;
    private String component;
    private String redirect;
    private String name;
    private RouteMeta meta;
    private String parentId;
    private List<Route> children;
}
