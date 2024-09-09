package kote.demo.app.baekjoon.entity.enums

enum class AlgorithmName(val id: Int, val description: String) {
  DP(25, "DP"),
  DFS_BFS(126, "BFS & DFS"),
  Greedy(33, "Greedy"),
  Str(158, "String"),
  Simulation(141, "Simulation"),
  BruteForce(125, ""),
  DataStructure(175, "다양한 자료구조 학습"),
  BinarySearch(12, "이진탐색"),
  Graph(7, "UF 혹은 최단경로"),
  Sort(97, "단순한 정렬"),
  Else(0, "그 외 나올수 있는 문제들");

  companion object {
    private val map = values().associateBy(AlgorithmName::id)

    fun fromId(id: Int): String? {
      return map[id]?.name
    }
  }
}
