/**
* Marketplace
* Marketplace - учебный проект курса \"Backend разработка на Kotlin\"
*
* The version of the OpenAPI document: 0.0.1
* 
*
* NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
* https://openapi-generator.tech
* Do not edit the class manually.
*/
package ru.otus.otuskotlin.marketplace.kmp.transport.models

import kotlinx.serialization.*

/**
 * Структура для ответа на запрос поиска предложений для объявления
 * @param requestId 
 * @param result 
 * @param errors 
 * @param page 
 * @param offeredAds 
 */
@Serializable
data class OffersAdResponse (
    override val requestId: String? = null,
    override val result: Result? = null,
    override val errors: List<RequestError>? = null,
    val page: BasePaginatedResponse? = null,
    val offeredAds: List<ResponseAd>? = null
) : BaseResponse(requestId, result, errors)

