package uz.com.hibernate.dao.impl.auth;

import org.springframework.stereotype.Repository;
import uz.com.criteria.auth.UserContactCriteria;
import uz.com.hibernate.base.DaoImpl;
import uz.com.hibernate.dao.auth.UserContactDao;
import uz.com.hibernate.domain.auth.UserContact;

@Repository("userContactDao")
public class UserContactDaoImpl extends DaoImpl<UserContact, UserContactCriteria> implements UserContactDao {

}
