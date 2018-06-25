package com.jesper.model;

import java.io.Serializable;
import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * @author KL-MZY
 *
 */
public class Course implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5914715994393492L;

	private String id ;
	private String title;
	private String url;
	private String author;
	@JSONField(format="yyyy-MM-dd HH:mm:ss")
	private Date addDate;
	
	
	
	private String updateStr;
	private Integer start;
	private Integer end;
	
	
	
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
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public Date getAddDate() {
		return addDate;
	}
	public void setAddDate(Date addDate) {
		this.addDate = addDate;
	}
	
	
	public String getUpdateStr() {
		return updateStr;
	}
	public void setUpdateStr(String updateStr) {
		this.updateStr = updateStr;
	}
	public Integer getStart() {
		return start;
	}
	public void setStart(Integer start) {
		this.start = start;
	}
	public Integer getEnd() {
		return end;
	}
	public void setEnd(Integer end) {
		this.end = end;
	}
	
	
 }
