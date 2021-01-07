package uz.com.hibernate.dao.impl.settings;


import org.springframework.stereotype.Repository;
import uz.com.criteria.settings.ActivityCriteria;
import uz.com.hibernate.base.DaoImpl;
import uz.com.hibernate.dao.settings.ActivityDao;
import uz.com.hibernate.domain.activity.Activity;

import java.util.Map;
import java.util.stream.Stream;

@Repository("activeDao")
public class ActivityDaoImpl extends DaoImpl<Activity, ActivityCriteria> implements ActivityDao {

    @Override
    public Stream<Activity> listStream(ActivityCriteria criteria) {
        CustomFilter customFilter = new CustomFilter(criteria).invoke();
        String filterQuery = customFilter.getFilterQuery();
        Map<String, Object> params = customFilter.getParams();

        return findIntervalStream("SELECT t FROM Activity t " +
                " WHERE t.state <> 2 " + filterQuery + onSortBy(criteria).toString(), params, criteria.getPage(), criteria.getPerPage());
    }

    @Override
    public Long total(ActivityCriteria criteria) {
        CustomFilter customFilter = new CustomFilter(criteria).invoke();
        String filterQuery = customFilter.getFilterQuery();
        Map<String, Object> params = customFilter.getParams();

        return (Long) findSingle("SELECT COUNT(t) FROM Activity t WHERE t.state <> 2 " + filterQuery, params);
    }

    private class CustomFilter {
        private final ActivityCriteria criteria;
        private String filterQuery;
        private Map<String, Object> params;

        public CustomFilter(ActivityCriteria criteria) {
            this.criteria = criteria;
        }

        public String getFilterQuery() {
            return filterQuery;
        }

        public Map<String, Object> getParams() {
            return params;
        }

        public CustomFilter invoke() {
            filterQuery = "";
            params = preparing();

            if (!utils.isEmpty(criteria.getUserId())) {
                filterQuery += " AND t.auditInfo.createdBy = :userId ";
                params.put("userId", criteria.getUserId());
            }

            return this;
        }
    }

}
