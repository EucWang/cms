package cn.wxn.demo.basic_hibernate.model;

import java.util.List;

/**
 * 分页
 * @author wangxn
 *
 * @param <T>
 */
public class Pager<T> {

	/**
	 * 一页有多少条item
	 */
	private int itemsCountOfAPage;
	
	/**
	 * 当前的页面索引
	 * 第几页
	 */
	private int indexOfPage;
	
	/**
	 * 总条目数
	 */
	private long countOfItems;
	 
	private List<T> datas;
	
	public int getItemsCountOAPage() {
		return itemsCountOfAPage;
	}

	public void setItemsCountOfAPage(int itemsCountOfAPage) {
		this.itemsCountOfAPage = itemsCountOfAPage;
	}

	public int getIndexOfPage() {
		return indexOfPage;
	}

	public void setIndexOfPage(int indexOfPage) {
		this.indexOfPage = indexOfPage;
	}

	public long getCountOfItems() {
		return countOfItems;
	}

	public void setCountOfItems(long countOfItems) {
		this.countOfItems = countOfItems;
	}

	public List<T> getDatas() {
		return datas;
	}

	public void setDatas(List<T> datas) {
		this.datas = datas;
	}
}
