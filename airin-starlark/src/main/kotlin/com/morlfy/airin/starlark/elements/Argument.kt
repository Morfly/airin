@file:Suppress("FunctionName")

package com.morlfy.airin.starlark.elements


/**
 *
 */
class Argument(
    val id: String,
    override var value: Expression?
) : ValueHolder {

    // comparison by id only
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as Argument
        if (id != other.id) return false
        return true
    }

    override fun hashCode() = id.hashCode()


    override fun <A> accept(visitor: ElementVisitor<A>, position: Int, mode: PositionMode, accumulator: A) {
        visitor.visit(this, position, mode, accumulator)
    }
}

/**
 *
 */
fun Arguments(args: Map<String, *>): LinkedHashSet<Argument> =
    args.mapTo(linkedSetOf()) { (id, value) -> Argument(id, Expression(value)) }