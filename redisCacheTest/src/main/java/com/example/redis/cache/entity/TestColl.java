package com.example.redis.cache.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author ASUS
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TestColl implements Serializable {
    private static final long serialVersionUID = -8382947720994209010L;

    private String id;
    private String name;
    private Integer age;
    private BigDecimal money;

}
