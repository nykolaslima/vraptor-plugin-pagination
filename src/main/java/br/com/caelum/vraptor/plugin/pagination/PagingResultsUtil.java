package br.com.caelum.vraptor.plugin.pagination;

import java.util.List;

import org.displaytag.properties.SortOrderEnum;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;

public class PagingResultsUtil {

	@SuppressWarnings("unchecked")
	public static <T> List<T> listPagingResults(Criteria criteria, PagingResults<T> pagingResults) {
		if(pagingResults == null) {
			throw new IllegalStateException("Invalid null PagingResults");
		}
		
		if(pagingResults.getSortCriterion() != null) {
			if(pagingResults.getFilterSortDirection().equals(SortOrderEnum.ASCENDING)) {
				criteria.addOrder(Order.asc(pagingResults.getSortCriterion()));
			} else {
				criteria.addOrder(Order.desc(pagingResults.getSortCriterion()));
			}
		}
		
		return criteria.setFirstResult(pagingResults.getFirstRecordIndex()).setMaxResults(pagingResults.getObjectsPerPage()).list();
	}
}
