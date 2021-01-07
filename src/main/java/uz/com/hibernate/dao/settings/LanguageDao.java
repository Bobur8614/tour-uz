package uz.com.hibernate.dao.settings;

import uz.com.criteria.settings.LanguageCriteria;
import uz.com.hibernate.base.Dao;
import uz.com.hibernate.domain.settings.Language;

public interface LanguageDao extends Dao<Language, LanguageCriteria> {

    Language findByCode(String code);

}
