package br.com.caelum.vraptor.plugin.pagination.displaytag;

import static br.com.caelum.vraptor.plugin.pagination.PagingResults.RequestParameters.ASC;
import static br.com.caelum.vraptor.plugin.pagination.PagingResults.RequestParameters.DESC;
import static br.com.caelum.vraptor.plugin.pagination.PagingResults.RequestParameters.PAGE;
import static br.com.caelum.vraptor.plugin.pagination.PagingResults.RequestParameters.SORT_CRITERION;
import static br.com.caelum.vraptor.plugin.pagination.PagingResults.RequestParameters.SORT_DIRECTION;
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.junit.Ignore;
import org.junit.Test;

import br.com.caelum.vraptor.plugin.pagination.SortOrder;

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
		when(criteria.setFirstResult(anyInt())).thenReturn(criteria);
		
		DisplayTagPagingResults<Object> pagingResults = new DisplayTagPagingResults<Object>(request);
		pagingResults.fetchResults(criteria);
		
		verify(criteria, never()).addOrder(any(Order.class));
	}
	
	@Test
	public void shouldFetchFirstPageOfResults() {
		HttpServletRequest request = mock(HttpServletRequest.class);
		Criteria criteria = mock(Criteria.class);
		
		when(request.getParameter(PAGE)).thenReturn("1");
		when(criteria.setFirstResult(anyInt())).thenReturn(criteria);
		DisplayTagPagingResults<Object> pagingResults = new DisplayTagPagingResults<Object>(request);
		
		pagingResults.fetchResults(criteria);
		
		verify(criteria).setFirstResult(0);
		verify(criteria).setMaxResults(pagingResults.getObjectsPerPage());
	}
	
	@Test
	public void shouldFetchSecondPageOfResults() {
		HttpServletRequest request = mock(HttpServletRequest.class);
		Criteria criteria = mock(Criteria.class);
		
		when(request.getParameter(PAGE)).thenReturn("2");
		when(criteria.setFirstResult(anyInt())).thenReturn(criteria);
		DisplayTagPagingResults<Object> pagingResults = new DisplayTagPagingResults<Object>(request);
		
		pagingResults.fetchResults(criteria);
		
		verify(criteria).setFirstResult(10);
		verify(criteria).setMaxResults(pagingResults.getObjectsPerPage());
	}
	
	@Test
	public void shouldFetchFirstPageOfResultsIfNoPageParameterIsNotDefined() {
		HttpServletRequest request = mock(HttpServletRequest.class);
		Criteria criteria = mock(Criteria.class);
		
		when(request.getParameter(PAGE)).thenReturn(null);
		when(criteria.setFirstResult(anyInt())).thenReturn(criteria);
		DisplayTagPagingResults<Object> pagingResults = new DisplayTagPagingResults<Object>(request);
		
		pagingResults.fetchResults(criteria);
		
		verify(criteria).setFirstResult(0);
		verify(criteria).setMaxResults(pagingResults.getObjectsPerPage());
	}
	
	@Test
	public void shouldSetTotalNumberOfResultRows() {
		HttpServletRequest request = mock(HttpServletRequest.class);
		Criteria criteria = mock(Criteria.class);
		
		when(criteria.setFirstResult(anyInt())).thenReturn(criteria);
		when(criteria.uniqueResult()).thenReturn(20);
		
		DisplayTagPagingResults<Object> pagingResults = new DisplayTagPagingResults<Object>(request);
		
		pagingResults.fetchResults(criteria);
		
		assertEquals(20, pagingResults.getTotalNumberOfRows());
	}
	
	@Test
	public void shouldUseDefaultOrderIfHasNoSortCriterion() {
		HttpServletRequest request = mock(HttpServletRequest.class);
		Criteria criteria = mock(Criteria.class);
		
		when(criteria.setFirstResult(anyInt())).thenReturn(criteria);
		when(criteria.uniqueResult()).thenReturn(20);
		DisplayTagPagingResults<Object> pagingResults = new DisplayTagPagingResults<Object>(request);
		
		pagingResults.setDefaultOrder("name", SortOrder.ASCENDING);
		pagingResults.fetchResults(criteria);
		
		verify(criteria).addOrder(any(Order.class));
	}
}
