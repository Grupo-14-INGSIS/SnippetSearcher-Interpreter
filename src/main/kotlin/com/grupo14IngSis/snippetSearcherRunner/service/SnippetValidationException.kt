package com.grupo14IngSis.snippetSearcherRunner.service

import com.grupo14IngSis.snippetSearcherRunner.dto.ValidationResponse

class SnippetValidationException(
    override val message: String,
    val rule: String?,
    val line: Int?,
    val column: Int?
) : RuntimeException(message)