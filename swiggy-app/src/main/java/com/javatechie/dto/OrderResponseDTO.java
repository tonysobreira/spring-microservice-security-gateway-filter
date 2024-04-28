package com.javatechie.dto;

import java.util.Date;
import java.util.Objects;

public class OrderResponseDTO {

	private String orderId;

	private String name;

	private int qty;

	private double price;

	private Date orderDate;

	private String status;

	private int estimateDeliveryWindow;

	public OrderResponseDTO() {
	}

	public OrderResponseDTO(String orderId, String name, int qty, double price, Date orderDate, String status,
			int estimateDeliveryWindow) {
		this.orderId = orderId;
		this.name = name;
		this.qty = qty;
		this.price = price;
		this.orderDate = orderDate;
		this.status = status;
		this.estimateDeliveryWindow = estimateDeliveryWindow;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getQty() {
		return qty;
	}

	public void setQty(int qty) {
		this.qty = qty;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public Date getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getEstimateDeliveryWindow() {
		return estimateDeliveryWindow;
	}

	public void setEstimateDeliveryWindow(int estimateDeliveryWindow) {
		this.estimateDeliveryWindow = estimateDeliveryWindow;
	}

	@Override
	public int hashCode() {
		return Objects.hash(orderId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		OrderResponseDTO other = (OrderResponseDTO) obj;
		return Objects.equals(orderId, other.orderId);
	}

	@Override
	public String toString() {
		return "OrderResponseDTO [orderId=" + orderId + ", name=" + name + ", qty=" + qty + ", price=" + price
				+ ", orderDate=" + orderDate + ", status=" + status + ", estimateDeliveryWindow="
				+ estimateDeliveryWindow + "]";
	}

}
