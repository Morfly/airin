package com.morlfy.airin.starlark.elements


/**
 *
 */
interface ElementVisitor<A> {

    //
    fun visit(element: Element, position: Int, mode: PositionMode, acc: A)

    fun visit(element: BazelFile, position: Int, mode: PositionMode, acc: A)

    fun visit(element: Expression?, position: Int, mode: PositionMode, acc: A)
    
    fun visit(element: ExpressionStatement, position: Int, mode: PositionMode, acc: A)

    fun visit(element: Argument, position: Int, mode: PositionMode, acc: A)

    fun visit(element: Assignment, position: Int, mode: PositionMode, acc: A)

    fun visit(element: DynamicValue, position: Int, mode: PositionMode, acc: A)

    fun visit(element: BinaryOperation, position: Int, mode: PositionMode, acc: A)

    fun visit(element: ListExpression, position: Int, mode: PositionMode, acc: A)

    fun visit(element: DictionaryExpression, position: Int, mode: PositionMode, acc: A)

    fun visit(element: ListComprehension<*>, position: Int, mode: PositionMode, acc: A)

    fun visit(element: DictionaryComprehension<*, *>, position: Int, mode: PositionMode, acc: A)

    fun visit(element: Comprehension.For, position: Int, mode: PositionMode, acc: A)

    fun visit(element: Comprehension.If, position: Int, mode: PositionMode, acc: A)

    fun visit(element: FunctionCall, position: Int, mode: PositionMode, acc: A)

    fun visit(element: StringLiteral, position: Int, mode: PositionMode, acc: A)

    fun visit(element: IntegerLiteral, position: Int, mode: PositionMode, acc: A)

    fun visit(element: FloatLiteral, position: Int, mode: PositionMode, acc: A)

    fun visit(element: BooleanLiteral, position: Int, mode: PositionMode, acc: A)

    fun visit(element: LoadStatement, position: Int, mode: PositionMode, acc: A)

    fun visit(element: LoadStatement.Symbol, position: Int, mode: PositionMode, acc: A)

    fun visit(element: RawStatement, position: Int, mode: PositionMode, acc: A)

    fun visit(element: Reference, position: Int, mode: PositionMode, acc: A)

    fun visit(element: EmptyLineStatement, position: Int, mode: PositionMode, acc: A)

    fun visit(element: RawText, position: Int, mode: PositionMode, acc: A)
}