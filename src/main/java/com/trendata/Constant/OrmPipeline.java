package com.trendata.Constant;


import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.db.MysqlDatabaseType;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.table.TableUtils;
import org.apache.commons.lang.builder.ToStringBuilder;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.PageModelPipeline;

import java.sql.SQLException;

/**
 * Created by friddle on 3/10/14.
 */
public class OrmPipeline implements PageModelPipeline {
    JdbcConnectionSource connectionSource;
	Class t;

	public void setClassType(Class t)
	{
		this.t=t;
	}

    @Override
    public void process(Object o, Task task) {
        try {
            initOrm();
            reflect(o);
            System.out.println(ToStringBuilder.reflectionToString(o));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void reflect(Object o) throws SQLException {
		Dao<Object, String> dao = DaoManager.createDao(connectionSource, t);
		dao.create(o);
    }

	public void createTable() throws SQLException {
		TableUtils.createTableIfNotExists(connectionSource,t);
	}

    public void initOrm() throws SQLException {
        if (connectionSource == null) {
            String databaseUrl = "jdbc:mysql://localhost/spider";
            connectionSource =new JdbcConnectionSource(databaseUrl);
            connectionSource.setPassword("88636311");
            connectionSource.setUsername("root");
            connectionSource.setDatabaseType(new MysqlDatabaseType());
			createTable();
        }
    }
}

