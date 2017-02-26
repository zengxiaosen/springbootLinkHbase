package recomend.util
import java.util

import scala.collection.JavaConversions._
import org.apache.hadoop.hbase.HBaseConfiguration
import org.apache.hadoop.hbase.trace.HBaseHTraceConfiguration

import scala.collection.mutable.ListBuffer
import scala.util.parsing.json.JSONArray
/**
  * Created by Administrator on 2017/2/26.
  */

/**
  * provides DAL for Customer entities for MySQL database
  */
object ModuleDAO {
  private var hb = HbaseTool

  {
    //lond the config of Hbase，create Table recomend
    var confHbase = HBaseConfiguration.create()
    confHbase.set("hbase.zookeeper.property.clientPort", "2181")
    confHbase.set("hbase.zookeeper.quorum", "spark1.soledede.com,spark2.soledede.com,spark3.soledede.com")
    confHbase.set("hbase.master", "spark1.soledede.com:60000")
    confHbase.addResource("/opt/cloudera/parcels/CDH/lib/hbase/conf/hbase-site.xml")
    ModuleDAO.hb.setConf(confHbase)
  }

  /**
    *
    * @param id
    * @return
    */
  def get(id: String): java.util.List[String] = {

    //val list: ListBuffer[String] = ModuleDAO.hb.getSingleValue("recommend", id, "fruitTopCF", "fruitId").asInstanceOf[ListBuf


    val jsonStr: JSONArray = ModuleDAO.hb.getSingleValue("recommend", id, "fruitTopCF", "fruitId").asInstanceOf[JSONArray]
    val list = jsonStr.list
    //val jsonaAyr = JSON.parseArray(jsonStr)
    result(list) match {
      case Some(toReturn) => return toReturn
      case None => null
    }
  }

  def test() {
    println("yesk")
  }

  def filter(userId: String): java.util.List[String] = {
    val list: JSONArray = ModuleDAO.hb.scanLike("recommend", userId.toInt.hashCode().toString).asInstanceOf[JSONArray]
    result(list.list) match {
      case Some(toReturn) => return toReturn
      case None => null
    }
  }

  def result(list: Seq[Any]): Option[List[String]] = {
    if (list == null || list.length <= 0) return Some(null)
    val results = new util.ArrayList[String]()

    var returnList = for (item <- list) yield {
      val stringItem: String = String.valueOf(item)
      val rArray = stringItem.split("::")
      rArray(0)
    }

    returnList = for (itemid <- returnList; if (itemid != null)) yield itemid
    returnList.foreach { r => results.add(r) }
    Some(results.toList)
  }

}
