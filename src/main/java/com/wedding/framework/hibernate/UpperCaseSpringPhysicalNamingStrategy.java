package com.wedding.framework.hibernate;

import org.hibernate.boot.model.naming.CamelCaseToUnderscoresNamingStrategy;
import org.hibernate.boot.model.naming.Identifier;
import org.hibernate.engine.jdbc.env.spi.JdbcEnvironment;

import java.util.Locale;

public class UpperCaseSpringPhysicalNamingStrategy extends CamelCaseToUnderscoresNamingStrategy {

	@Override
	protected Identifier getIdentifier(String name, boolean quoted, JdbcEnvironment jdbcEnvironment) {
		if (isCaseInsensitive(jdbcEnvironment)) {
			name = name.toUpperCase(Locale.ROOT);
		}

		return new Identifier(name, quoted);
	}	
}