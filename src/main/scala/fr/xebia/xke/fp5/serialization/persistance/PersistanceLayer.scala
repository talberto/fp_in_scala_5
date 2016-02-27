package fr.xebia.xke.fp5.serialization.persistance

trait PersistanceLayer {

  // TODO: return validation with possible errors
  def persist(string: String): Unit

}
