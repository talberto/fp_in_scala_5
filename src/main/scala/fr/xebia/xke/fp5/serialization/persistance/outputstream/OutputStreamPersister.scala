package fr.xebia.xke.fp5.serialization.persistance.outputstream

import java.io.PrintWriter

import fr.xebia.xke.fp5.serialization.persistance.PersistanceLayer


object OutputStreamPersister {

  def outputStreamPersister(printWriter: PrintWriter): PersistanceLayer =
    new PersistanceLayer {

      override def persist(s: String): Unit = printWriter.println(s)

    }

}
