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
    public String host="jdbc:mysql://localhost/spider";
    public String user="root";
    public String passwd="qushu_2018";
    public String type="mysql";
    JdbcConnectionSource connectionSource;
	Class t;
	public void setClassType(Class t)
	{
		this.t=t;
	}

    public void setDatabase(String host,String user,String passwd,String type) throws SQLException {
        this.host=host;
        this.user=user;
        this.passwd=passwd;
        this.type=type;
        initOrm();
    }

    public OrmPipeline()
    {
        try
        {
            initOrm();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void process(Object o, Task task) {
        try {
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
        connectionSource =new JdbcConnectionSource(this.host);
        connectionSource.setPassword(this.passwd);
        connectionSource.setUsername(this.user);
        connectionSource.setDatabaseType(new MysqlDatabaseType());
        if(this.type.toLowerCase().contains("mysql"))
        {
            connectionSource.setDatabaseType(new MysqlDatabaseType());
        }
        createTable();
    }
}

