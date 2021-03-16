package com.domingoslatorre.teacherbot.teacherbotbackend.util

import com.fasterxml.jackson.annotation.*
import com.fasterxml.jackson.databind.JsonNode
import org.springframework.data.domain.*
import java.util.ArrayList

@JsonIgnoreProperties(ignoreUnknown = true)
class TestPage<T> : PageImpl<T> {
    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    constructor(@JsonProperty("content") content: List<T>?,
                @JsonProperty("number") number: Int,
                @JsonProperty("size") size: Int,
                @JsonProperty("totalElements") totalElements: Long?,
                @JsonProperty("pageable") pageable: JsonNode?,
                @JsonProperty("last") last: Boolean,
                @JsonProperty("totalPages") totalPages: Int,
                @JsonProperty("sort") sort: JsonNode?,
                @JsonProperty("first") first: Boolean,
                @JsonProperty("numberOfElements") numberOfElements: Int) : super(content!!, PageRequest.of(number, size), totalElements!!) {
    }

    constructor(content: List<T>?, pageable: Pageable, total: Long) : super(content!!, pageable, total) {}
    constructor(content: List<T>?) : super(content!!) {}
    constructor() : super(ArrayList<T>()) {}
}