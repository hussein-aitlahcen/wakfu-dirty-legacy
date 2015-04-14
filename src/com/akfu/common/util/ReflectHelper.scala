package com.akfu.common.util

package utils.common

import scala.reflect.runtime.universe._

object ReflectHelper {
  
  def classAnnotations[T: TypeTag]: Map[String, Map[String, Any]] = {
    typeOf[T].typeSymbol.asClass.annotations.map { a =>
      a.tree.tpe.typeSymbol.name.toString -> a.tree.children.withFilter {
        _.productPrefix eq "AssignOrNamedArg"
      }.map { tree =>
        tree.productElement(0).toString -> tree.productElement(1)
      }.toMap
    }.toMap
  }

  def methodAnnotations[T: TypeTag]: Map[String, Map[String, Map[String, Any]]] = {
    typeOf[T].decls.collect { case m: MethodSymbol => m }.withFilter {
      _.annotations.length > 0
    }.map { m =>
      m.name.toString -> m.annotations.map { a =>
        a.tree.tpe.typeSymbol.name.toString -> a.tree.children.withFilter {
         _.productPrefix eq "AssignOrNamedArg"
        }.map { tree =>
          tree.productElement(0).toString -> tree.productElement(1)
        }.toMap
      }.toMap
    }.toMap
  }
}