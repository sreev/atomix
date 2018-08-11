/*
 * Copyright 2015-present Open Networking Foundation
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
package io.atomix.protocols.raft.protocol;

import io.atomix.cluster.MemberId;

import java.util.Objects;

import static com.google.common.base.MoreObjects.toStringHelper;
import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Server vote request.
 * <p>
 * Vote requests are sent by candidate servers during an election to determine whether they should become the leader for
 * a cluster. Vote requests contain the necessary information for followers to determine whether a candidate should
 * receive their vote based on log and other information.
 */
public class VoteRequest extends AbstractRaftRequest {

  public static VoteRequest request(long term, String candidate, long lastLogIndex, long lastLogTerm) {
    return new VoteRequest(term, candidate, lastLogIndex, lastLogTerm);
  }

  private final long term;
  private final String candidate;
  private final long lastLogIndex;
  private final long lastLogTerm;

  private VoteRequest(long term, String candidate, long lastLogIndex, long lastLogTerm) {
    checkArgument(term >= 0, "term must be positive");
    checkNotNull(candidate, "candidate cannot be null");
    checkArgument(lastLogIndex >= 0, "lastLogIndex must be positive");
    checkArgument(lastLogTerm >= 0, "lastLogTerm must be positive");
    this.term = term;
    this.candidate = candidate;
    this.lastLogIndex = lastLogIndex;
    this.lastLogTerm = lastLogTerm;
  }

  /**
   * Returns the requesting node's current term.
   *
   * @return The requesting node's current term.
   */
  public long term() {
    return term;
  }

  /**
   * Returns the candidate's address.
   *
   * @return The candidate's address.
   */
  public MemberId candidate() {
    return MemberId.from(candidate);
  }

  /**
   * Returns the candidate's last log index.
   *
   * @return The candidate's last log index.
   */
  public long lastLogIndex() {
    return lastLogIndex;
  }

  /**
   * Returns the candidate's last log term.
   *
   * @return The candidate's last log term.
   */
  public long lastLogTerm() {
    return lastLogTerm;
  }

  @Override
  public int hashCode() {
    return Objects.hash(getClass(), term, candidate, lastLogIndex, lastLogTerm);
  }

  @Override
  public boolean equals(Object object) {
    if (object instanceof VoteRequest) {
      VoteRequest request = (VoteRequest) object;
      return request.term == term
          && request.candidate == candidate
          && request.lastLogIndex == lastLogIndex
          && request.lastLogTerm == lastLogTerm;
    }
    return false;
  }

  @Override
  public String toString() {
    return toStringHelper(this)
        .add("term", term)
        .add("candidate", candidate)
        .add("lastLogIndex", lastLogIndex)
        .add("lastLogTerm", lastLogTerm)
        .toString();
  }
}
