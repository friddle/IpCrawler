package com.trendata.Constant;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

/**
 * Created by friddle on 7/9/14.
 */
public class LittleSpider {

	static Logger logger = Logger.getLogger(LittleSpider.class);

	public static synchronized String getHtml(String url) throws IOException {
		logger.info("little spider crawl url:" + url);
		HttpClient client = getDefaultHttpClient();
		HttpGet mGet = new HttpGet(url);
		try {
			HttpResponse response = client.execute(mGet);
			String util = EntityUtils.toString(response.getEntity());
			return util;
		} catch (Exception e) {
			logger.error(e);
		}
		return null;
	}

	public static synchronized HttpClient getDefaultHttpClient() {
		RequestConfig.Builder requestBuilder = RequestConfig.custom();
		requestBuilder = requestBuilder.setConnectTimeout(10);
		requestBuilder = requestBuilder.setConnectionRequestTimeout(10);
		HttpClientBuilder builder = HttpClientBuilder.create();
		builder.setDefaultRequestConfig(requestBuilder.build());
		HttpClient client = HttpClientBuilder.create().build();
		return client;

	}

	public static String getHtml(String url, List<NameValuePair> headers) throws IOException {
		logger.info("little spider crawl url:" + url);
		HttpClient client = getDefaultHttpClient();
		HttpGet mGet = getHeaderHttpGet(url, headers);
		try {
			HttpResponse response = client.execute(mGet);
			String util = EntityUtils.toString(response.getEntity());
			return util;
		} catch (Exception e) {
			logger.error(e);
		}
		return null;
	}

	private static HttpGet getHeaderHttpGet(String url, List<NameValuePair> headers) {
		HttpGet mGet = new HttpGet(url);
		if (headers != null) {
			Iterator<NameValuePair> itrHeaders = headers.iterator();
			while (itrHeaders.hasNext()) {
				NameValuePair header = itrHeaders.next();
				mGet.addHeader(header.getName(), header.getValue());
			}
		}
		return mGet;
	}
}
