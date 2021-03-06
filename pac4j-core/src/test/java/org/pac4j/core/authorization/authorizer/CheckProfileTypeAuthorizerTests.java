package org.pac4j.core.authorization.authorizer;

import org.junit.Test;
import org.pac4j.core.exception.RequiresHttpAction;
import org.pac4j.core.profile.CommonProfile;
import org.pac4j.core.profile.UserProfile;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Tests {@link CheckProfileTypeAuthorizer}.
 *
 * @author Jerome Leleu
 * @since 1.8.1
 */
public final class CheckProfileTypeAuthorizerTests {

    class FakeProfile1 extends CommonProfile {
    }
    class FakeProfile2 extends CommonProfile {
    }

    @Test
    public void testGoodProfile() throws RequiresHttpAction {
        final CheckProfileTypeAuthorizer authorizer = new CheckProfileTypeAuthorizer(FakeProfile1.class, FakeProfile2.class);
        final List<UserProfile> profiles = new ArrayList<>();
        profiles.add(new FakeProfile1());
        assertTrue(authorizer.isAuthorized(null, profiles));
    }

    @Test
    public void testBadProfileType() throws RequiresHttpAction {
        final CheckProfileTypeAuthorizer authorizer = new CheckProfileTypeAuthorizer(FakeProfile1.class);
        final List<UserProfile> profiles = new ArrayList<>();
        profiles.add(new FakeProfile2());
        assertFalse(authorizer.isAuthorized(null, profiles));
    }
}
