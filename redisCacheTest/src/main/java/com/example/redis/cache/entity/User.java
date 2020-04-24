package com.example.redis.cache.entity;

import jdk.internal.org.objectweb.asm.commons.SerialVersionUIDAdder;
import lombok.Data;

import java.io.Serializable;

/**
 * @author ASUS
 */
@Data
public class User implements Serializable {
    private static final long serialVersionUID = 5505466181747922254L;

    private String id;
    private String name;
    private String passWord;

}
