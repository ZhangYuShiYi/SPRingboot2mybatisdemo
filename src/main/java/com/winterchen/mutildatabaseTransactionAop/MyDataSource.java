package com.winterchen.mutildatabaseTransactionAop;

import java.lang.annotation.*;

/**
 * @author: zy
 */

@Target(ElementType.METHOD)
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface MyDataSource {

	String value() default "datasource1";

}
