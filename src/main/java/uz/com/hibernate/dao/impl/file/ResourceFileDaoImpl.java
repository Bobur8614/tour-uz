package uz.com.hibernate.dao.impl.file;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import uz.com.criteria.file.ResourceFileCriteria;
import uz.com.hibernate.base.DaoImpl;
import uz.com.hibernate.dao.file.ResourceFileDao;
import uz.com.hibernate.domain.files.ResourceFile;

import javax.persistence.EntityManager;
import java.util.Map;
import java.util.stream.Stream;

@Repository("resourceFileDao")
public class ResourceFileDaoImpl extends DaoImpl<ResourceFile, ResourceFileCriteria> implements ResourceFileDao {

    protected final Log logger = LogFactory.getLog(getClass());

    @Autowired
    protected EntityManager entityManager;

    @Override
    public Stream<ResourceFile> listStream(ResourceFileCriteria criteria) {
        CustomFilter customFilter = new CustomFilter(criteria).invoke();
        String filterQuery = customFilter.getFilterQuery();
        Map<String, Object> params = customFilter.getParams();

        return findIntervalStream("SELECT t FROM ResourceFile t " +
                " WHERE t.state <> 2 " + filterQuery + onSortBy(criteria).toString(), params, criteria.getPage(), criteria.getPerPage());
    }

    @Override
    public Long total(ResourceFileCriteria criteria) {
        CustomFilter customFilter = new CustomFilter(criteria).invoke();
        String filterQuery = customFilter.getFilterQuery();
        Map<String, Object> params = customFilter.getParams();

        return (Long) findSingle("SELECT COUNT(t) FROM ResourceFile t WHERE t.state <> 2 " + filterQuery, params);
    }



    private class CustomFilter {
        private final ResourceFileCriteria criteria;
        private String filterQuery;
        private Map<String, Object> params;

        public CustomFilter(ResourceFileCriteria criteria) {
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
            if (!utils.isEmpty(criteria.getMimeType())) {
                filterQuery += " AND t.mimeType = :mimeType ";
                params.put("mimeType", criteria.getMimeType());
            }
            if (!utils.isEmpty(criteria.getSize())) {
                filterQuery += " AND t.size = :size ";
                params.put("size", criteria.getSize());
            }
            if (!utils.isEmpty(criteria.getName())) {
                filterQuery += " AND t.name = :name ";
                params.put("name", criteria.getName());
            }
            if (!utils.isEmpty(criteria.getObjectId())) {
                filterQuery += " AND t.objectId = :objectId";
                params.put("objectId", criteria.getObjectId());
            }

            return this;
        }
    }

}
