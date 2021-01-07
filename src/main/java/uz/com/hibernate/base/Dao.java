package uz.com.hibernate.base;

import org.hibernate.Session;
import uz.com.criteria.GenericCriteria;

import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public interface Dao<T extends _Entity, C extends GenericCriteria> {

    T save(T entity);

    T get(Long id);

    T find(C criteria);

    PageStream<T> search(C criteria);

    Stream<T> listStream(C criteria);

    List<T> list(C criteria);

    Long total(C criteria);

    Stream findStream(String query, Map<String, ?> params);

    List find(String query, Map<String, ?> params);

    Stream findIntervalStream(String query, Map<String, ?> params, Integer page, Integer perPage);

    List findInterval(String query, Map<String, ?> params, Integer page, Integer perPage);

    Stream findNativeIntervalStream(String query, Map<String, ?> params, Integer page, Integer perPage);

    List findNativeInterval(String query, Map<String, ?> params, Integer page, Integer perPage);

    Object findSingle(String query, Map<String, ?> params);

    Object findSingleNative(String query, Map<String, ?> params);

    void delete(T entity);

    void delete(Long id);

    Session getSession();
}
