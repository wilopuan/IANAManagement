package com.wlopez.model;

import java.util.List;

import org.springframework.http.HttpStatus;

public class ProccessResponse {

	/**
	 * Estado de la respuesta
	 */
	private HttpStatus status;

	/**
	 * Comentarios a la respuesta
	 */
	private String comment;

	/**
	 * Objetos que acompañan la respuesta
	 */

	private List<Object> items;

	public HttpStatus getStatus() {
		return status;
	}

	public void setStatus(HttpStatus status) {
		this.status = status;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public List<Object> getItems() {
		return items;
	}

	public void setItems(List<Object> items) {
		this.items = items;
	}

	public ProccessResponse(HttpStatus status, String comment, List<Object> items) {
		this.status = status;
		this.comment = comment;
		this.items = items;
	}

	public ProccessResponse() {

	}

}
