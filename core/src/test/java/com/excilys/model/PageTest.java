package com.excilys.model;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import junit.framework.TestCase;

public class PageTest extends TestCase{
	private int maxElement;
	private ArrayList<String> list;
	private Page<String> page;
	
	@Override
	@Before
	public void setUp() {
		list = new ArrayList<String>();
		maxElement = 2;
		page = new Page<String>(list, maxElement);
	}
	
	@Test
	public void testPrecPage() {
		list.add("Page 1");
		list.add("Page 1");
		list.add("Page 2");
		list.add("Page 2");
		list.add("Page 3");
		
		page.setList(list);
		int numPage = 2;
		page.setNumPage(numPage);
		
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
		list.add("Page 1");
		list.add("Page 1");
		list.add("Page 2");
		list.add("Page 2");
		list.add("Page 3");
		
		page.setList(list);
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
		list.add("Page 1");
		list.add("Page 1");
		list.add("Page 3");
		
		page.setList(list);
		int numPage = 0;
		page.setNumPage(0);
		
		assertEquals(list.subList(maxElement*numPage, maxElement*(numPage+1)), page.currentPage());
		assertEquals(numPage, page.getNumPage());
		
		numPage++;
		page.setNumPage(1);
		
		assertEquals(list.subList(maxElement*numPage, list.size()), page.currentPage());
		assertEquals(numPage, page.getNumPage());
	}
	
	@Test
	public void testSetNumPage() {
		list.add("Page 1");
		list.add("Page 1");
		list.add("Page 3");
		
		page.setList(list);
		page.setNumPage(0);
		assertEquals(0, page.getNumPage());
		
		page.setNumPage(-1);
		assertEquals(0, page.getNumPage());
		
		page.setNumPage(1000);
		assertEquals(page.getMaxPage(), page.getNumPage());
	}
}
