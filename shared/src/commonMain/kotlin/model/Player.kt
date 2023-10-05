package model

enum class Player {
    PLAYER_1,
    PLAYER_2;

    fun next(): Player {
        return when (this) {
            PLAYER_1 -> PLAYER_2
            PLAYER_2 -> PLAYER_1
        }
    }
}
