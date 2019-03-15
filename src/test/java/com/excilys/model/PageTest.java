package com.excilys.model;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import junit.framework.TestCase;

public class PageTest extends TestCase{
	@Test
	public void testPrecPage() {
		ArrayList<String> list = new ArrayList<String>();
		list.add("Page 1");
		list.add("Page 1");
		list.add("Page 2");
		list.add("Page 2");
		list.add("Page 3");
		
		int maxElement = 2;
		
		Page<String> page = new Page<String>(list, maxElement);
		int numPage = 2;
		page.setNumPage(2);
		
		numPage--;
		assertEquals(list.subList(maxElement*numPage, maxElement*(numPage+1)), page.precPage());
		assertEquals(numPage, page.getNumPage());
		
		numPage--;
		assertEquals(list.subList(maxElement*numPage, maxElement*(numPage+1)), page.precPage());
		assertEquals(list.subList(maxElement*numPage, maxElement*(numPage+1)), page.precPage());
		assertEquals(numPage, page.getNumPage());
	}
	
	@Test
	public void testNextPage() {
		ArrayList<String> list = new ArrayList<String>();
		list.add("Page 1");
		list.add("Page 1");
		list.add("Page 2");
		list.add("Page 2");
		list.add("Page 3");
		
		int maxElement = 2;
		
		Page<String> page = new Page<String>(list, maxElement);
		int numPage = 0;
		page.setNumPage(0);
		
		numPage++;
		assertEquals(list.subList(maxElement*numPage, maxElement*(numPage+1)), page.nextPage());
		assertEquals(numPage, page.getNumPage());
		
		numPage++;
		assertEquals(list.subList(maxElement*numPage, list.size()), page.nextPage());
		assertEquals(list.subList(maxElement*numPage, list.size()), page.nextPage());
		assertEquals(numPage, page.getNumPage());
	}
	
	@Test
	public void testCurrentPage() {
		ArrayList<String> list = new ArrayList<String>();
		list.add("Page 1");
		list.add("Page 1");
		list.add("Page 3");
		
		int maxElement = 2;
		
		Page<String> page = new Page<String>(list, maxElement);
		int numPage = 0;
		page.setNumPage(0);
		
		assertEquals(list.subList(maxElement*numPage, maxElement*(numPage+1)), page.currentPage());
		assertEquals(numPage, page.getNumPage());
		
		numPage++;
		page.setNumPage(1);
		
		assertEquals(list.subList(maxElement*numPage, list.size()), page.currentPage());
		assertEquals(numPage, page.getNumPage());
	}
}
