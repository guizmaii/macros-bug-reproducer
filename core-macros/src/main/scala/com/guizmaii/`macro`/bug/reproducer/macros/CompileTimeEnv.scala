package com.guizmaii.`macro`.bug.reproducer.macros

sealed trait CompileTimeEnv extends Product with Serializable
object CompileTimeEnv {
  final case class Production(value: String) extends CompileTimeEnv
  final case class Staging(value: String)    extends CompileTimeEnv
  final case class Dev(value: String)        extends CompileTimeEnv
}
