/**
 * Copyright 2025 PICONET
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package kr.co.piconet.ai.sample.config;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.boot.Metadata;
import org.hibernate.boot.model.relational.ColumnOrderingStrategyLegacy;
import org.hibernate.cfg.AvailableSettings;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.type.filter.AnnotationTypeFilter;

import jakarta.persistence.Entity;

@Configuration
public class JpaColumnOrderingStrategy extends ColumnOrderingStrategyLegacy implements HibernatePropertiesCustomizer
{
	private static String pkg;
	static {
		String p = JpaColumnOrderingStrategy.class.getPackageName();
		int index = p.lastIndexOf('.');
		pkg = p.substring(0, index);
	}

	@Override
	public List<org.hibernate.mapping.Column> orderTableColumns(org.hibernate.mapping.Table table, Metadata metadata)
	{
		List<org.hibernate.mapping.Column> columns = new ArrayList<>();

		Map<String,org.hibernate.mapping.Column> columnMap = new HashMap<>();
		table.getColumns().forEach((column) -> columnMap.put(column.getName(), column));

		List<String> names = getEntityColumns(table.getName());
		for(String name:  names) {
			if(columnMap.containsKey(name)) {
				columns.add(columnMap.get(name));
			}
		}
		return columns;
	}
	@Override
	public void customize(Map<String, Object> hibernateProperties) {
		hibernateProperties.put(AvailableSettings.COLUMN_ORDERING_STRATEGY, this);
	}

	private static List<String> getEntityColumns(String tableName)
	{
		List<String> columns = new ArrayList<>();
		ClassPathScanningCandidateComponentProvider provider =
				  new ClassPathScanningCandidateComponentProvider(false);
		provider.addIncludeFilter(new AnnotationTypeFilter(Entity.class));
		for (BeanDefinition beanDefinition : provider.findCandidateComponents(pkg)) {
			try {
				Class<?> entityClass = Class.forName(beanDefinition.getBeanClassName());
				jakarta.persistence.Table tableClass = entityClass.getAnnotation(jakarta.persistence.Table.class);
				if(tableClass.name().equals(tableName)) {
					Map<String,String> parentColumns = new LinkedHashMap<>();
					for(Field field:  entityClass.getSuperclass().getDeclaredFields()) {
						String name = field.getName();
						jakarta.persistence.Column column = field.getAnnotation(jakarta.persistence.Column.class);
						if(column != null && column.name().length() > 0) {
							name = column.name();
						}
						if(name.equals("id")) {
							columns.add(name);
						} else {
							parentColumns.put(name, name);
						}						
					}
					for(Field field:  entityClass.getDeclaredFields()) {
						String name = field.getName();
						jakarta.persistence.Column column = field.getAnnotation(jakarta.persistence.Column.class);
						if(column != null && column.name().length() > 0) {
							name = column.name();
						}
						columns.add(name);
					}
					columns.addAll(parentColumns.values());
				}
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
		return columns;
	}
}