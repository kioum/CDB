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
		if(numPage < getMaxPage())
			numPage++;
		return currentPage();
	}

	public int startPage() {
		if(numPage + 4 > getMaxPage() && numPage > 3)
			return getMaxPage()-4;
		if(numPage < 3)
			return 0;
		return numPage-2;
	}
	
	public int endPage() {
		if(numPage < 3 && getMaxPage() >= 5)
			return 4;
		if(numPage < 3 && getMaxPage() < 5)
			return getMaxPage();
		if(numPage + 4 > getMaxPage())
			return getMaxPage();
		return numPage+2;
	}
	
	public int getMaxPage() {
		return (int) (Math.floor(list.size()/maxElement));
	}

	public int getNumPage() {
		return numPage;
	}

	public void setNumPage(int numPage) {
		if(numPage >= 0 && numPage <= getMaxPage())
			this.numPage = numPage;
	}

	public void setMaxElement(int maxElement) {
		this.maxElement = maxElement;
		if(maxElement*(numPage+1) > list.size())
			numPage = getMaxPage();
	}

	public List<T> getList() {
		return list;
	}

	public void setList(List<T> list) {
		this.list = list;
	}
}
