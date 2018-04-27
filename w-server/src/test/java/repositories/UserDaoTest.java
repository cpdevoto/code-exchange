/**
 * Copyright (C) 2012-2018 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

/**
 * Copyright (C) 2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

package repositories;


import static org.hamcrest.Matchers.greaterThanOrEqualTo;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.junit.Test;

import static org.junit.Assert.assertThat;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;

import models.Merchant;
import models.User;

public class UserDaoTest extends AbstractDaoTest {

  @Test
  public void test_fetch_users_and_fetch_user_by_id() {
    List<User> users = repo.users().fetch();

    assertThat(users, notNullValue());
    assertThat(users.size(), greaterThanOrEqualTo(1));
    Map<String, User> userMap =
        users.stream().collect(Collectors.toMap(User::getEmail, Function.identity()));
    User jblow = userMap.get("jblow@gmail.com");
    assertUser(jblow);

    Optional<User> user = repo.users().fetch(jblow.getId());
    assertThat(user.isPresent(), equalTo(true));
    assertUser(user.get());
  }

  @Test
  public void test_fetch_user_by_subject() {
    Optional<User> user = repo.users().fetch("d5440f24-b740-4d70-addf-cf9141fb8223");
    assertThat(user.isPresent(), equalTo(true));
    assertUser(user.get());

  }

  @Test
  public void test_insert_update_delete_user() {
    User user = new User();
    user.setAdmin(false);
    user.setSubject("e5440f24-b740-4d70-addf-cf9141fb8223");
    user.setEmail("jdoe@gmail.com");
    user.setPreferredUsername("jdoe");
    user.setName("Jane Doe");
    user.setGivenName("Jane");
    user.setFamilyName("Doe");
    repo.users().save(user);

    Optional<User> fetched = repo.users().fetch("e5440f24-b740-4d70-addf-cf9141fb8223");
    assertThat(fetched.isPresent(), equalTo(true));
    assertThat(fetched.get().getId(), notNullValue());
    user.setId(fetched.get().getId());
    assertThat(fetched.get(), equalTo(user));

    user.setName("Jane J. Doe");
    repo.users().save(user);
    fetched = repo.users().fetch("e5440f24-b740-4d70-addf-cf9141fb8223");
    assertThat(fetched.isPresent(), equalTo(true));
    assertThat(fetched.get(), equalTo(user));

    repo.users().delete(user);

    fetched = repo.users().fetch("e5440f24-b740-4d70-addf-cf9141fb8223");
    assertThat(fetched.isPresent(), equalTo(false));

  }

  @Test
  public void test_fetch_user_favorites() {
    Optional<User> user = repo.users().fetch("d5440f24-b740-4d70-addf-cf9141fb8223");
    assertThat(user.isPresent(), equalTo(true));
    assertUser(user.get());

    List<Merchant> favorites = user.get().getUserFavoriteMerchants();
    assertThat(favorites, notNullValue());
    assertThat(favorites.size(), equalTo(2));

    Set<Long> favoriteIds =
        favorites.stream().map(Merchant::getId).collect(Collectors.toSet());
    assertThat(favoriteIds.contains(51335L), equalTo(true));
    assertThat(favoriteIds.contains(51336L), equalTo(true));


  }

  private void assertUser(User user) {
    assertThat(user, notNullValue());
    assertThat(user.getId(), greaterThanOrEqualTo(1L));
    assertThat(user.getAdmin(), equalTo(false));
    assertThat(user.getSubject(), equalTo("d5440f24-b740-4d70-addf-cf9141fb8223"));
    assertThat(user.getEmail(), equalTo("jblow@gmail.com"));
    assertThat(user.getPreferredUsername(), equalTo("jblow"));
    assertThat(user.getName(), equalTo("Joe Blow"));
    assertThat(user.getGivenName(), equalTo("Joe"));
    assertThat(user.getFamilyName(), equalTo("Blow"));
  }

}
