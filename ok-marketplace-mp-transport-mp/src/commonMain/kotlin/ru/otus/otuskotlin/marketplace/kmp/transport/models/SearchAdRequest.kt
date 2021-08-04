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
 * Структура для запроса поиска объявлений
 * @param requestId 
 * @param page 
 */
@Serializable
@SerialName("SearchAdRequest")
data class SearchAdRequest (
    override val requestId: String? = null,
    val page: BasePaginatedRequest? = null
) : BaseRequest(requestId)

