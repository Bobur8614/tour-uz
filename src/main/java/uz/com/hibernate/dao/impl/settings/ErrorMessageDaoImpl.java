package uz.com.hibernate.dao.impl.settings;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import uz.com.criteria.settings.ErrorMessageCriteria;
import uz.com.enums.ErrorCodes;
import uz.com.hibernate.base.DaoImpl;
import uz.com.hibernate.dao.settings.ErrorMessageDao;
import uz.com.hibernate.dao.settings.LanguageDao;
import uz.com.hibernate.domain.settings.ErrorMessage;
import uz.com.utils.UserSession;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.util.Map;
import java.util.stream.Stream;

@Repository("errorMessageDao")
public class ErrorMessageDaoImpl extends DaoImpl<ErrorMessage, ErrorMessageCriteria> implements ErrorMessageDao {

    protected final Log logger = LogFactory.getLog(getClass());
    @Autowired
    protected EntityManager entityManager;
    @Autowired
    private UserSession userSession;
    @Autowired
    private LanguageDao languageDao;

    @Override
    public Stream<ErrorMessage> listStream(ErrorMessageCriteria criteria) {
        CustomFilter customFilter = new CustomFilter(criteria).invoke();
        String filterQuery = customFilter.getFilterQuery();
        Map<String, Object> params = customFilter.getParams();

        return findIntervalStream("SELECT t FROM ErrorMessage t " +
                " WHERE t.state <> 2 " + filterQuery + onSortBy(criteria).toString(), params, criteria.getPage(), criteria.getPerPage());
    }

    @Override
    public Long total(ErrorMessageCriteria criteria) {
        CustomFilter customFilter = new CustomFilter(criteria).invoke();
        String filterQuery = customFilter.getFilterQuery();
        Map<String, Object> params = customFilter.getParams();

        return (Long) findSingle("SELECT COUNT(t) FROM ErrorMessage t WHERE t.state <> 2 " + filterQuery, params);
    }

    @Override
    public String getErrorMessage(ErrorCodes errorCode, String params) {
        try {
            Long lang = null;
            if (userSession.getUser() == null && languageDao.findByCode("ru") != null) {
                lang = languageDao.findByCode("ru").getId();
            } else if (userSession.getUser().getLanguage() == null && languageDao.findByCode("en") != null) {
                lang = languageDao.findByCode("en").getId();
            } else if (userSession.getUser().getLanguage() != null && languageDao.findByCode(userSession.getUser().getLanguage().getCode()) != null) {
                lang = userSession.getUser().getLanguage().getId();
            }
            String singleResult = (String) entityManager.createQuery("SELECT emt.name FROM ErrorMessage t INNER JOIN ErrorMessageTranslation emt on t.id = emt.messageId WHERE t.errorCode = '" + errorCode + "' and emt.language.id = '" + lang + "' ORDER BY t.id DESC ").getSingleResult();
            if (!utils.isEmpty(singleResult)) {
                singleResult = String.format(singleResult, params);
            }
            return singleResult;
        } catch (NoResultException ex) {
            logger.error(ex.getMessage());
            throw new RuntimeException(String.format("ErrorMessage with errorCode '%s' not found", errorCode));
        }
    }

    private class CustomFilter {
        private final ErrorMessageCriteria criteria;
        private String filterQuery;
        private Map<String, Object> params;

        public CustomFilter(ErrorMessageCriteria criteria) {
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

            return this;
        }
    }

}
