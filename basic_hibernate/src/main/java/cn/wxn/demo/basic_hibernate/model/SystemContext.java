package cn.wxn.demo.basic_hibernate.model;

public class SystemContext {
	
	/**
	 * 一页中的条目数
	 */
	private static ThreadLocal<Integer> itemCountOfAPage = new ThreadLocal<Integer>();
	
	/**
	 * 页面偏移量， 
	 */
	private static ThreadLocal<Integer> pageOffset= new ThreadLocal<Integer>();
	
	/**
	 * 分页的排序字段
	 */
	private static ThreadLocal<String> sort= new ThreadLocal<String>();
	
	
	/**
	 * 列表的排序方式
	 */
	private static ThreadLocal<String> order= new ThreadLocal<String>();

	
	public static Integer getItemCountOfAPage() {
		return itemCountOfAPage.get();
	}

	public static void setItemCountOfAPage(Integer _itemCountOfAPage) {
		itemCountOfAPage.set(_itemCountOfAPage);
	}

	public static Integer getPageOffset() {
		return pageOffset.get();
	}

	public static void setPageOffset(Integer _pageOffset) {
		pageOffset.set(_pageOffset);
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
	
	public static void removeItemCountOfAPage(){
		itemCountOfAPage.remove();
	}
	
	public static void removePageOffset(){
		pageOffset.remove();
	}
	
	public static void removeSort(){
		sort.remove();
	}
	
	public static void removeOrder(){
		order.remove();
	}
	
}
