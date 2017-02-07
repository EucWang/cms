package cn.wxn.demo.entity;

public class Role {

	
	private Long id;
	
	private String name;
	
	private String description;

	public Role( String name, String description) {
		super();
		this.name = name;
		this.description = description;
	}

	public Role() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return "Role [id=" + id + ", name=" + name + ", description=" + description + "]";
	}
	
	
}
