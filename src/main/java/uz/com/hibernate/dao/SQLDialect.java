package uz.com.hibernate.dao;

import org.hibernate.dialect.function.StandardSQLFunction;
import org.hibernate.type.BooleanType;
import org.hibernate.type.IntegerType;
import org.hibernate.type.StringType;
import org.hibernate.type.TextType;

public class SQLDialect extends org.hibernate.dialect.PostgreSQLDialect {

    public SQLDialect() {
        super();
        registerFunction("getuseridsbyapplication", new StandardSQLFunction("getuseridsbyapplication", new StringType()));


    }
}
