package br.com.caelum.vraptor.plugin.pagination.displaytag;

import java.util.List;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.displaytag.pagination.PaginatedList;
import org.displaytag.properties.SortOrderEnum;

import br.com.caelum.vraptor.ioc.Component;
import br.com.caelum.vraptor.ioc.RequestScoped;
import br.com.caelum.vraptor.plugin.pagination.PagingResults;
import br.com.caelum.vraptor.plugin.pagination.SortOrder;

@Component
@RequestScoped
public class DisplayTagPagingResults<T> implements PagingResults<T>, PaginatedList {
	
	private static final Logger logger = Logger.getLogger(DisplayTagPagingResults.class);
	private int pageNumber;
	private int	objectsPerPage;
	private int	totalNumberOfRows;
	private List<T> list;
	private SortOrderEnum sortDirection	= SortOrderEnum.ASCENDING;
	private String sortCriterion;
	
	public DisplayTagPagingResults(HttpServletRequest request) {
		configureObjectsPerPage();
		
		configurePagingResultTo(request);
	}

	private void configurePagingResultTo(HttpServletRequest request) {
		sortCriterion = request.getParameter(PagingResults.RequestParameters.SORT);
		String requestSortDirection = request.getParameter(PagingResults.RequestParameters.DIRECTION);
		
		if(PagingResults.RequestParameters.DESC.equals(requestSortDirection)) {
			sortDirection = SortOrderEnum.DESCENDING;
		} else {
			sortDirection = SortOrderEnum.ASCENDING;
		}
		
		String page = request.getParameter(PagingResults.RequestParameters.PAGE);
		if(page == null) {
			pageNumber = 1;
		} else {
			pageNumber = Integer.parseInt(page);
		}
	}

	private void configureObjectsPerPage() {
		String stringObjectsPerPage = getBundle().getString("objects-per-page");
		if(stringObjectsPerPage == null) throw new IllegalArgumentException("No key 'objects-per-page' found in displaytag.properties.");

		logger.info("Using objects per page: " + stringObjectsPerPage);
		setObjectsPerPage(Integer.parseInt(stringObjectsPerPage));
	}
	
	public ResourceBundle getBundle() {
		return ResourceBundle.getBundle(PagingResults.paginationPropertiesFile);
	}
	
	@Override
	public int getPageNumber() {
		return pageNumber;
	}
	
	@Override
	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}
	
	@Override
	public int getTotalNumberOfRows() {
		return totalNumberOfRows;
	}
	
	@Override
	public void setTotalNumberOfRows(int total) {
		totalNumberOfRows = total;
	}
	
	@Override
	public int getTotalPages() {
		return (int) Math.ceil(((double) totalNumberOfRows) / objectsPerPage);
	}
	
	@Override
	public List<T> getList() {
		return list;
	}
	
	@Override
	public void setList(List<T> resultList) {
		list = resultList;
	}
	
	@Override
	public int getObjectsPerPage() {
		return objectsPerPage;
	}
	
	@Override
	public void setObjectsPerPage(int objectsPerPage) {
		this.objectsPerPage = objectsPerPage;
	}
	
	@Override
	public String getSortCriterion() {
		return sortCriterion;
	}
	
	@Override
	public void setSortCriterion(String sortCriterion) {
		this.sortCriterion = sortCriterion;
	}
	
	@Override
	public SortOrder getFilterSortDirection() {
		if(sortDirection.equals(SortOrderEnum.ASCENDING)) {
			return SortOrder.ASCENDING;
		}
		else {
			return SortOrder.DESCENDING;
		}
	}
	
	@Override
	public void setSortDirection(SortOrder sortOrder) {
		switch(sortOrder) {
			case ASCENDING: sortDirection = SortOrderEnum.ASCENDING; break;
			case DESCENDING: sortDirection = SortOrderEnum.DESCENDING; break;
		}
	}
	
	@Override
	public SortOrderEnum getSortDirection() {
		return sortDirection;
	}
	
	@Override
	public int getFirstRecordIndex() {
		return (pageNumber - 1) * objectsPerPage;
	}
	
	@Override
	public int getFullListSize() {
		return totalNumberOfRows;
	}
	
	/**
	 * No implementation
	 */
	@Override
	public String getSearchId() {
		return null;
	}
}
