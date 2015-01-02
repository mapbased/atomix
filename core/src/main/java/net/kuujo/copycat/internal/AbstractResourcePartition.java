/*
 * Copyright 2014 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.kuujo.copycat.internal;

import net.kuujo.copycat.CopycatState;
import net.kuujo.copycat.ResourcePartition;
import net.kuujo.copycat.ResourcePartitionContext;
import net.kuujo.copycat.cluster.Cluster;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Copycat context.
 *
 * @author <a href="http://github.com/kuujo">Jordan Halterman</a>
 */
public abstract class AbstractResourcePartition<T extends ResourcePartition<T>> extends AbstractManagedResource<T> implements ResourcePartition<T> {
  protected ResourcePartitionContext context;
  private AtomicBoolean open = new AtomicBoolean();

  protected AbstractResourcePartition(ResourcePartitionContext context) {
    this.context = context;
  }

  @Override
  public String name() {
    return context.name();
  }

  @Override
  public int partition() {
    return context.config().getPartition();
  }

  @Override
  public Cluster cluster() {
    return context.cluster();
  }

  @Override
  public CopycatState state() {
    return context.state();
  }

  @Override
  @SuppressWarnings("all")
  public CompletableFuture<T> open() {
    if (open.compareAndSet(false, true)) {
      return super.open().thenComposeAsync(v -> context.open(), context).thenApply(v -> (T) this);
    }
    return CompletableFuture.completedFuture((T) this);
  }

  @Override
  public synchronized boolean isOpen() {
    return open.get();
  }

  @Override
  public CompletableFuture<Void> close() {
    if (open.compareAndSet(true, false)) {
      return context.close().thenCompose(v -> super.close());
    }
    return CompletableFuture.completedFuture(null);
  }

  @Override
  public synchronized boolean isClosed() {
    return !open.get();
  }

}