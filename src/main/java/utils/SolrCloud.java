package utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.CloudSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;

import cn.mrdear.pay.model.TestSolr;

public class SolrCloud {
    // 低版本的使用
    //private static CloudSolrServer cloudSolrServer;
    private static CloudSolrClient cloudSolrClient;
    private static synchronized CloudSolrClient getCloudSolrServer(final String zkHost) {
        if (cloudSolrClient == null) {
            try {
            	cloudSolrClient = new CloudSolrClient(zkHost);
            	//cloudSolrServer = new CloudSolrServer(zkHost);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return cloudSolrClient;
    }
    //添加索引
    private void addIndex(CloudSolrClient cloudSolrClient2) {
        try {
        	 List<TestSolr> list=new ArrayList<TestSolr>();  
            /*SolrInputDocument doc1 = new SolrInputDocument();
            doc1.setField("id", "421245251215121452521251");
            doc1.setField("title", "张三");
            SolrInputDocument doc2 = new SolrInputDocument();
            doc2.setField("id", "4224558524254245848524243");
            doc2.setField("title", "李四");
            
            SolrInputDocument doc3 = new SolrInputDocument();
            doc3.setField("id", "4543543458643541324153453");
            doc3.setField("title", "王五");
            
            Collection<SolrInputDocument> docs = new ArrayList<SolrInputDocument>();
            docs.add(doc1);
            docs.add(doc2);
            docs.add(doc3);*/
        	 TestSolr testSolr = null;
        	 for(int i=0;i<10;i++){
        		testSolr = new TestSolr();
             	testSolr.setId("111");
             	testSolr.setTitle("aaa");
        	 }
        	
            //cloudSolrClient2.add(docs);
        	list.add(testSolr);
        	//cloudSolrClient2.deleteById("4224558524254245848524243");
        	cloudSolrClient2.addBeans(list);
            cloudSolrClient2.commit();
            
        } catch (SolrServerException e) {
            System.out.println("Add docs Exception !!!");
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("Unknowned Exception!!!!!");
            e.printStackTrace();
        }
        
    }
    
    // 搜索
    public void search(CloudSolrClient cloudSolrClient2, String String) {
        SolrQuery query = new SolrQuery();
        query.setQuery(String);
        try {
            QueryResponse response = cloudSolrClient2.query(query);
            SolrDocumentList docs = response.getResults();
            System.out.println("文档: "+docs.size());
            for (SolrDocument doc : docs) {
                for (Map.Entry<String, Object> entry : doc) {
                    System.out.println("key值: "+entry.getKey() + "=" + entry.getValue());
                }
            }
        } catch (SolrServerException e) {
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("Unknowned Exception!!!!");
            e.printStackTrace();
        }
    }
    
    //删除
    public void deleteAllIndex(CloudSolrClient cloudSolrClient2) {
        try {
            cloudSolrClient2.deleteByQuery("*:*");// delete everything!
            cloudSolrClient2.commit();
        } catch (SolrServerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("Unknowned Exception !!!!");
            e.printStackTrace();
        }
    }
    
    public static void main(String[] args) throws IOException {
        final String zkHost = "127.0.0.1:2181,127.0.0.1:2182,127.0.0.1:2183,127.0.0.1:2184";
        final String defaultCollection = "collection1";
        final int zkClientTimeout = 20000;
        final int zkConnectTimeout = 10000;
        // 低版本的使用
        //CloudSolrServer cloudSolrServer = getCloudSolrServer(zkHost);
    
        CloudSolrClient cloudSolrClient = getCloudSolrServer(zkHost);
        
        System.out.println("The Cloud SolrServer Instance has benn created!");
        // 低版本的使用
//        cloudSolrServer.setDefaultCollection(defaultCollection);
//        cloudSolrServer.setZkClientTimeout(zkClientTimeout);
//        cloudSolrServer.setZkConnectTimeout(zkConnectTimeout);
//        cloudSolrServer.connect();
        cloudSolrClient.setDefaultCollection(defaultCollection);
        cloudSolrClient.setZkClientTimeout(zkClientTimeout);
        cloudSolrClient.setZkConnectTimeout(zkConnectTimeout);
        cloudSolrClient.connect();
        System.out.println("The cloud Server has been connected !!!!");
        SolrCloud test = new SolrCloud();
        // 低版本的使用
//        test.addIndex(cloudSolrServer);
//        test.search(cloudSolrServer, "id:*");
//        test.deleteAllIndex(cloudSolrServer);
//        test.search(cloudSolrServer, "id:*");
       test.addIndex(cloudSolrClient);
        //test.search(cloudSolrClient, "id:*");
//        test.deleteAllIndex(cloudSolrClient);
        System.out.println("hashCode" + test.hashCode());
        cloudSolrClient.close();
    }
    
    
}