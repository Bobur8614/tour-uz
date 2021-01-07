package uz.com.hibernate.dao.impl.settings;

import org.springframework.stereotype.Repository;
import uz.com.criteria.settings.LanguageCriteria;
import uz.com.hibernate.base.DaoImpl;
import uz.com.hibernate.dao.settings.LanguageDao;
import uz.com.hibernate.domain.settings.Language;

@Repository("languageDao")
public class LanguageDaoImpl extends DaoImpl<Language, LanguageCriteria> implements LanguageDao {
    @Override
    public Language findByCode(String code) {
        return (Language) findSingle("select t from Language t " +
                " where lower(t.code) = :code and t.state < 2 order by t.id asc ", preparing(new Entry("code", code.toLowerCase())));
    }
}
