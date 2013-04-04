package br.com.caelum.vraptor.plugin.pagination;

import java.util.List;

public interface PagingResults<T> {

	int	DEFAULT_OBJECTS_PER_PAGE	= 10;
	
	public interface RequestParameters {
		String	SORT		= "sort";
		String	PAGE		= "page";
		String	ASC			= "asc";
		String	DESC		= "desc";
		String	DIRECTION	= "dir";
	}

	int getPageNumber();
	
	void setPageNumber(int pageNumber);
	
	int getTotalNumberOfRows();
	
	void setTotalNumberOfRows(int total);
	
	int getTotalPages();
	
	List<T> getList();
	
	void setList(List<T> resultList);
	
	int getObjectsPerPage();
	
	void setObjectsPerPage(int objectsPerPage);
	
	String getSortCriterion();
	
	void setSortCriterion(String sortCriterion);
	
	SortOrder getFilterSortDirection();
	
	void setSortDirection(SortOrder sortOrderEnum);
	
	int getFirstRecordIndex();
	
}