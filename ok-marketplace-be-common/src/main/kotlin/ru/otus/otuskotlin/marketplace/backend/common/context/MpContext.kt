package ru.otus.otuskotlin.marketplace.backend.common.context

import ru.otus.otuskotlin.marketplace.backend.common.models.*
import java.time.Instant

data class MpContext(
    var startTime : Instant = Instant.MIN,
    var operation: MpOperations = MpOperations.NONE,

    var onRequest: String = "",
    var requestAdId: AdIdModel = AdIdModel.NONE,
    var requestAd: AdModel = AdModel(),
    var responseAd: AdModel = AdModel(),
    var requestPage: PaginatedModel = PaginatedModel(),
    var responsePage: PaginatedModel = PaginatedModel(),
    var responseAds: MutableList<AdModel> = mutableListOf(),
    var errors: MutableList<IError> = mutableListOf(),
    var status: CorStatus = CorStatus.STARTED,
) {
    enum class MpOperations {
        NONE,
        INIT,
        CREATE,
        READ,
        UPDATE,
        DELETE,
        SEARCH,
        OFFER
    }

    /**
     * Добавляет ошибку в контекст
     *
     * @param failingStatus Необходимо ли установить статус выполнения в FAILING (true/false)
     * @param
     */
    fun addError(failingStatus: Boolean = true, lambda: CommonErrorModel.() -> Unit) = apply {
        if (failingStatus) status = CorStatus.FAILING
        errors.add(
            CommonErrorModel(
                field = "_", level = IError.Level.ERROR
            ).apply(lambda)
        )
    }
}
