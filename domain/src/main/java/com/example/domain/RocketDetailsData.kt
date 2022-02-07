package com.example.domain

data class RocketDetailsData(
    val number: String,
    val basics: BasicInfoEntity = BasicInfoEntity.EMPTY,
    val mission: Mission,
    val expandables: List<Expandable>,
    val payload: Payload = Payload.EMPTY,
    val linkInfo: LinkInfo,
) {
    companion object {
        val EMPTY = RocketDetailsData(
            number = "",
            mission = Mission.EMPTY,
            expandables = emptyList(),
            payload = Payload.EMPTY,
            linkInfo = LinkInfo.EMPTY,
        )
    }
}

data class Expandable(
    val topItem: RocketSingleValue,
    val itemList: List<TitleAndTextList>,
) {
    companion object {
        val EMPTY = Expandable(
            topItem = RocketSingleValue.EMPTY,
            itemList = mutableListOf(),
        )
    }
}

data class TitleAndTextList(
    val topItem: RocketSingleValue,
    val itemList: Map<String, String>,
) {
    companion object {
        val EMPTY = TitleAndTextList(
            topItem = RocketSingleValue.EMPTY,
            itemList = emptyMap(),
        )
    }
}

data class BasicInfoEntity(
    val firstFlight: RocketSingleValue,
    val country: RocketSingleValue,
    val stages: RocketSingleValue,
    val cost: RocketSingleValue,
) {
    companion object {
        val EMPTY = BasicInfoEntity(
            firstFlight = RocketSingleValue.EMPTY,
            country = RocketSingleValue.EMPTY,
            stages = RocketSingleValue.EMPTY,
            cost = RocketSingleValue.EMPTY
        )
    }
}

data class RocketSingleValue(
    val word: String,
) {
    companion object {
        val EMPTY = RocketSingleValue(
            word = "",
        )
    }
}
