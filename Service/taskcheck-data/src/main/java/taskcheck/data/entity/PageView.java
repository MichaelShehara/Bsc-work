/*
 * Copyright (c) iHub 2021. All rights reserved. <br><br> 
 *
 */
package taskcheck.data.entity;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.fasterxml.jackson.annotation.JsonView;

import taskcheck.data.view.Views;

/**
 * The Class PageView.
 */
public class PageView {
	
	/** The content. */
	@JsonView(Views.BaseEntity.class)
	private List<?> content;
	
	/** The first. */
	private boolean first;
	
	/** The last. */
	private boolean last;
	
	/** The number. */
	private int number;
	
	/** The num of elements. */
	private int numOfElements;
	
	/** The pageable. */
	private Pageable pageable;
	
	/** The size. */
	private int size;
	
	/** The sort. */
	private Sort sort;
	
	/** The total pages. */
	private int totalPages;
	
	/**
	 * Gets the content.
	 *
	 * @return the content
	 */
	@JsonView(Views.PageView.class)
	public List<?> getContent() {
		return content;
	}
	
	/**
	 * Sets the content.
	 *
	 * @param content the new content
	 */
	public void setContent(List<?> content) {
		this.content = content;
	}
	
	/**
	 * Checks if is first.
	 *
	 * @return true, if is first
	 */
	@JsonView(Views.PageView.class)
	public boolean isFirst() {
		return first;
	}
	
	/**
	 * Sets the first.
	 *
	 * @param first the new first
	 */
	public void setFirst(boolean first) {
		this.first = first;
	}
	
	/**
	 * Checks if is last.
	 *
	 * @return true, if is last
	 */
	@JsonView(Views.PageView.class)
	public boolean isLast() {
		return last;
	}
	
	/**
	 * Sets the last.
	 *
	 * @param last the new last
	 */
	public void setLast(boolean last) {
		this.last = last;
	}
	
	/**
	 * Gets the number.
	 *
	 * @return the number
	 */
	@JsonView(Views.PageView.class)
	public int getNumber() {
		return number;
	}
	
	/**
	 * Sets the number.
	 *
	 * @param number the new number
	 */
	public void setNumber(int number) {
		this.number = number;
	}
	
	/**
	 * Gets the num of elements.
	 *
	 * @return the num of elements
	 */
	@JsonView(Views.PageView.class)
	public int getNumOfElements() {
		return numOfElements;
	}
	
	/**
	 * Sets the num of elements.
	 *
	 * @param numOfElements the new num of elements
	 */
	public void setNumOfElements(int numOfElements) {
		this.numOfElements = numOfElements;
	}
	
	
	/**
	 * Gets the pageable.
	 *
	 * @return the pageable
	 */
	public Pageable getPageable() {
		return pageable;
	}
	
	/**
	 * Sets the pageable.
	 *
	 * @param pageable the new pageable
	 */
	public void setPageable(Pageable pageable) {
		this.pageable = pageable;
	}
	
	/**
	 * Gets the size.
	 *
	 * @return the size
	 */
	@JsonView(Views.PageView.class)
	public int getSize() {
		return size;
	}
	
	/**
	 * Sets the size.
	 *
	 * @param size the new size
	 */
	public void setSize(int size) {
		this.size = size;
	}
	
	/**
	 * Gets the sort.
	 *
	 * @return the sort
	 */
	@JsonView(Views.PageView.class)
	public Sort getSort() {
		return sort;
	}
	
	/**
	 * Sets the sort.
	 *
	 * @param sort the new sort
	 */
	public void setSort(Sort sort) {
		this.sort = sort;
	}
	
	/**
	 * Gets the total pages.
	 *
	 * @return the total pages
	 */
	@JsonView(Views.PageView.class)
	public int getTotalPages() {
		return totalPages;
	}

	/**
	 * Sets the total pages.
	 *
	 * @param totalPages the new total pages
	 */
	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}
	
	
	
}
