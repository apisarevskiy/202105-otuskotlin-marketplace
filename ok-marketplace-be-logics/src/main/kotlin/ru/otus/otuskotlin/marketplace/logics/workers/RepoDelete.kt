package ru.otus.otuskotlin.marketplace.logics.workers

import ru.otus.otuskotlin.marketplace.backend.common.context.CorStatus
import ru.otus.otuskotlin.marketplace.backend.common.context.MpContext
import ru.otus.otuskotlin.marketplace.backend.common.models.MpStubCase
import ru.otus.otuskotlin.marketplace.backend.common.models.WorkMode
import ru.otus.otuskotlin.marketplace.backend.repo.common.DbAdIdRequest
import ru.otus.otuskotlin.marketplace.backend.repo.common.DbAdModelRequest
import ru.otus.otuskotlin.marketplace.backend.repo.common.IRepoAd
import ru.otus.otuskotlin.marketplace.common.cor.ICorChainDsl
import ru.otus.otuskotlin.marketplace.common.cor.handlers.worker

internal fun ICorChainDsl<MpContext>.repoDelete(title: String) = worker {
    this.title = title
    description = "The requested object will be deleted from the DB Repository"

    on { status == CorStatus.RUNNING }

    handle {
        val result = adRepo.delete(DbAdIdRequest(id = dbAd.id))
        val resultValue = result.result
        if (result.isSuccess && resultValue != null) {
            responseAd =  resultValue
        } else {
            result.errors.forEach {
                addError(it)
            }
        }
    }
}
