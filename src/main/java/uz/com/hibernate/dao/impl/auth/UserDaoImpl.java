package uz.com.hibernate.dao.impl.auth;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import uz.com.criteria.auth.UserCriteria;
import uz.com.enums.RoleType;
import uz.com.hibernate.base.DaoImpl;
import uz.com.hibernate.dao.auth.RoleDao;
import uz.com.hibernate.dao.auth.UserDao;
import uz.com.hibernate.domain.auth.User;
import uz.com.utils.UserSession;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

@Repository("userDao")
public class UserDaoImpl extends DaoImpl<User, UserCriteria> implements UserDao {

    /**
     * Common logger for use in subclasses.
     */
    protected final Log logger = LogFactory.getLog(getClass());
    @Autowired
    protected EntityManager entityManager;
    @Autowired
    protected RoleDao roleDao;
    @Autowired
    protected UserSession userSession;

    @Override
    public Stream<User> listStream(UserCriteria criteria) {
        CustomFilter customFilter = new CustomFilter(criteria).invoke();
        String filterQuery = customFilter.getFilterQuery();
        Map<String, Object> params = customFilter.getParams();

        return findIntervalStream("SELECT distinct (t) FROM User t left join t.organizations o left join t.roles r" +
                " WHERE t.state <> 2 " + filterQuery + onSortBy(criteria).toString(), params, criteria.getPage(), criteria.getPerPage());
    }

    @Override
    public List<User> list(UserCriteria criteria) {
        CustomFilter customFilter = new CustomFilter(criteria).invoke();
        String filterQuery = customFilter.getFilterQuery();
        Map<String, Object> params = customFilter.getParams();

        return findInterval("SELECT distinct (t) FROM User t left join t.organizations o left join t.roles r" +
                " WHERE t.state <> 2 " + filterQuery + onSortBy(criteria).toString(), params, criteria.getPage(), criteria.getPerPage());
    }

    @Override
    public Long total(UserCriteria criteria) {
        CustomFilter customFilter = new CustomFilter(criteria).invoke();
        String filterQuery = customFilter.getFilterQuery();
        Map<String, Object> params = customFilter.getParams();

        return (Long) findSingle("SELECT COUNT(distinct t) FROM User t left join t.organizations o left join t.roles r WHERE t.state <> 2 " + filterQuery, params);
    }

    @Override
    public User findByUsername(String username) {
        return (User) findSingle("select t from User t " +
                " where lower(t.username) = :username and t.state < 2 order by t.id asc ", preparing(new Entry("username", username.toLowerCase())));
    }

    @Override
    public User findByUserNameNotId(String username, Long id) {
        return (User) findSingle("select t from User t " +
                " where lower(t.username) = :username and t.id != :id and t.state < 2 order by t.id asc ", preparing(new Entry("username", username.toLowerCase()), new Entry("id", id)));
    }

    private class CustomFilter {
        private final UserCriteria criteria;
        private String filterQuery;
        private Map<String, Object> params;

        public CustomFilter(UserCriteria criteria) {
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

            if (!utils.isEmpty(criteria.getSelfId())) {
                filterQuery += " AND t.id = :selfId ";
                params.put("selfId", criteria.getSelfId());
            }
            if (!utils.isEmpty(criteria.getFullName())) {
                filterQuery += " AND (t.firstName LIKE :fullName OR t.lastName LIKE :fullName OR t.middleName LIKE :fullName) ";
                params.put("fullName", "%" + criteria.getFullName() + "%");
            }
            if (!utils.isEmpty(criteria.getUsername())) {
                filterQuery += " AND t.username = :username ";
                params.put("username", criteria.getUsername());
            }
            if (!utils.isEmpty(criteria.getEmail())) {
                filterQuery += " AND t.email = :email ";
                params.put("email", criteria.getEmail());
            }
            if (!utils.isEmpty(criteria.getPhone())) {
                filterQuery += " AND t.phone = :phone ";
                params.put("phone", criteria.getPhone());
            }
            if (!utils.isEmpty(criteria.getOrganizationId())) {
                filterQuery += " AND o.id = :organizationId ";
                params.put("organizationId", criteria.getOrganizationId());
            }
//            if (!utils.isEmpty(criteria.isMyUsers()) && criteria.isMyUsers()) {
//                filterQuery += " AND o.id in :ids ";
//                params.put("ids", userSession.getOrganizationIds());
//            }
            if (!utils.isEmpty(criteria.getIsFinancier())) {
                filterQuery += " AND r in :role ";
                params.put("role", roleDao.findByCode(RoleType.FINANCE_ADMIN.name()));
            }
            if (!utils.isEmpty(criteria.getSupplyDepartmentTypeId())) {
                filterQuery += " AND t.supplyDepartmentType.id = :supplyDepartmentTypeId ";
                params.put("supplyDepartmentTypeId", criteria.getSupplyDepartmentTypeId());
            }

            return this;
        }
    }
}
