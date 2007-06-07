/*
 * Copyright [2007] [University Corporation for Advanced Internet Development, Inc.]
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.opensaml.xml.security;

import org.opensaml.xml.util.ClassIndexedSet;

/**
 * This class holds instances of {@link ResolverCriteria} which are used by
 * a {@link Resolver} to resolve information of an implementation-specific type.
 */
public class ResolverCriteriaSet extends ClassIndexedSet<ResolverCriteria> {
    
    /**
     * Constructor.
     *
     */
    public ResolverCriteriaSet() {
        super();
    }
    
    /**
     * A convenience constructor for constructing and adding a single criteria.
     *
     * @param criteria a single criteria 
     */
    public ResolverCriteriaSet(ResolverCriteria criteria) {
        super();
        add(criteria);
    }

}
