package com.akfu.common.util

sealed abstract class ValueType

object ValueType {
  object BOOLEAN extends ValueType
  object STRING extends ValueType
  object NUMBER extends ValueType
  object NUMBERLIST extends ValueType
  object STRINGLIST extends ValueType
}