package recomend.util


import java.io.ByteArrayInputStream


import org.apache.hadoop.conf.Configuration
import org.apache.hadoop.hbase.client._
import org.apache.hadoop.hbase.filter.CompareFilter.CompareOp
import org.apache.hadoop.hbase.filter.{Filter, RegexStringComparator, RowFilter}

import scala.collection.mutable
import scala.collection.JavaConversions._
import scala.collection.mutable


/**
  * Created by Administrator on 2017/2/26.
  */
object HbaseTool {
  val table = new mutable.HashMap[String, HTable]()
  var conf = HBaseConfiguration.create()


  def setConf(c: Configuration) = {
    conf = c
  }

  def getTable(tableName: String) : HTable = {
    table.getOrElse(tableName, {
      println("----new connection----")
      val tbl = new HTable(conf, tableName)
      table(tableName) = tbl
      tbl
    })
  }

  def getSimgleValue(tableName: String, rowKey: String, family: String, qualifiers: String): AnyRef = {
    val table_t = getTable(tableName)
    val row1 = new Get(Bytes.toBytes(rowKey))
    val HBaseRow = table_t.get(row1)
    var obh: AnyRef = null
    //new JSONArray[List[String]]
    //var bv = new Array[Byte](0)
    // var bv = new String
    if (HBaseRow != null && !HBaseRow.isEmpty) {
      val b = new ByteArrayInputStream(HBaseRow.getValue(Bytes.toBytes(family), Bytes.toBytes(qualifiers)))
      val o = new ObjectInputStream(b)
      obh = o.readObject()
      o.close()
      //bv = new String(HBaseRow.getValue(Bytes.toBytes(family), Bytes.toBytes(qualifiers)),"UTF-8")
    }
    obh
  }




}
