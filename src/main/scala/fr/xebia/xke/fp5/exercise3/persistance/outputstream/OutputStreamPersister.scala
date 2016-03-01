package fr.xebia.xke.fp5.exercise3.persistance.outputstream

import java.io.PrintWriter

import fr.xebia.xke.fp5.exercise3.persistance.PersistanceLayer


object OutputStreamPersister {

  def outputStreamPersister(printWriter: PrintWriter): PersistanceLayer =
    new PersistanceLayer {

      override def persist(s: String): Unit = printWriter.println(s)

    }

}
