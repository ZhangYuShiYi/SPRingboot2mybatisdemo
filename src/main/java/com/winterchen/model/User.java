package com.winterchen.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by zy on 2019/10/8.
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
//springboot引入lombok，简化实体类，主要是pom加依赖及实体类上的注解，作用不是很大
public class User {

    String Id;
    String username;
    String password;

}
