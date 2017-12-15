package OnlineShoping.Beans;

import java.sql.Blob;

import org.apache.commons.fileupload.FileItem;

public class Product {

	private String productID;
	private FileItem productImage;
	private String productName;
	private String producttType;
	private String description;
	private int productSize;
	private Float productPrice;
	private String state;

	private Blob pImage;
	
	private String supplier;

	private String warehouse;
	
	private String supplierPrice;

	private int quantity;
	
	private String orderStatus;
	
	private String orderIssueDate;
	
	private int availableQuantity;

	public Product() {

	}

	public String getProductID() {
		return productID;
	}

	public void setProductID(String productID) {
		this.productID = productID;
	}

	public FileItem getProductImage() {
		return productImage;
	}

	public void setProductImage(FileItem productImage) {
		this.productImage = productImage;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProducttType() {
		return producttType;
	}

	public void setProducttType(String producttType) {
		this.producttType = producttType;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getProductSize() {
		return productSize;
	}

	public void setProductSize(int productSize) {
		this.productSize = productSize;
	}

	public Float getProductPrice() {
		return productPrice;
	}

	public void setProductPrice(Float productPrice) {
		this.productPrice = productPrice;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public String getSupplier() {
		return supplier;
	}

	public void setSupplier(String supplier) {
		this.supplier = supplier;
	}

	public String getWarehouse() {
		return warehouse;
	}

	public void setWarehouse(String warehouse) {
		this.warehouse = warehouse;
	}

	public String getSupplierPrice() {
		return supplierPrice;
	}

	public void setSupplierPrice(String supplierPrice) {
		this.supplierPrice = supplierPrice;
	}

	public Blob getpImage() {
		return pImage;
	}

	public void setpImage(Blob pImage) {
		this.pImage = pImage;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public String getOrderIssueDate() {
		return orderIssueDate;
	}

	public void setOrderIssueDate(String orderIssueDate) {
		this.orderIssueDate = orderIssueDate;
	}

	public int getAvailableQuantity() {
		return availableQuantity;
	}

	public void setAvailableQuantity(int availableQuantity) {
		this.availableQuantity = availableQuantity;
	}

	
	

}
