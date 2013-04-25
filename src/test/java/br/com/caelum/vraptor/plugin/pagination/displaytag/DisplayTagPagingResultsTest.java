package br.com.caelum.vraptor.plugin.pagination.displaytag;

import static br.com.caelum.vraptor.plugin.pagination.PagingResults.RequestParameters.ASC;
import static br.com.caelum.vraptor.plugin.pagination.PagingResults.RequestParameters.DESC;
import static br.com.caelum.vraptor.plugin.pagination.PagingResults.RequestParameters.SORT_CRITERION;
import static br.com.caelum.vraptor.plugin.pagination.PagingResults.RequestParameters.SORT_DIRECTION;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.junit.Ignore;
import org.junit.Test;

public class DisplayTagPagingResultsTest {
	
	@Test @Ignore("since Order.class don't implement equals and hashcode we can't test with verify")
	public void shouldSortAsc() {
		HttpServletRequest request = mock(HttpServletRequest.class);
		Criteria criteria = mock(Criteria.class);
		
		when(request.getParameter(SORT_DIRECTION)).thenReturn(ASC);
		when(request.getParameter(SORT_CRITERION)).thenReturn("name");
		DisplayTagPagingResults<Object> pagingResults = new DisplayTagPagingResults<Object>(request);
		
		pagingResults.fetchResults(criteria);
		verify(criteria).addOrder(Order.asc("name"));
	}
	
	@Test @Ignore("since Order.class don't implement equals and hashcode we can't test with verify")
	public void shouldSortDesc() {
		HttpServletRequest request = mock(HttpServletRequest.class);
		Criteria criteria = mock(Criteria.class);
		
		when(request.getParameter(SORT_DIRECTION)).thenReturn(DESC);
		when(request.getParameter(SORT_CRITERION)).thenReturn("name");
		DisplayTagPagingResults<Object> pagingResults = new DisplayTagPagingResults<Object>(request);
		
		pagingResults.fetchResults(criteria);
		verify(criteria).addOrder(Order.desc("name"));
	}
	
	@Test
	public void fetchResultsShouldWorkWithoutSort() {
		HttpServletRequest request = mock(HttpServletRequest.class);
		Criteria criteria = mock(Criteria.class);
		
		when(request.getParameter(SORT_DIRECTION)).thenReturn(null);
		
		DisplayTagPagingResults<Object> pagingResults = new DisplayTagPagingResults<Object>(request);
		pagingResults.fetchResults(criteria);
		
		verify(criteria, never()).addOrder(any(Order.class));
	}
}
