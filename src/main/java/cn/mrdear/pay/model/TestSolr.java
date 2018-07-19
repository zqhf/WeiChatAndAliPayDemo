package cn.mrdear.pay.model;

import java.io.Serializable;

import org.apache.solr.client.solrj.beans.Field;

public class TestSolr{

	@Field
	private String id;
	@Field
	private String title;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	@Override
	public String toString() {
		return "TestSolr [id=" + id + ", title=" + title + "]";
	}
}
