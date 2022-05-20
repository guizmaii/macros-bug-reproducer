# Macros bug reproducer

This project aims to provide a minimal reproducer to a bug observed in a macro happening from time to time inconsistently.

To try to reproduce the bug, in a sbt console:
```
> reproduce
```

The bug we're trying to reproduce is generating the following error:
```scala
[error] exception during macro expansion:
[error] java.lang.IndexOutOfBoundsException: 0
[error] 	at scala.collection.LinearSeqOps.apply(LinearSeq.scala:117)
[error] 	at scala.collection.LinearSeqOps.apply$(LinearSeq.scala:114)
[error] 	at scala.reflect.internal.Importers$StandardImporter.recreateOrRelink$1(Importers.scala:183)
[error] 	at scala.reflect.internal.Importers$StandardImporter.importSymbol(Importers.scala:223)
[error] 	at scala.reflect.internal.Importers$StandardImporter.recreateType(Importers.scala:237)
[error] 	at scala.reflect.internal.Importers$StandardImporter.importType(Importers.scala:298)
[error] 	at scala.reflect.internal.Importers$StandardImporter.recreateType(Importers.scala:237)
[error] 	at scala.reflect.internal.Importers$StandardImporter.importType(Importers.scala:298)
[error] 	at scala.reflect.internal.Importers$StandardImporter.recreateSymbol(Importers.scala:141)
[error] 	at scala.reflect.internal.Importers$StandardImporter.cachedRecreateSymbol$1(Importers.scala:158)
[error] 	at scala.reflect.internal.Importers$StandardImporter.$anonfun$importSymbol$6(Importers.scala:207)
[error] 	at scala.reflect.internal.Symbols$Symbol.orElse(Symbols.scala:2646)
[error] 	at scala.reflect.internal.Importers$StandardImporter.recreateOrRelink$1(Importers.scala:206)
[error] 	at scala.reflect.internal.Importers$StandardImporter.importSymbol(Importers.scala:223)
[error] 	at scala.reflect.internal.Importers$StandardImporter.$anonfun$recreateType$5(Importers.scala:261)
[error] 	at scala.reflect.internal.Scopes$Scope.foreach(Scopes.scala:455)
[error] 	at scala.reflect.internal.Importers$StandardImporter.recreateType(Importers.scala:261)
[error] 	at scala.reflect.internal.Importers$StandardImporter.importType(Importers.scala:298)
[error] 	at scala.reflect.internal.Importers$StandardImporter$$anon$3.complete(Importers.scala:91)
[error] 	at scala.reflect.internal.Symbols$Symbol.completeInfo(Symbols.scala:1570)
[error] 	at scala.reflect.internal.Symbols$Symbol.info(Symbols.scala:1533)
[error] 	at scala.reflect.runtime.SynchronizedSymbols$SynchronizedSymbol$$anon$9.scala$reflect$runtime$SynchronizedSymbols$SynchronizedSymbol$$super$info(SynchronizedSymbols.scala:209)
[error] 	at scala.reflect.runtime.SynchronizedSymbols$SynchronizedSymbol.info(SynchronizedSymbols.scala:158)
[error] 	at scala.reflect.runtime.SynchronizedSymbols$SynchronizedSymbol.info$(SynchronizedSymbols.scala:158)
[error] 	at scala.reflect.runtime.SynchronizedSymbols$SynchronizedSymbol$$anon$9.info(SynchronizedSymbols.scala:209)
[error] 	at scala.reflect.internal.Importers$StandardImporter.recreateOrRelink$1(Importers.scala:180)
[error] 	at scala.reflect.internal.Importers$StandardImporter.importSymbol(Importers.scala:223)
[error] 	at scala.reflect.internal.Importers$StandardImporter.recreatedTreeCompleter(Importers.scala:312)
[error] 	at scala.reflect.internal.Importers$StandardImporter.$anonfun$importTree$1(Importers.scala:432)
[error] 	at scala.reflect.internal.Importers$StandardImporter.tryFixup(Importers.scala:61)
[error] 	at scala.reflect.internal.Importers$StandardImporter.importTree(Importers.scala:433)
[error] 	at scala.reflect.internal.Importers$StandardImporter.recreateTree(Importers.scala:397)
[error] 	at scala.reflect.internal.Importers$StandardImporter.importTree(Importers.scala:430)
[error] 	at scala.reflect.internal.Importers$StandardImporter.recreateTree(Importers.scala:388)
[error] 	at scala.reflect.internal.Importers$StandardImporter.importTree(Importers.scala:430)
[error] 	at scala.reflect.internal.Importers$StandardImporter.recreateTree(Importers.scala:397)
[error] 	at scala.reflect.internal.Importers$StandardImporter.importTree(Importers.scala:430)
[error] 	at scala.reflect.internal.Importers$StandardImporter.recreateTree(Importers.scala:361)
[error] 	at scala.reflect.internal.Importers$StandardImporter.importTree(Importers.scala:430)
[error] 	at scala.reflect.internal.Importers$StandardImporter.$anonfun$recreateTree$20(Importers.scala:388)
[error] 	at scala.collection.immutable.List.map(List.scala:246)
[error] 	at scala.reflect.internal.Importers$StandardImporter.recreateTree(Importers.scala:388)
[error] 	at scala.reflect.internal.Importers$StandardImporter.importTree(Importers.scala:430)
[error] 	at scala.reflect.internal.Importers$StandardImporter.recreateTree(Importers.scala:361)
[error] 	at scala.reflect.internal.Importers$StandardImporter.importTree(Importers.scala:430)
[error] 	at scala.reflect.internal.Importers$StandardImporter.$anonfun$recreateTree$20(Importers.scala:388)
[error] 	at scala.collection.immutable.List.map(List.scala:246)
[error] 	at scala.reflect.internal.Importers$StandardImporter.recreateTree(Importers.scala:388)
[error] 	at scala.reflect.internal.Importers$StandardImporter.importTree(Importers.scala:430)
[error] 	at scala.reflect.internal.Importers$StandardImporter.recreateTree(Importers.scala:369)
[error] 	at scala.reflect.internal.Importers$StandardImporter.importTree(Importers.scala:430)
[error] 	at scala.reflect.internal.Importers$StandardImporter.recreateTree(Importers.scala:337)
[error] 	at scala.reflect.internal.Importers$StandardImporter.importTree(Importers.scala:430)
[error] 	at scala.reflect.internal.Importers$StandardImporter.$anonfun$recreateTree$10(Importers.scala:347)
[error] 	at scala.reflect.internal.Importers$StandardImporter.recreateTree(Importers.scala:347)
[error] 	at scala.reflect.internal.Importers$StandardImporter.importTree(Importers.scala:430)
[error] 	at scala.reflect.internal.Importers$StandardImporter.importTree(Importers.scala:41)
[error] 	at scala.reflect.macros.contexts.Evals.eval(Evals.scala:31)
[error] 	at scala.reflect.macros.contexts.Evals.eval$(Evals.scala:26)
[error] 	at scala.reflect.macros.contexts.Context.eval(Context.scala:18)
[error] 	at <...>.CompileTimeEnv$.compileEnvMacro(CompileTimeEnv.scala:51)
[error]     @inline def compileTimeEnv: CompileTimeEnv = CompileTimeEnv.compileEnv
```