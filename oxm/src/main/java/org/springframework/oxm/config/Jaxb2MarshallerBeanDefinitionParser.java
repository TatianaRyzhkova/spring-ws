/*
 * Copyright 2007 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.oxm.config;

import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.ManagedList;
import org.springframework.beans.factory.xml.AbstractSingleBeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.util.StringUtils;
import org.springframework.util.xml.DomUtils;
import org.w3c.dom.Element;

/**
 * Parser for the <code>&lt;oxm:jaxb2-marshaller/&gt; element.
 *
 * @author Arjen Poutsma
 * @since 1.5.0
 */
class Jaxb2MarshallerBeanDefinitionParser extends AbstractSingleBeanDefinitionParser {

    public static final String JAXB2_MARSHALLER_CLASS_NAME = "org.springframework.oxm.jaxb.Jaxb2Marshaller";

    protected String getBeanClassName(Element element) {
        return JAXB2_MARSHALLER_CLASS_NAME;
    }

    protected void doParse(Element element, ParserContext parserContext, BeanDefinitionBuilder beanDefinitionBuilder) {
        String contextPath = element.getAttribute("contextPath");
        if (StringUtils.hasText(contextPath)) {
            beanDefinitionBuilder.addPropertyValue("contextPath", contextPath);
        }
        List classes = DomUtils.getChildElementsByTagName(element, "class-to-be-bound");
        if (!classes.isEmpty()) {
            ManagedList classesToBeBound = new ManagedList(classes.size());
            for (Iterator iterator = classes.iterator(); iterator.hasNext();) {
                Element classToBeBound = (Element) iterator.next();
                String className = classToBeBound.getAttribute("name");
                classesToBeBound.add(className);
            }
            beanDefinitionBuilder.addPropertyValue("classesToBeBound", classesToBeBound);
        }
    }

}