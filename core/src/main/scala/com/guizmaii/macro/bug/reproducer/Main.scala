package com.guizmaii.`macro`.bug.reproducer

import com.guizmaii.`macro`.bug.reproducer.macros.{CompileTimeEnv, CompileTimeEnvMacro}

class Main extends App {

  assert(CompileTimeEnvMacro.compileEnv.isInstanceOf[CompileTimeEnv.Staging], "CompileTime env is not staging")

}
