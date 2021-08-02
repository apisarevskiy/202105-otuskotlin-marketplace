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
 * Структура для запроса создания нового объявления
 * @param requestId 
 * @param createAd 
 * @param debug 
 */
@Serializable
data class CreateAdRequest (
    override val requestId: String? = null,
    val createAd: CreateableAd? = null,
    val debug: BaseDebugRequest? = null
) : BaseRequest(
    requestId = requestId
)

