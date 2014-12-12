package com.trendata.Constant;

import com.j256.ormlite.logger.Logger;
import com.j256.ormlite.logger.LoggerFactory;

import java.sql.Timestamp;

/**
 * Created by friddle on 12/11/14.
 */
public class TypeFun {
	static Logger log= LoggerFactory.getLogger(TypeFun.class.getName());
	public static Timestamp getFromTime(String time,int formattype)
	{
		return Timestamp.valueOf(time);
	}

	public static Integer String2Int(String value) {
		value=value.replaceAll("\\D+","");
        Integer mNumber = null;
        try {
            mNumber = Integer.valueOf(value);
        } catch (Exception e) {
			log.warn(e.getMessage());
        }
        return mNumber;
    }

    public static double String2Double(String value) {
        double mNumber = 0;
        try {
            mNumber = Double.valueOf(value);
        } catch (Exception e) {
			log.warn(e.getMessage());
        }
        return mNumber;
    }
}
