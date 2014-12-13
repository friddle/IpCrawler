package com.trendata;


import com.trendata.Constant.OrmPipeline;
import com.trendata.Process.HideMyAss;
import org.apache.commons.cli.*;
import org.apache.http.HttpHost;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.model.OOSpider;

import java.sql.SQLException;

/**
 * Created by friddle on 12/12/14.
 */
public class Main {
    public static void main(String[] args) throws ParseException, SQLException {
        Options options = new Options();
        options.addOption("host", true, "databasehost");
        options.addOption("user", true, "user");
        options.addOption("passwd", true, "password");
        options.addOption("url", true, "url to crawl");
        options.addOption("proxy", true, "must be host:port mode");
        options.addOption("help",false,"the helper");
        CommandLineParser parser = new PosixParser();
        CommandLine cmd = parser.parse(options, args);
        if(cmd.hasOption("help")||cmd.getOptionValue("url")==null)
        {
            printHelper();
            System.exit(0);
        }
        String host=cmd.getOptionValue("host");
        String user=cmd.getOptionValue("user");
        String password=cmd.getOptionValue("passwd");
        String proxy=cmd.getOptionValue("proxy");
        String url=cmd.getOptionValue("url");
        OrmPipeline mPipeline=new OrmPipeline();
        if(host!=null)
        {
            mPipeline.setDatabase(host,user,password, "mysql");
        }
        HideMyAss process=new HideMyAss();
        process.setPipeline(mPipeline);
        if(proxy!=null)
        {
            String proxyhost=proxy.split(":")[0].toString();
            String proxyport=proxy.split(":")[1].toString();
            Site mSite=new Site();
            HttpHost mHost=new HttpHost(proxyhost,Integer.valueOf(proxyport));
            process.setOnSite(mSite);
        }
        OOSpider.create(process).addUrl(url).start();
    }

    public static void printHelper()
    {
        System.out.println("usage: IpCrawl -url `url from kickass` ");
        System.out.println("you can set database by set -host 'host' -user 'user' -passwd 'passwd' -type 'database type' ");
        System.out.println("you can set httpproxy by set -proxy 'proxy' ");
    }
}
