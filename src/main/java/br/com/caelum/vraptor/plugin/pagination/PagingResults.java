package br.com.caelum.vraptor.plugin.pagination;

import java.util.List;

import org.hibernate.Criteria;

public interface PagingResults<T> {

	public static final String paginationPropertiesFile = "vraptor-pagination";
	
	public interface RequestParameters {
		String	SORT_CRITERION		= "sort";
		String	PAGE				= "page";
		String	ASC					= "asc";
		String	DESC				= "desc";
		String	SORT_DIRECTION		= "dir";
	}

	int getPageNumber();
	
	void setPageNumber(int pageNumber);
	
	int getTotalNumberOfRows();
	
	void setTotalNumberOfRows(int total);
	
	int getTotalPages();
	
	List<T> getResults();
	
	int getObjectsPerPage();
	
	void setObjectsPerPage(int objectsPerPage);
	
	String getSortCriterion();
	
	void setSortCriterion(String sortCriterion);
	
	SortOrder getFilterSortDirection();
	
	void setSortDirection(SortOrder sortOrderEnum);
	
	int getFirstRecordIndex();
	
	void fetchResults(Criteria criteria);
}