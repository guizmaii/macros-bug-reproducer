package com.guizmaii.`macro`.bug.reproducer.macros

import scala.annotation.nowarn
import scala.reflect.macros.whitebox

sealed trait CompileTimeEnv extends Product with Serializable
object CompileTimeEnv {
  final case class Production(value: String) extends CompileTimeEnv
  final case class Staging(value: String)    extends CompileTimeEnv
  final case class Dev(value: String)        extends CompileTimeEnv

  def compileEnv: CompileTimeEnv = macro compileEnvMacro

  def compileEnvMacro(c: whitebox.Context): c.Expr[CompileTimeEnv] = {
    import c.universe.*

    /**
     * Needs to be defined here because the `Liftable` instance must come from `c.universe.*`
     * Otherwise, the compiler will not find the `Liftable` implicit instance.
     *
     * About `Liftable`, see doc: https://docs.scala-lang.org/overviews/quasiquotes/lifting.html
     */
    @nowarn("cat=unused")
    implicit val liftable: Liftable[CompileTimeEnv] = {
      val ProductionSym = symbolOf[Production].companion
      val StagingSym    = symbolOf[Staging].companion
      val DevSym        = symbolOf[Dev].companion

      Liftable[CompileTimeEnv] {
        case Production(value) => q"$ProductionSym($value)"
        case Staging(value)    => q"$StagingSym($value)"
        case Dev(value)        => q"$DevSym($value)"
      }
    }

    val cc = reify(c)

    val result: CompileTimeEnv =
      c.eval {
        reify {
          val ccc = cc.splice

          sys.env.get("ENV") match {
            case None                                     => ccc.abort(ccc.enclosingPosition, "'ENV' envvar is not set")
            case Some(e) if e.isBlank                     => ccc.abort(ccc.enclosingPosition, "Invalid 'ENV' envvar value: Can't be empty")
            case Some(e) if e.toLowerCase == "production" => CompileTimeEnv.Production(value = e)
            case Some(e) if e.toLowerCase == "staging"    => CompileTimeEnv.Staging(value = e)
            case Some(e) if e.toLowerCase == "dev"        => CompileTimeEnv.Dev(value = e)
            case e                                        => ccc.abort(ccc.enclosingPosition, s"Invalid 'ENV' envvar value: $e is not a valid env")
          }
        }
      }

    c.Expr(q"$result")
  }

}
