package com.aswqazx.server.entity.param;

import lombok.Data;
import java.io.Serializable;

/**
 * @author OMNIS
 */
@Data
public class RouteMeta implements Serializable {

    private String title;
    private String icon;
    private String[] roles;
}
