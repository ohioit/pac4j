package org.pac4j.core.authorization.authorizer;

import org.pac4j.core.context.WebContext;
import org.pac4j.core.exception.RequiresHttpAction;
import org.pac4j.core.profile.UserProfile;

import java.util.List;
import java.util.Set;

/**
 * Checks an access if the user profile has all the roles.
 *
 * @param <U> the user profile
 * @author Jerome Leleu
 * @since 1.8.0
 */
public class RequireAllRolesAuthorizer<U extends UserProfile> extends AbstractRequireAllAuthorizer<String, U> {

    public RequireAllRolesAuthorizer() { }

    public RequireAllRolesAuthorizer(final String... roles) {
        setElements(roles);
    }

    public RequireAllRolesAuthorizer(final List<String> roles) {
        setElements(roles);
    }

    public RequireAllRolesAuthorizer(final Set<String> roles) {
        setElements(roles);
    }

    @Override
    protected boolean check(final WebContext context, final U profile, final String element) throws RequiresHttpAction {
        final Set<String> profileRoles = profile.getRoles();
        return profileRoles.contains(element);
    }
}
