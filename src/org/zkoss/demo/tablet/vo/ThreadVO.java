package org.zkoss.demo.tablet.vo;

public class ThreadVO {
	private String title;
	private String author;
	private String lastPoster;
	private int post;
	private String url;
	private boolean popular;
	private boolean hot;
	private String category;
	
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public int getPost() {
		return post;
	}
	public void setPost(int post) {
		this.post = post;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getLastPoster() {
		return lastPoster;
	}
	public void setLastPoster(String lastPoster) {
		this.lastPoster = lastPoster;
	}
	public boolean isPopular() {
		return popular;
	}
	public void setPopular(boolean popular) {
		this.popular = popular;
	}
	public boolean isHot() {
		return hot;
	}
	public void setHot(boolean hot) {
		this.hot = hot;
	}
}