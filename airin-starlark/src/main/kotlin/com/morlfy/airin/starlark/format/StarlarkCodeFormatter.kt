package com.morlfy.airin.starlark.format

import com.morlfy.airin.starlark.elements.*
import com.morlfy.airin.starlark.elements.PositionMode.*


/**
 *
 */
const val DEFAULT_INDENT_SIZE = 4

/**
 *
 */
internal val nl: String = System.getProperty("line.separator")

/**
 *
 */
class StarlarkCodeFormatter(indentSize: Int = DEFAULT_INDENT_SIZE) : ElementVisitor<Appendable>, BazelFileFormatter {

    private val indent = " ".repeat(indentSize)

    private val _indents = hashMapOf(0 to "", 1 to indent)

    private fun indent(position: Int): String {
        require(position >= 0) { "Indent 'position' must be non-negative but was $position." }

        return _indents[position] ?: synchronized(this) {
            if (position in _indents) _indents[position]!!
            else {
                val value = indent.repeat(position)
                _indents[position] = value
                value
            }
        }
    }


    override fun format(bazelFile: BazelFile): String {
        val accumulator = StringBuilder()
        visit(bazelFile, position = 0, mode = NEW_LINE, accumulator)
        return accumulator.toString()
    }

    override fun format(bazelFile: BazelFile, accumulator: Appendable) {
        visit(bazelFile, position = 0, mode = NEW_LINE, accumulator)
    }


    // TODO test
    override fun visit(element: Element, position: Int, mode: PositionMode, acc: Appendable) {
        element.accept(this, position, mode, acc)
    }

    // TODO test
    override fun visit(element: BazelFile, position: Int, mode: PositionMode, acc: Appendable) {
        for (statement in element.statements) {
            visit(statement, position, mode, acc)
            acc += nl
        }
    }

    // tested
    override fun visit(element: Expression?, position: Int, mode: PositionMode, acc: Appendable) {
        if (element == null) {
            acc += when (mode) {
                NEW_LINE -> indent(position)
                CONTINUE_LINE -> ""
                SINGLE_LINE -> TODO()
            }
            acc += None
        } else element.accept(this, position, mode, acc)
    }

    // TODO test
    override fun visit(element: ExpressionStatement, position: Int, mode: PositionMode, acc: Appendable) {
        acc += nl
        visit(element.expression, position, mode, acc)
    }

    // tested
    override fun visit(element: Argument, position: Int, mode: PositionMode, acc: Appendable) {
        val indent = when (mode) {
            NEW_LINE -> indent(position)
            CONTINUE_LINE -> ""
            SINGLE_LINE -> TODO()
        }

        acc += indent
        if (element.id.isNotBlank())
            acc += element.id + " = "

        visit(element.value, position, CONTINUE_LINE, acc)
    }

    // tested
    override fun visit(element: Assignment, position: Int, mode: PositionMode, acc: Appendable) {
        require(mode == NEW_LINE) { "Assignment statements must be formatted only in NEW_LINE mode but was $mode." }

        acc += indent(position)
        acc += element.name + " = "
        visit(element.value, position, CONTINUE_LINE, acc)
    }

    // tested
    override fun visit(element: DynamicValue, position: Int, mode: PositionMode, acc: Appendable) {
        visit(element.value, position, mode, acc)
    }

    // tested
    override fun visit(element: BinaryOperation, position: Int, mode: PositionMode, acc: Appendable) {
        visit(element.left, position, mode, acc)
        acc += " ${element.operator} "
        visit(element.right, position, mode = CONTINUE_LINE, acc)
    }

    // tested
    override fun visit(element: ListExpression, position: Int, mode: PositionMode, acc: Appendable) {
        val indent = indent(position)
        val firstLineIndent = when (mode) {
            NEW_LINE -> indent
            CONTINUE_LINE -> ""
            SINGLE_LINE -> TODO()
        }

        val list = element.value
        when (list.size) {
            0 -> acc += "$firstLineIndent[]"
            1 -> {
                acc += "$firstLineIndent["
                visit(list.first(), position, CONTINUE_LINE, acc)
                acc += ']'
            }
            else -> {
                acc += "$firstLineIndent[$nl"
                for (item in list) {
                    visit(item, position + 1, NEW_LINE, acc)
                    acc += ",$nl"
                }
                acc += "$indent]"
            }
        }
    }

    // tested
    override fun visit(element: DictionaryExpression, position: Int, mode: PositionMode, acc: Appendable) {
        val indent = indent(position)
        val firstLineIndent = when (mode) {
            NEW_LINE -> indent
            CONTINUE_LINE -> ""
            SINGLE_LINE -> TODO()
        }

        val dict = element.value.entries
        when (dict.size) {
            0 -> acc += "$firstLineIndent{}"
            1 -> {
                val (key, value) = dict.first()
                acc += "$firstLineIndent{"
                visit(key, position, CONTINUE_LINE, acc)
                acc += ": "
                visit(value, position, CONTINUE_LINE, acc)
                acc += '}'
            }
            else -> {
                acc += "$firstLineIndent{$nl"
                for ((key, value) in dict) {
                    visit(key, position + 1, NEW_LINE, acc)
                    acc += ": "
                    visit(value, position + 1, CONTINUE_LINE, acc)
                    acc += ",$nl"
                }
                acc += "$indent}"
            }
        }
    }

    // tested
    override fun visit(element: ListComprehension<*>, position: Int, mode: PositionMode, acc: Appendable) {
        val indent = indent(position)
        val firstLineIndent = when (mode) {
            NEW_LINE -> indent
            CONTINUE_LINE -> ""
            SINGLE_LINE -> TODO()
        }

        acc += firstLineIndent
        acc += '['

        val newLine = when (element.body) {
            is BinaryOperation, is Comprehension, is FunctionCall -> nl
            else -> null
        }
        val childrenIndentMode = if (newLine != null) NEW_LINE else CONTINUE_LINE

        acc += newLine ?: ""
        visit(element.body, position + 1, childrenIndentMode, acc)
        acc += newLine ?: " "
        val clauses = element.clauses
        for (i in clauses.indices) {
            visit(clauses[i], position + 1, childrenIndentMode, acc)
            if (i < clauses.lastIndex) acc += newLine ?: " "
        }
        acc += newLine?.let { it + indent } ?: ""
        acc += ']'
    }

    // TODO
    override fun visit(element: DictionaryComprehension<*, *>, position: Int, mode: PositionMode, acc: Appendable) {
        val firstLineIndent = when (mode) {
            NEW_LINE -> indent(position)
            CONTINUE_LINE -> ""
            SINGLE_LINE -> TODO()
        }

        acc += firstLineIndent
        acc += '{'
        acc += '}'
    }

    // tested
    override fun visit(element: Comprehension.For, position: Int, mode: PositionMode, acc: Appendable) {
        val indent = when (mode) {
            NEW_LINE -> indent(position)
            CONTINUE_LINE -> ""
            SINGLE_LINE -> TODO()
        }
        acc += indent
        acc += "for "
        val variables = element.variables
        for (i in variables.indices) {
            visit(variables[i], position, CONTINUE_LINE, acc)
            if (i < variables.lastIndex) acc += ", "
        }

        acc += " in "
        visit(element.iterable, position + 1, CONTINUE_LINE, acc)
    }

    // tested
    override fun visit(element: Comprehension.If, position: Int, mode: PositionMode, acc: Appendable) {
        acc += "if "
        visit(element.condition, position, CONTINUE_LINE, acc)
    }

    // tested
    override fun visit(element: FunctionCall, position: Int, mode: PositionMode, acc: Appendable) {
        val indent = indent(position)
        val firstLineIndent = when (mode) {
            NEW_LINE -> indent
            CONTINUE_LINE -> ""
            SINGLE_LINE -> TODO()
        }

        val name = element.name
        val args = element.args
        when (args.size) {
            0 -> acc += "$firstLineIndent$name()"
            1 -> {
                val arg = args.first()
                acc += "$firstLineIndent$name("
                visit(arg, position, CONTINUE_LINE, acc)
                acc += ')'
            }
            else -> {
                acc += firstLineIndent
                acc += name
                acc += "($nl"
                for (arg in args) {
                    visit(arg, position + 1, NEW_LINE, acc)
                    acc += ",$nl"
                }
                acc += indent
                acc += ')'
            }
        }
    }

    // tested
    override fun visit(element: StringLiteral, position: Int, mode: PositionMode, acc: Appendable) {
        val indent = indent(position)
        val firstLineIndent = when (mode) {
            NEW_LINE -> indent
            CONTINUE_LINE -> ""
            SINGLE_LINE -> TODO()
        }

        val lines = element.value.lines()
        when (lines.size) {
            1 -> {
                acc += firstLineIndent
                acc += '"'
                acc += lines.first()
                acc += '"'
            }
            else -> {
                acc += firstLineIndent
                acc += "\"\"\""
                acc += nl
                for (line in lines) {
                    acc += indent
                    acc += line
                    acc += nl
                }
                acc += indent
                acc += "\"\"\""
            }
        }
    }

    // tested
    override fun visit(element: IntegerLiteral, position: Int, mode: PositionMode, acc: Appendable) {
        val indent = when (mode) {
            NEW_LINE -> indent(position)
            CONTINUE_LINE -> ""
            SINGLE_LINE -> TODO()
        }
        acc += "$indent${element.value}"
    }

    // tested
    override fun visit(element: FloatLiteral, position: Int, mode: PositionMode, acc: Appendable) {
        val indent = when (mode) {
            NEW_LINE -> indent(position)
            CONTINUE_LINE -> ""
            SINGLE_LINE -> TODO()
        }
        acc += "$indent${element.value}"
    }

    // tested
    override fun visit(element: BooleanLiteral, position: Int, mode: PositionMode, acc: Appendable) {
        val indent = when (mode) {
            NEW_LINE -> indent(position)
            CONTINUE_LINE -> ""
            SINGLE_LINE -> TODO()
        }
        acc += indent
        acc += when (element.value) {
            true -> True
            false -> False
        }
    }

    override fun visit(element: LoadStatement, position: Int, mode: PositionMode, acc: Appendable) {
        val indent = indent(position)

        val symbols = element.symbols
        when {
            symbols.size == 1 -> {
                acc += "load("
                visit(element.file, position = 1, CONTINUE_LINE, acc)
                acc += ", "
                visit(symbols.first(), position = 1, CONTINUE_LINE, acc)
                acc += ')'
            }
            symbols.size > 1 -> {
                acc += "load($nl"
                visit(element.file, position = 1, NEW_LINE, acc)
                acc += ",$nl"
                for (symbol in symbols) {
                    visit(symbol, position = 1, NEW_LINE, acc)
                    acc += ",$nl"
                }
                acc += ')'
            }
        }
    }

    // TODO test
    override fun visit(element: LoadStatement.Symbol, position: Int, mode: PositionMode, acc: Appendable) {
        val indent = when (mode) {
            NEW_LINE -> indent(position)
            CONTINUE_LINE -> ""
            SINGLE_LINE -> TODO()
        }

        acc += indent
        if (element.alias != null) {
            acc += element.alias
            acc += " = "
        }
        visit(element.name, position, CONTINUE_LINE, acc)
    }

    override fun visit(element: RawStatement, position: Int, mode: PositionMode, acc: Appendable) {
        TODO("Not yet implemented")
    }

    // tested
    override fun visit(element: Reference, position: Int, mode: PositionMode, acc: Appendable) {
        val indent = when (mode) {
            NEW_LINE -> indent(position)
            CONTINUE_LINE -> ""
            SINGLE_LINE -> TODO()
        }
        acc += indent
        acc += element.name
    }

    // tested
    override fun visit(element: EmptyLineStatement, position: Int, mode: PositionMode, acc: Appendable) {
        acc += nl
    }

    // FIXME
    override fun visit(element: RawText, position: Int, mode: PositionMode, acc: Appendable) {
        acc += element.value
    }
}