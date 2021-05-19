package com.morlfy.airin.starlark.elements


/**
 *
 */
sealed interface BazelRcStatement : Statement


class CommandStatement(
    val command: String,
    val config: String?,
    val option: String
) : BazelRcStatement {

    override fun <A> accept(visitor: ElementVisitor<A>, indentIndex: Int, mode: PositionMode, accumulator: A) {
        visitor.visit(this, indentIndex, mode, accumulator)
    }
}

class ImportStatement() : BazelRcStatement {
    override fun <A> accept(visitor: ElementVisitor<A>, indentIndex: Int, mode: PositionMode, accumulator: A) {
        visitor.visit(this, indentIndex, mode, accumulator)
    }
}

class TryImportStatement() : BazelRcStatement {
    override fun <A> accept(visitor: ElementVisitor<A>, indentIndex: Int, mode: PositionMode, accumulator: A) {
        visitor.visit(this, indentIndex, mode, accumulator)
    }
}