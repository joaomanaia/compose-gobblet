package core.game

interface ComputerGameEngine : GameEngine {
    suspend fun makeComputerMove()
}
