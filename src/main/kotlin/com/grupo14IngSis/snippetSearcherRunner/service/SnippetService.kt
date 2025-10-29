package com.grupo14IngSis.snippetSearcherRunner.service

import com.grupo14IngSis.snippetSearcherRunner.dto.SnippetCreationRequest
import com.grupo14IngSis.snippetSearcherRunner.dto.SnippetCreationResponse
import org.springframework.stereotype.Service

// Modelo interno para el resultado de la validación
data class ValidationResult(
    val isValid: Boolean,
    val rule: String? = null,
    val line: Int? = null,
    val column: Int? = null,
)

@Service
class SnippetService(/* private val snippetRepository: SnippetRepository */) {
    fun validateAndSave(request: SnippetCreationRequest): SnippetCreationResponse {
        // 1. **Validación del Snippet** (Lógica del parser)
        val validationResult = validateCode(request.code, request.language)

        if (!validationResult.isValid) {
            // Si es inválido, lanzamos la excepción con los detalles
            throw SnippetValidationException(
                message = "El código no es válido según el parser.",
                rule = validationResult.rule,
                line = validationResult.line,
                column = validationResult.column,
            )
        }

        // 2. **Persistencia** (Simulación)
        // snippetRepository.save(...)
        val snippetId = "RUNNER-${System.currentTimeMillis()}"

        // 3. **Respuesta Exitosa**
        return SnippetCreationResponse(
            success = true,
            message = "Snippet '${request.name}' creado y guardado con ID: $snippetId",
        )
    }

    // Simulación del parser según las "Notas" del caso de uso
    private fun validateCode(
        code: String,
        language: String,
    ): ValidationResult {
        // Simulación: Falla si el código contiene una palabra prohibida
        if (code.contains("prohibited_call", ignoreCase = true)) {
            return ValidationResult(
                isValid = false,
                rule = "Uso de 'prohibited_call' no permitido en $language",
                line = 10,
                column = 5,
            )
        }
        return ValidationResult(true)
    }
}
