package com.morlfy.airin.starlark


class TestContext {

    val args = mutableMapOf<String, Any>()

    val name: String? by args
}