package com.trendata.Process;

import org.junit.Test;
import us.codecraft.webmagic.selector.Html;

import java.util.List;

/**
 * Created by friddle on 12/12/14.
 */
public class HideMyAssTest {

	@Test
	public void process()
	{
		    String ipentry="a";
			HideMyAssEntity ass=new HideMyAssEntity();
			Html ipentrys=new Html(ipentry);
			List<String> items=ipentrys.xpath("/tr/td").all();
			ass.updatetimes=new Html(items.get(0)).xpath("//span/text()").toString();
			ass.updatetimes=new Html(items.get(1)).xpath("//span/text()").toString();
			ass.port=Integer.valueOf(new Html(items.get(2)).xpath("//text()").toString());
			ass.country=new Html(items.get(3)).xpath("//span/text()").toString();
			ass.Speed=Integer.valueOf(new Html(items.get(4)).xpath("//div/@value").toString());
			ass.ConnectionTime=new Html(items.get(5)).xpath("//div/@value").toString();
			ass.type=new Html(items.get(6)).xpath("//test()").toString();
	}
}
