package kote.demo.app.problem.util.enum

import kotlin.random.Random

enum class AlgoWeightEnum(val weight: Float) {
  DP(2.0f),
  DFS_BFS(1.8f),
  Greedy(1.4f),
  Str(1.4f),
  Simulation(1f),
  BruteForce(0.8f),
  DataStructure(0.6f),
  BinarySearch(0.5f),
  Graph(0.4f),
  Sort(0.2f),
  Else(0.1f);

  companion object {
    fun getEnumList(): List<AlgoWeightEnum> {
      return enumValues<AlgoWeightEnum>().toList()
    }
    fun getRandomEnumByWeight(enumList: List<AlgoWeightEnum>): String {
      var totalWeight = 0f
      enumList.forEach { totalWeight += it.weight }
      val randomValue = Random.nextFloat() * totalWeight

      var cumulativeWeight = 0.0f
      for (enum in enumList) {
        cumulativeWeight += enum.weight
        if (randomValue <= cumulativeWeight) {
          return enum.name
        }
      }
      // Return the last element in case of rounding issues
      return enumList.last().name
    }
  }
}
