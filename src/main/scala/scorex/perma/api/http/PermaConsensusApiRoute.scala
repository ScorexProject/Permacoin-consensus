package scorex.perma.api.http

import javax.ws.rs.Path

import akka.actor.ActorRefFactory
import akka.http.scaladsl.server.Route
import io.swagger.annotations._
import play.api.libs.json.Json
import scorex.api.http.{ApiRoute, CommonApiFunctions}
import scorex.app.Application
import scorex.crypto.encode.Base58
import scorex.perma.consensus.PermaConsensusModule


@Path("/consensus")
@Api(value = "/consensus", description = "Consensus-related calls")
class PermaConsensusApiRoute(override val application: Application)
                            (implicit val context: ActorRefFactory)
  extends ApiRoute with CommonApiFunctions {

  private val consensusModule = application.consensusModule.asInstanceOf[PermaConsensusModule]
  private val blockStorage = application.blockStorage

  val blockchain = blockStorage.history

  override val route: Route =
    pathPrefix("consensus") {
      algo ~ target ~ targetId ~ puz ~ puzId
    }

  @Path("/target")
  @ApiOperation(value = "Last target", notes = "Target of a last block", httpMethod = "GET")
  def target: Route = {
    path("target") {
      getJsonRoute {
        Json.obj("target" -> consensusModule.consensusBlockData(blockchain.lastBlock).target.toString)
      }
    }
  }

  @Path("/target/{blockId}")
  @ApiOperation(value = "Target of selected block", notes = "Target of a block with specified id", httpMethod = "GET")
  @ApiImplicitParams(Array(
    new ApiImplicitParam(name = "blockId", value = "Block id ", required = true, dataType = "String", paramType = "path")
  ))
  def targetId: Route = {
    path("target" / Segment) { case encodedSignature =>
      getJsonRoute {
        withBlock(blockchain, encodedSignature) { block =>
          Json.obj(
            "target" -> consensusModule.consensusBlockData(block).target.toString
          )
        }
      }
    }
  }

  @Path("/puz")
  @ApiOperation(value = "Current puzzle", notes = "Current puzzle", httpMethod = "GET")
  def puz: Route = {
    path("puz") {
      getJsonRoute {
        Json.obj("puz" -> Base58.encode(consensusModule.generatePuz(blockchain.lastBlock)))
      }
    }
  }

  @Path("/puz/{blockId}")
  @ApiOperation(value = "Puzzle of selected block", notes = "Puzzle of a block with specified id", httpMethod = "GET")
  @ApiImplicitParams(Array(
    new ApiImplicitParam(name = "blockId", value = "Block id ", required = true, dataType = "String", paramType = "path")
  ))
  def puzId: Route = {
    path("puz" / Segment) { case encodedSignature =>
      getJsonRoute {
        withBlock(blockchain, encodedSignature) { block =>
          Json.obj(
            "puz" -> Base58.encode(consensusModule.consensusBlockData(block).puz)
          )
        }
      }
    }
  }

  @Path("/algo")
  @ApiOperation(value = "Consensus algo", notes = "Shows which consensus algo being using", httpMethod = "GET")
  def algo: Route = {
    path("algo") {
      getJsonRoute {
        Json.obj("consensusAlgo" -> "perma")
      }
    }
  }
}
