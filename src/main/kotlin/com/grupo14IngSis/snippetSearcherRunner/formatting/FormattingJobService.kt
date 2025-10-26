package com.grupo14IngSis.snippetSearcherRunner.formatting

import com.grupo14IngSis.snippetSearcherRunner.client.SnippetServiceClient
import com.grupo14IngSis.snippetSearcherRunner.formatting.dto.FormattingJob
import com.grupo14IngSis.snippetSearcherRunner.formatting.dto.FormattingJobStatus
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class FormattingJobService(
    private val formattingJobRepository: FormattingJobRepository,
    private val formattingJobProcessor: FormattingJobProcessor,
    private val snippetServiceClient: SnippetServiceClient, // ← Cambiado
) {
    fun createFormattingJob(
        ruleId: String,
        userId: Int,
    ): FormattingJob {
        val totalSnippets = snippetServiceClient.countSnippetsByUserId(userId) // ← Cambiado

        val job =
            FormattingJob(
                id = UUID.randomUUID(),
                ruleId = ruleId,
                userId = userId,
                totalSnippets = totalSnippets,
                status = FormattingJobStatus.PENDING,
            )

        return formattingJobRepository.save(job)
    }

    suspend fun processNextJob() {
        val job = formattingJobRepository.getNextPendingJob() ?: return
        formattingJobProcessor.processJob(job)
    }

    fun getJobStatus(jobId: String): FormattingJob? {
        return formattingJobRepository.findById(jobId)
    }
}
