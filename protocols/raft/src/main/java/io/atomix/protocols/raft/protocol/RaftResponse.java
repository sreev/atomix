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

import io.atomix.protocols.raft.RaftError;

/**
 * Base interface for responses.
 * <p>
 * Each response has a non-null {@link RaftResponse.Status} of either {@link RaftResponse.Status#OK} or {@link
 * RaftResponse.Status#ERROR}. Responses where {@link #status()} is {@link RaftResponse.Status#ERROR} may provide an
 * optional {@link #error()} code.
 */
public interface RaftResponse extends RaftMessage {

  /**
   * Response status.
   */
  enum Status {

    /**
     * Indicates a successful response status.
     */
    OK(1),

    /**
     * Indicates a response containing an error.
     */
    ERROR(0);

    /**
     * Returns the status for the given identifier.
     *
     * @param id The status identifier.
     * @return The status for the given identifier.
     * @throws IllegalArgumentException if {@code id} is not 0 or 1
     */
    public static Status forId(int id) {
      switch (id) {
        case 1:
          return OK;
        case 0:
          return ERROR;
        default:
          break;
      }
      throw new IllegalArgumentException("invalid status identifier: " + id);
    }

    private final byte id;

    Status(int id) {
      this.id = (byte) id;
    }

    /**
     * Returns the status identifier.
     *
     * @return The status identifier.
     */
    public byte id() {
      return id;
    }
  }

  /**
   * Returns the response status.
   *
   * @return The response status.
   */
  Status status();

  /**
   * Returns the response error if the response status is {@code Status.ERROR}
   *
   * @return The response error.
   */
  RaftError error();
}
