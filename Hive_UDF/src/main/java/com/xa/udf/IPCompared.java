package com.xa.udf;

import org.apache.hadoop.hive.ql.exec.UDF;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.CloudSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocumentList;

import com.xa.solr.SolrClient;
import com.xa.util.ConfigUtil;

public class IPCompared extends UDF {
	private static String collectionName = null;
	private static CloudSolrServer solrServer = null;
	private static String ip_start_name = null;
	private static String ip_end_name = null;
	
	public static void main(String[] args) {
		System.out.println(new IPCompared().evaluate("6"));
	}
	
	static {
	ConfigUtil configutil = new ConfigUtil("conf/zookeeper.properties");
		collectionName = configutil.getString("collection_name");
		ip_start_name = configutil.getString("ipStart");
		ip_end_name = configutil.getString("ipEnd");
		solrServer = SolrClient.newInstance().getClient(collectionName);
	}
	
	public String evaluate(String content) {
		boolean isIn = false;
		SolrQuery params = new SolrQuery();
		params.setQuery(ip_end_name + ":[" + content + " TO *]");
		params.addFilterQuery(ip_start_name + ":[* TO " + content + "]");

		QueryResponse response = null;
		try {
			response = solrServer.query(params);
			SolrDocumentList docs = response.getResults();
			long number = docs.getNumFound();
			if (number > 0) {
				isIn = true;
			}
		} catch (SolrServerException e) {
			e.printStackTrace();
		}
		return String.valueOf(isIn);
	}
}
