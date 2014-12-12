package com.trendata.Process;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by friddle on 12/11/14.
 */

@DatabaseTable(tableName = "iplist")
public class HideMyAssEntity {
	@DatabaseField(generatedId = true)
	public int id;
	@DatabaseField
	public String updatetimes;
	@DatabaseField
	public String ips;
	@DatabaseField
	public int port;
	@DatabaseField
	public String country;
	@DatabaseField
	public int Speed;
	@DatabaseField
	public String ConnectionTime;
	@DatabaseField
	public String type;
}
