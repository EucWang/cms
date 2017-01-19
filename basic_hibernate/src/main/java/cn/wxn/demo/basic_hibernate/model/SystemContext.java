package cn.wxn.demo.basic_hibernate.model;

public class SystemContext {
	
	/**
	 * 一页中的条目数
	 */
	private static ThreadLocal<Integer> itemsOfPage = new ThreadLocal<Integer>();
	
	/**
	 * 页面索引
	 */
	private static ThreadLocal<Integer> indexOfPage= new ThreadLocal<Integer>();
	
	/**
	 * 分页的排序字段
	 */
	private static ThreadLocal<String> sort= new ThreadLocal<String>();
	
	
	/**
	 * 列表的排序方式
	 */
	private static ThreadLocal<String> order= new ThreadLocal<String>();

	
	public static Integer getItemsOfPage() {
		return itemsOfPage.get();
	}

	public static void setItemsOfPage(Integer _itemsOfPage) {
		itemsOfPage.set(_itemsOfPage);
	}

	public static Integer getIndexOfPage() {
		return indexOfPage.get();
	}

	public static void setIndexOfPage(Integer _indexOfPage) {
		indexOfPage.set(_indexOfPage);
	}

	public static String getSort() {
		return sort.get();
	}

	public static void setSort(String _sort) {
		sort.set(_sort);
	}

	public static String getOrder() {
		return order.get();
	}

	public static void setOrder(String _order) {
		order.set(_order);
	}
	
	public static void removeItemsOfPage(){
		indexOfPage.remove();
	}
	
	public static void removeIndexOfPage(){
		indexOfPage.remove();
	}
	
	public static void removeSort(){
		sort.remove();
	}
	
	public static void removeOrder(){
		order.remove();
	}
	
}
