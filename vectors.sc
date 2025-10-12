val nums = Vector(1, 2, 3, -88)
val people = Vector("Hawking", "Penrose", "Witten")

println(nums)
println(people)

val nums2 = -33 +: nums
println(nums2)

val nums3 = nums2 :+ 44

println(nums3)