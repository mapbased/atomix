/*
 * Copyright 2014 the original author or authors.
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
package net.kuujo.copycat.collections;

import com.typesafe.config.Config;
import net.kuujo.copycat.internal.util.Configs;

import java.util.Map;

/**
 * Asynchronous set configuration.
 *
 * @author <a href="http://github.com/kuujo">Jordan Halterman</a>
 */
public class AsyncSetConfig extends AsyncCollectionConfig {

  public AsyncSetConfig() {
    super(Configs.load("copycat.set", "copycat.collection").toConfig());
  }

  public AsyncSetConfig(String resource) {
    super(Configs.load(resource, "copycat.set", "copycat.collection").toConfig());
  }

  public AsyncSetConfig(Map<String, Object> config) {
    super(Configs.load(config, "copycat.set", "copycat.collection").toConfig());
  }

  public AsyncSetConfig(Config config) {
    super(config);
  }

}
