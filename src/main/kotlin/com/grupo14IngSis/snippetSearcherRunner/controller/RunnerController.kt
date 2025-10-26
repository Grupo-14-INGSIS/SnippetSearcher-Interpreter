package com.grupo14IngSis.snippetSearcherRunner.controller

import com.grupo14IngSis.snippetSearcherRunner.dto.SnippetCreationRequest
import com.grupo14IngSis.snippetSearcherRunner.dto.SnippetCreationResponse
import com.grupo14IngSis.snippetSearcherRunner.dto.ValidationResponse
import com.grupo14IngSis.snippetSearcherRunner.service.SnippetService
import com.grupo14IngSis.snippetSearcherRunner.service.SnippetValidationException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/internal/snippets")
class RunnerController(private val snippetService: SnippetService) {

    // Endpoint llamado por SnippetSearcher-App
    @PostMapping
    fun processAndSave(@RequestBody request: SnippetCreationRequest): ResponseEntity<SnippetCreationResponse> {
        // La llamada puede lanzar SnippetValidationException
        val response = snippetService.validateAndSave(request)

        // Si no hay excepción, devuelve 201 Created
        return ResponseEntity.status(HttpStatus.CREATED).body(response)
    }

    // Este manejador captura la excepción de validación y asegura que la respuesta HTTP sea 400
    @ExceptionHandler(SnippetValidationException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleValidationException(e: SnippetValidationException): ValidationResponse {
        // Devuelve el DTO de error, que el App capturará en su WebClient
        return ValidationResponse(
            message = e.message,
            rule = e.rule,
            line = e.line,
            column = e.column
        )
    }
}