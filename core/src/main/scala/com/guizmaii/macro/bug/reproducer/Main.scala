package com.guizmaii.`macro`.bug.reproducer

import com.guizmaii.`macro`.bug.reproducer.macros.CompileTimeEnv

class Main extends App {

  assert(CompileTimeEnv.compileEnv.isInstanceOf[CompileTimeEnv.Staging], "CompileTime env is not staging")

}
