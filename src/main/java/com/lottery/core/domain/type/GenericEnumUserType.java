package com.lottery.core.domain.type;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Properties;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.usertype.ParameterizedType;
import org.hibernate.usertype.UserType;

public class GenericEnumUserType implements UserType, ParameterizedType{
	 private static final String DEFAULT_IDENTIFIER_METHOD_NAME = "name";

	    private static final String DEFAULT_VALUE_OF_METHOD_NAME = "value";
	    @SuppressWarnings("rawtypes")
		private Class<? extends Enum> enumClass;

	    private Class<?> identifierType;

	    private Method identifierMethod;

	    private Method valueOfMethod;

	  

	    private int[] sqlTypes=new int[]{Types.INTEGER};;

	 
        @Override
	    public void setParameterValues(Properties parameters) {

	        String enumClassName = parameters.getProperty("enumClass");

	        try {

	            enumClass = Class.forName(enumClassName).asSubclass(Enum.class);

	        } catch (ClassNotFoundException cfne) {

	            throw new HibernateException("Enum class not found", cfne);

	        }

	 

	        String identifierMethodName = parameters.getProperty("identifierMethod", DEFAULT_IDENTIFIER_METHOD_NAME);

	 

	        try {

	            identifierMethod = enumClass.getMethod(identifierMethodName, new Class[0]);

	            identifierType = identifierMethod.getReturnType();

	        } catch (Exception e) {

	            throw new HibernateException("Failed to obtain identifier method", e);

	        }

	 

	      
	 

	        String valueOfMethodName = parameters.getProperty("valueOfMethod", DEFAULT_VALUE_OF_METHOD_NAME);

	 

	        try {

	            valueOfMethod = enumClass.getMethod(valueOfMethodName, new Class[] { identifierType });

	        } catch (Exception e) {

	            throw new HibernateException("Failed to obtain valueOf method", e);

	        }

	    }

	 

	    @SuppressWarnings("rawtypes")
		public Class returnedClass() {
	        return enumClass;

	    }
	    public int[] sqlTypes() {

	        return sqlTypes;

	    }

	 

	    public Object assemble(Serializable cached, Object owner) throws HibernateException {

	        return cached;

	    }

	 

	    public Object deepCopy(Object value) throws HibernateException {

	        return value;

	    }

	 

	    public Serializable disassemble(Object value) throws HibernateException {

	        return (Serializable) value;

	    }

	 

	    public boolean equals(Object x, Object y) throws HibernateException {

	        return x == y;

	    }

	 

	    public int hashCode(Object x) throws HibernateException {

	        return x.hashCode();

	    }

	 

	    public boolean isMutable() {

	        return false;

	    }

	 

	    public Object replace(Object original, Object target, Object owner) throws HibernateException {

	        return original;

	    }



		@Override
		public Object nullSafeGet(ResultSet rs, String[] names,
				SessionImplementor session, Object owner)
				throws HibernateException, SQLException {
			
			return nullSafeGet(rs, names, owner);
		}



		@Override
		public void nullSafeSet(PreparedStatement st, Object value, int index,
				SessionImplementor session) throws HibernateException,
				SQLException {
			nullSafeSet(st, value, index);
			
		}
		public Object nullSafeGet(ResultSet rs, String[] names, Object owner) throws HibernateException, SQLException {  

	       
	        Object identifier = rs.getInt(names[0]);
	        if (identifier == null) {

	            return null;

	        }

	         

	        try {

	            return valueOfMethod.invoke(enumClass, new Object[] { identifier });

	        } catch (Exception e) {

	            throw new HibernateException("Exception while invoking valueOf method '" + valueOfMethod.getName() + "' of " +

	                    "enumeration class '" + enumClass + "'", e);

	        }

	    }

	 

	    public void nullSafeSet(PreparedStatement st, Object value, int index) throws HibernateException, SQLException {

	        try {

	            if (value == null) {

	                st.setNull(index, Types.INTEGER);

	            } else {

	                Object identifier = identifierMethod.invoke(value, new Object[0]);
	                st.setObject(index, identifier);

	            }

	        } catch (Exception e) {

	            throw new HibernateException("Exception while invoking identifierMethod '" + identifierMethod.getName() + "' of " +

	                    "enumeration class '" + enumClass + "'", e);

	        }

	    }


}
