package com.excilys.model;

import java.util.List;

public class Page<T> {
	private List<T> list;
	private int maxElement;
	private int numPage;

	public Page(List<T> list, int maxElement){
		this.list = list;
		this.maxElement = maxElement;
		this.numPage = 0;
	}

	public List<T> currentPage() {
		int beg = numPage*maxElement;
		int end = (numPage + 1)*maxElement;
		
		if(end >= list.size())
			end = list.size();
		
		return list.subList(beg, end);
	}

	public List<T> precPage() {
		if(numPage > 0)
			numPage--;
		return currentPage();
	}

	public List<T> nextPage() {
		int maxPage = (int) (Math.floor(list.size()/maxElement));
		if(numPage < maxPage)
			numPage++;
		return currentPage();
	}

	public int getNumPage() {
		return numPage;
	}

	public void setNumPage(int numPage) {
		this.numPage = numPage;
	}

	public void setMaxElement(int maxElement) {
		this.maxElement = maxElement;
	}
}
