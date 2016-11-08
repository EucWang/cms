package cn.wxn.demo.basic_hibernate.model;

import java.util.List;

public class Pager<T> {

	private int itemsOfPage;
	
	private int indexOfPage;
	
	private int pages;
	 
	private List<T> datas;
	
	
	
	public int getItemsOfPage() {
		return itemsOfPage;
	}

	public void setItemsOfPage(int itemsOfPage) {
		this.itemsOfPage = itemsOfPage;
	}

	public int getIndexOfPage() {
		return indexOfPage;
	}

	public void setIndexOfPage(int indexOfPage) {
		this.indexOfPage = indexOfPage;
	}

	public int getPages() {
		return pages;
	}

	public void setPages(int pages) {
		this.pages = pages;
	}

	public List<T> getDatas() {
		return datas;
	}

	public void setDatas(List<T> datas) {
		this.datas = datas;
	}
 
	
	
}
