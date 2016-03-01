package fr.xebia.xke.fp5.exercise3.persistance

import fr.xebia.xke.fp5.exercise3.serializer.Serializable

object Persister {

  def persist[T, U](t: T)(implicit ev: PersistanceLayer, serializableEvidence: Serializable[T]): Unit =
    serializableEvidence.serialize(t).map(s => ev.persist(s))

}
