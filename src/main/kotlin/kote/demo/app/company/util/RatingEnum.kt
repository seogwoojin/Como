package kote.demo.app.company.util

enum class RatingEnum(val level: Int) {
  B1(5),
  S5(6),
  S4(7),
  S3(8),
  S2(9),
  S1(10),
  G5(11),
  G4(12),
  G3(13),
  G2(14),
  G1(15),
  P5(16);

  companion object {
    fun fromLevel(level: Int): RatingEnum? {
      return values().find { it.level == level }
    }
  }
}
