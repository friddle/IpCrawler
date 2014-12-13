package com.trendata.Process;

import com.trendata.Constant.OrmPipeline;
import com.trendata.Constant.TypeFun;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Html;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by friddle on 12/11/14.
 */
public class HideMyAss implements PageProcessor {
    OrmPipeline mPipeline=new OrmPipeline();
    Site mSite=Site.me();

    public void setPipeline(OrmPipeline pipeline)
    {
        this.mPipeline=pipeline;
    }

    public void setOnSite(Site site)
    {
        this.mSite=site;
    }

	@Override
	public void process(Page page) {
		for (String ipentry : page.getHtml().xpath("//section[@class=\"proxy-results\"]//tbody/tr").all()) {
			HideMyAssEntity ass = new HideMyAssEntity();
			ipentry = ipentry.replace("td", "lastd");
			Html ipentrys = new Html(ipentry);
			List<String> items = ipentrys.xpath("//lastd").all();
			if (items.size() == 0) {
				continue;
			}
			try {
				ass.updatetimes = new Html(items.get(0)).xpath("//span/text()").toString();
				ass.port = TypeFun.String2Int(new Html(items.get(2)).xpath("//lastd/text()").toString());
				ass.country = new Html(items.get(3)).xpath("//span/text()").toString();
				ass.Speed = Integer.valueOf(new Html(items.get(4)).xpath("//div/@value").regex("\\w+").toString());
				ass.ConnectionTime = new Html(items.get(5)).xpath("//div/@value").toString();
				ass.type = new Html(items.get(6)).xpath("//lastd/text()").regex("\\w+").toString();
				ass.ips = getIps(items.get(1));
				mPipeline.setClassType(ass.getClass());
				mPipeline.process(ass, null);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		try {
			String nexturl = page.getHtml().xpath("//li[@class=\"next\"]/a/@href").toString();
			page.addTargetRequest(nexturl);
		} catch (Exception e) {

		}
	}

	public String getIps(String text) {
		List<String> matches = new ArrayList<String>();
		for (String match : new Html(text).regex("\\S+\\{display:none\\}").all()) {
			match = match.replace("{display:none}", " ").replaceAll("\\s", "").replace(".", "");
			matches.add(match);
		}
		List<Integer> ips = new ArrayList<Integer>();
		List<Node> items = Jsoup.parse(text).select("span").get(0).childNodes();
		for (int i = 0; i < items.size(); i++) {
			Node item = items.get(i);
			if (item.getClass().toString().contains("TextNode")) {
				Integer nums = TypeFun.String2Int(item.toString());
				if (nums != null) {
					ips.add(nums);
				}
			} else if (item.nodeName().equals("span")) {
				if (item.attr("class") != null && (!matches.contains(item.attr("class"))) && (!item.attr("style").contains("none"))) {
					Integer nums = TypeFun.String2Int(((Element) item).ownText());
					if (nums != null) {
						ips.add(nums);
					}
				}
			} else if (item.nodeName().equals("div")) {
				if (item.attr("style") != null && (item.attr("style").contains("inline"))) {
					ips.add(TypeFun.String2Int(((Element) item).ownText()));
				}
			}
		}
		if (ips.size() == 4) {
			String ipstring = String.valueOf(ips.get(0));
			for (int i = 1; i < 4; i++) {
				ipstring = ipstring + "." + ips.get(i);
			}
			return ipstring;
		}
		return null;
	}

	@Override
	public Site getSite() {
        return this.mSite;
	}


	public static void main(String[] args) {
		Spider.create(new HideMyAss()).addUrl("http://proxylist.hidemyass.com/search-1415795#listable").run();
	}
}
