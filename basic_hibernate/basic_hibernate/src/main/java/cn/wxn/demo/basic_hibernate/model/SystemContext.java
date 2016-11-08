package cn.wxn.demo.basic_hibernate.model;

public class SystemContext {

	
	private ThreadLocal<Integer> itemsOfPage;
	
	private ThreadLocal<Integer> indexOfPage;
	
	private ThreadLocal<String> sort;
	
	private ThreadLocal<String> order;
	
	

	public Integer getItemsOfPage() {
		return itemsOfPage.get();
	}

	public void setItemsOfPage(Integer itemsOfPage) {
		this.itemsOfPage.set(itemsOfPage);
	}

	public Integer getIndexOfPage() {
		return indexOfPage.get();
	}

	public void setIndexOfPage(Integer indexOfPage) {
		this.indexOfPage.set(indexOfPage);
	}

	public String getSort() {
		return sort.get();
	}

	public void setSort(String sort) {
		this.sort.set(sort);
	}

	public String getOrder() {
		return order.get();
	}

	public void setOrder(String order) {
		this.order.set(order);
	}
	
	public void removeItemsOfPage(){
		this.indexOfPage.remove();
	}
	
	public void removeIndexOfPage(){
		this.indexOfPage.remove();
	}
	
	public void removeSort(){
		this.sort.remove();
	}
	
	public void removeOrder(){
		this.order.remove();
	}
	
}
