/*
 * Copyright 2016 Dennis Vriend
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package nl.pragmasoft.pekko.persistence.inmemory.query

import org.apache.pekko
import pekko.actor.ExtendedActorSystem
import pekko.persistence.query.ReadJournalProvider
import com.typesafe.config.Config
import nl.pragmasoft.pekko.persistence.inmemory.extension.StorageExtensionProvider

class InMemoryReadJournalProvider(system: ExtendedActorSystem, config: Config) extends ReadJournalProvider {
  private val scalaJournal = new scaladsl.InMemoryReadJournal(config, StorageExtensionProvider(system).journalStorage(config))(system)
  private val javaJournal = new javadsl.InMemoryReadJournal(scalaJournal)

  override def scaladslReadJournal(): pekko.persistence.query.scaladsl.ReadJournal = scalaJournal

  override def javadslReadJournal(): pekko.persistence.query.javadsl.ReadJournal = javaJournal
}
