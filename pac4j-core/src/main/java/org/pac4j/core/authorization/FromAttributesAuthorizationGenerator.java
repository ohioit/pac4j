/*
  Copyright 2012 - 2015 pac4j organization

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
 */
package org.pac4j.core.authorization;

import java.util.Arrays;
import java.util.Collection;
import java.util.StringTokenizer;

import org.pac4j.core.profile.CommonProfile;

/**
 * <p>Generate the authorization information by inspecting attributes.</p>
 * <p>The attributes containing the roles separated by the {@link #splitChar} property (can be set through {@link #setSplitChar(String)}) are
 * defined in the constructor. It's the same for the attributes containing the permissions.</p>
 * 
 * @author Jerome Leleu
 * @since 1.5.0
 */
public class FromAttributesAuthorizationGenerator<U extends CommonProfile> implements AuthorizationGenerator<U> {

    private final String[] roleAttributes;

    private final String[] permissionAttributes;

    private String splitChar = ",";

    public FromAttributesAuthorizationGenerator(final String[] roleAttributes, final String[] permissionAttributes) {
        this.roleAttributes = roleAttributes;
        this.permissionAttributes = permissionAttributes;
    }

    public void generate(final U profile) {
        generateAuth(profile, this.roleAttributes, true);
        generateAuth(profile, this.permissionAttributes, false);
    }

    private void generateAuth(final U profile, final String[] attributes, final boolean isRole) {
        if (attributes != null) {
            for (final String attribute : attributes) {
                final Object value = profile.getAttribute(attribute);
                if (value != null) {
                    if(value instanceof String) {
                        final StringTokenizer st = new StringTokenizer((String) value, this.splitChar);
                        while (st.hasMoreTokens()) {
                            setAuth(profile, st.nextToken(), isRole);
                        }
                    } else if(value.getClass().isArray() && value.getClass().getComponentType().isAssignableFrom(String.class)) {
                        for(Object item : (Object[])value) {
                            setAuth(profile, item.toString(), isRole);
                        }
                    } else if(Collection.class.isAssignableFrom(value.getClass())) {
                        for(Object item : (Collection<?>)value) {
                            if(item.getClass().isAssignableFrom(String.class)) {
                                setAuth(profile, item.toString(), isRole);
                            }
                        }
                    }
                }
            }
        }
    }

    private void setAuth(final U profile, final String value, final boolean isRole) {
        if (isRole) {
            profile.addRole(value);
        } else {
            profile.addPermission(value);
        }
    }

    public String getSplitChar() {
        return this.splitChar;
    }

    public void setSplitChar(final String splitChar) {
        this.splitChar = splitChar;
    }
}
