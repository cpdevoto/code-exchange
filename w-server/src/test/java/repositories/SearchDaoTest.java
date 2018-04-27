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


import java.util.List;

import org.junit.Test;

import com.fasterxml.jackson.core.JsonProcessingException;

import static org.junit.Assert.assertThat;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;

import inputs.SearchInputs;

public class SearchDaoTest extends AbstractDaoTest {

  @Test
  public void test_search() throws JsonProcessingException {

    SearchInputs inputs = SearchInputs.builder()
        .withSearch("R&:")
        .withLimit("3")
        .build();

    List<String> results =
        repo.search().search(inputs);
    assertThat(results, notNullValue());
    assertThat(results.size(), equalTo(3));

    assertThat(results.get(0), equalTo("restaurant"));
    assertThat(results.get(1), equalTo("retailer"));
    assertThat(results.get(2), equalTo("Rojo Mexican Bistro"));

  }


}
