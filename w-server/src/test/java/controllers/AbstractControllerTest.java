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

package controllers;


import java.util.Map;

import org.junit.ClassRule;
import org.junit.rules.RuleChain;

import com.google.common.collect.ImmutableMap;

import ninja.NinjaDocTester;
import testsuite.WantifyServerTestSuite;
import utils.auth.AuthHeaders;

public abstract class AbstractControllerTest extends NinjaDocTester {

  @ClassRule
  public static final RuleChain CHAIN = WantifyServerTestSuite.CHAIN;

  protected static final Map<String, String> PROTECTED_RESOURCE_HEADERS = ImmutableMap.of(
      "Cookie", "access-token=" + AuthHeaders.TEST_TOKEN);

  protected static final Map<String, String> PROTECTED_API_HEADERS = ImmutableMap.of(
      "Authorization", AuthHeaders.TEST_HEADER);

}
