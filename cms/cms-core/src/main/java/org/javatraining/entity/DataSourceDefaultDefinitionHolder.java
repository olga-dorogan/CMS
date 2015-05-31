package org.javatraining.entity;

import javax.annotation.sql.DataSourceDefinition;
import javax.ejb.Stateless;

/**
 * Created by olga on 31.05.15.
 */
@DataSourceDefinition(name = "java:global/CMS/DefaultDS",
        className = "org.h2.jdbcx.JdbcDataSource",
        url = "jdbc:h2:mem:test")
@Stateless
public class DataSourceDefaultDefinitionHolder {
}
