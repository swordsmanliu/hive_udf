package com.xa.solr;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.http.client.HttpClient;
import org.apache.solr.client.solrj.impl.CloudSolrServer;
import org.apache.solr.client.solrj.impl.HttpClientUtil;
import org.apache.solr.client.solrj.impl.LBHttpSolrServer;
import org.apache.solr.common.SolrException;
import org.apache.solr.common.params.ModifiableSolrParams;

import com.xa.util.ConfigUtil;


/**
 * solr连接的管理方式
 * 
 * @author rootwang
 * 
 */
public class SolrClient {

	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private static int zkClientTimeout;
	private static int zkConnectTimeout;
	private static String ZK_URL;

	private static SolrClient solrClient = new SolrClient();

	private static CloudSolrServer cloudSolrServer = null;

	static {
		ConfigUtil configutil = new ConfigUtil("conf/zookeeper.properties");
		zkClientTimeout = Integer.valueOf(configutil.getString("zkClientTimeout"));
		zkConnectTimeout = Integer.valueOf(configutil.getString("zkConnectTimeout"));
		ZK_URL = configutil.getString("ZK_URL");
	}

	private CloudSolrServer getCloudSolrClient(String zkHost) {
		ModifiableSolrParams params = new ModifiableSolrParams();
		params.set(HttpClientUtil.PROP_MAX_CONNECTIONS, 5000);
		params.set(HttpClientUtil.PROP_MAX_CONNECTIONS_PER_HOST, 300);
		HttpClient httpClient = HttpClientUtil.createClient(params);

		LBHttpSolrServer lbClient = new LBHttpSolrServer(httpClient);

		cloudSolrServer = new CloudSolrServer(zkHost, lbClient);
		cloudSolrServer.setZkClientTimeout(zkClientTimeout);
		cloudSolrServer.setZkConnectTimeout(zkConnectTimeout);
		cloudSolrServer.connect();

		return cloudSolrServer;
	}

	public static SolrClient newInstance() {
		try {
			if (cloudSolrServer == null) {
				synchronized (SolrClient.class) {
					if (cloudSolrServer == null) {
						cloudSolrServer = solrClient.getCloudSolrClient(ZK_URL);
						System.out.println("create cloudSolrServer success at " + sdf.format(new Date()));
					}
				}
			}
		} catch (SolrException e) {
			System.out
					.println("create cloudSolrServer failed aboat " + e.getMessage() + " at " + sdf.format(new Date()));
			return null;
		}
		return solrClient;
	}

	/**
	 * 根据collection name 获取客户端连接
	 * 
	 * @param collection_name
	 * @return
	 */
	public CloudSolrServer getClient(String collection_name) {
		cloudSolrServer.setDefaultCollection(collection_name);
		return cloudSolrServer;
	}

	public void setCloudSolrServer(CloudSolrServer cloudSolrServer) {
		this.cloudSolrServer = cloudSolrServer;
	}

	public static void main(String[] args) {
		SolrClient sc = SolrClient.newInstance();
		CloudSolrServer css = sc.getClient("tc_par_cust_info_t8");
		System.out.println(css.getDefaultCollection());
	}
}
