/*
 * Copyright 2021 Pavlo Stavytskyi
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

@file:Suppress("LocalVariableName")

package org.morfly.airin.starlark.lang.feature

import org.morfly.airin.starlark.elements.ListReference
import org.morfly.airin.starlark.elements.StringReference
import org.morfly.airin.starlark.lang.StringType
import org.morfly.airin.starlark.lang.api.LanguageContext


private fun ListComprehensionsFeatureUnderCompilationTest.CompilationTests() {
    var STRING_LIST_RESULT: List<StringType>?
    var STRING_MATRIX_RESULT: List<List<StringType>>?
    var STRING_MATRIX_3D_RESULT: List<List<List<StringType>>>?


    // =================================
    // ===== One dimensional lists =====
    // =================================

    // ["item1", "item2", "item3", "item4"]
    val LIST = listOf("item1", "item2", "item3", "item4")

    // [item for item in LIST]
    STRING_LIST_RESULT =
        "item" `in` LIST take { item: StringReference -> item }
    // [item for item in LIST if condition]
    STRING_LIST_RESULT =
        "item" `in` LIST `if` "condition" take { item: StringReference -> item }


    // =================================
    // ===== Two dimensional lists =====
    // =================================

    // [["item1", "item2"],
    //  ["item3", "item4"]]
    val MATRIX = listOf(listOf("item1", "item2"), listOf("item3", "item4"))

    // [item for sublist in MATRIX for item in sublist]
    STRING_LIST_RESULT =
        "sublist" `in` MATRIX `for` { sublist: ListReference<StringType> ->
            "item" `in` sublist take { item: StringReference -> item }
        }
    // [item for sublist in MATRIX if condition for item in sublist if condition]
    STRING_LIST_RESULT =
        "sublist" `in` MATRIX `if` "condition" `for` { sublist: ListReference<StringType> ->
            "item" `in` sublist `if` "condition" take { item: StringReference -> item }
        }

    // [sublist for sublist in MATRIX for item in sublist]
    STRING_MATRIX_RESULT =
        "sublist" `in` MATRIX `for` { sublist: ListReference<StringType> ->
            "item" `in` sublist take { sublist }
        }
    // [sublist for sublist in MATRIX if condition for item in sublist if condition]
    STRING_MATRIX_RESULT =
        "sublist" `in` MATRIX `if` "condition" `for` { sublist: ListReference<StringType> ->
            "item" `in` sublist `if` "condition" take { sublist }
        }

    // [sublist for sublist in Matrix]
    STRING_MATRIX_RESULT =
        "sublist" `in` MATRIX take { sublist: ListReference<StringType> -> sublist }
    // [sublist for sublist in Matrix if condition]
    STRING_MATRIX_RESULT =
        "sublist" `in` MATRIX `if` "condition" take { sublist: ListReference<StringType> -> sublist }


    // ===================================
    // ===== Three dimensional lists =====
    // ===================================

    // [[["item1"], ["item2"]],
    //  [["item3"], ["item4"]]]
    val MATRIX_3D = listOf(
        listOf(listOf("item1"), listOf("item2")),
        listOf(listOf("item3"), listOf("item4"))
    )

    // [item for sub_matrix in MATRIX_3D for sublist in sub_matrix for item in sublist]
    STRING_LIST_RESULT =
        "sub_matrix" `in` MATRIX_3D `for` { sub_matrix: ListReference<List<StringType>> ->
            "sublist" `in` sub_matrix `for` { sublist: ListReference<StringType> ->
                "item" `in` sublist take { item: StringReference -> item }
            }
        }
    // [item for sub_matrix in MATRIX_3D if condition
    //  for sublist in sub_matrix if condition
    //  for item in sublist if condition]
    STRING_LIST_RESULT =
        "sub_matrix" `in` MATRIX_3D `if` "condition" `for` { sub_matrix: ListReference<List<StringType>> ->
            "sublist" `in` sub_matrix `if` "condition" `for` { sublist: ListReference<StringType> ->
                "item" `in` sublist `if` "condition" take { item: StringReference -> item }
            }
        }

    // [sublist for sub_matrix in MATRIX_3D for sublist in sub_matrix for item in sublist]
    STRING_MATRIX_RESULT =
        "sub_matrix" `in` MATRIX_3D `for` { sub_matrix: ListReference<List<StringType>> ->
            "sublist" `in` sub_matrix `for` { sublist: ListReference<StringType> ->
                "item" `in` sublist take { sublist }
            }
        }
    // [sublist for sub_matrix in MATRIX_3D if "condition"
    //  for sublist in sub_matrix if "condition"
    //  for item in sublist if condition]
    STRING_MATRIX_RESULT =
        "sub_matrix" `in` MATRIX_3D `if` "condition" `for` { sub_matrix: ListReference<List<StringType>> ->
            "sublist" `in` sub_matrix `if` "condition" `for` { sublist: ListReference<StringType> ->
                "item" `in` sublist `if` "condition" take { sublist }
            }
        }

    // [sub_matrix for sub_matrix in MATRIX_3d for sublist in sub_matrix for item in sublist]
    STRING_MATRIX_3D_RESULT =
        "sub_matrix" `in` MATRIX_3D `for` { sub_matrix: ListReference<List<StringType>> ->
            "sublist" `in` sub_matrix `for` { sublist: ListReference<StringType> ->
                "item" `in` sublist take { sub_matrix }
            }
        }
    // [sub_matrix for sub_matrix in MATRIX_3d if "condition"
    //  for sublist in sub_matrix if "condition"
    //  for item in sublist if condition]
    STRING_MATRIX_3D_RESULT =
        "sub_matrix" `in` MATRIX_3D `if` "condition" `for` { sub_matrix: ListReference<List<StringType>> ->
            "sublist" `in` sub_matrix `if` "condition" `for` { sublist: ListReference<StringType> ->
                "item" `in` sublist `if` "condition" take { sub_matrix }
            }
        }

    // [sublist for sub_matrix in MATRIX_3d for sublist in sub_matrix]
    STRING_MATRIX_RESULT =
        "sub_matrix" `in` MATRIX_3D `for` { sub_matrix: ListReference<List<StringType>> ->
            "sublist" `in` sub_matrix take { sublist: ListReference<StringType> -> sublist }
        }
    // [sublist for sub_matrix in MATRIX_3d if condition for sublist in sub_matrix if condition]
    STRING_MATRIX_RESULT =
        "sub_matrix" `in` MATRIX_3D `if` "condition" `for` { sub_matrix: ListReference<List<StringType>> ->
            "sublist" `in` sub_matrix `if` "condition" take { sublist: ListReference<StringType> -> sublist }
        }

    // [sub_matrix for sub_matrix in MATRIX_3D for sublist in sub_matrix]
    STRING_MATRIX_3D_RESULT =
        "sub_matrix" `in` MATRIX_3D `for` { sub_matrix: ListReference<List<StringType>> ->
            "sublist" `in` sub_matrix take { sub_matrix }
        }
    // [sub_matrix for sub_matrix in MATRIX_3D if condition for sublist in sub_matrix if condition]
    STRING_MATRIX_3D_RESULT =
        "sub_matrix" `in` MATRIX_3D `if` "condition" `for` { sub_matrix: ListReference<List<StringType>> ->
            "sublist" `in` sub_matrix `if` "condition" take { sub_matrix }
        }

    // [sub_matrix for sub_matrix in MATRIX_3D]
    STRING_MATRIX_3D_RESULT =
        "sub_matrix" `in` MATRIX_3D take { sub_matrix: ListReference<List<StringType>> -> sub_matrix }
    // [sub_matrix for sub_matrix in MATRIX_3D if condition]
    STRING_MATRIX_3D_RESULT =
        "sub_matrix" `in` MATRIX_3D `if` "condition" take { sub_matrix: ListReference<List<StringType>> -> sub_matrix }


    // =========================================================
    // ===== Another comprehension as a comprehension body =====
    // =========================================================

    // [[j for j in LIST] for i in LIST]
    STRING_MATRIX_RESULT =
        "i" `in` LIST take { i: StringReference ->
            "j" `in` LIST take { j: StringReference -> j }
        }
    // [[j for j in LIST if condition] for i in LIST if condition]
    STRING_MATRIX_RESULT =
        "i" `in` LIST `if` "condition" take { i: StringReference ->
            "j" `in` LIST `if` "condition" take { j: StringReference -> j }
        }

    // [[[k for k in LIST] for j in LIST] for i in LIST]
    STRING_MATRIX_3D_RESULT =
        "i" `in` LIST take { i: StringReference ->
            "j" `in` LIST take { j: StringReference ->
                "k" `in` LIST take { k: StringReference -> k }
            }
        }
    // [[[k for k in LIST if condition] for j in LIST if condition] for i in LIST if condition]
    STRING_MATRIX_3D_RESULT =
        "i" `in` LIST `if` "condition" take { i: StringReference ->
            "j" `in` LIST `if` "condition" take { j: StringReference ->
                "k" `in` LIST `if` "condition" take { k: StringReference -> k }
            }
        }

    // [[item for i in LIST] for sublist in MATRIX for item in sublist]
    STRING_MATRIX_RESULT =
        "sublist" `in` MATRIX `for` { sublist: ListReference<StringType> ->
            "item" `in` sublist take { item: StringReference ->
                "i" `in` LIST take { i: StringReference -> item }
            }
        }
    // [[item for i in LIST if condition] for sublist in MATRIX if condition for item in sublist if condition]
    STRING_MATRIX_RESULT =
        "sublist" `in` MATRIX `if` "condition" `for` { sublist: ListReference<StringType> ->
            "item" `in` sublist `if` "condition" take { item: StringReference ->
                "i" `in` LIST `if` "condition" take { i: StringReference -> item }
            }
        }
}


private interface ListComprehensionsFeatureUnderCompilationTest :
// Feature under test
    ListComprehensionsFeature<LanguageContext>