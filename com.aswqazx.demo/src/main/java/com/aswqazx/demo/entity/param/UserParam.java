package com.aswqazx.demo.entity.param;

import lombok.Data;
import java.io.Serializable;

/**
 * @author OMNIS
 */
@Data
public class UserParam implements Serializable {

    private String name;
    private String username;
    private int page;
    private int limit;
}
