/*
 * Valid Anagram Solution in Scala
 * 
 * This program demonstrates an efficient algorithm for determining whether two strings
 * are anagrams of each other. An anagram is a word or phrase formed by rearranging
 * the letters of a different word or phrase, typically using all the original letters
 * exactly once.
 * 
 * Examples:
 * - "listen" and "silent" are anagrams
 * - "elbow" and "below" are anagrams  
 * - "hello" and "world" are NOT anagrams
 * 
 * Algorithm Approach:
 * This solution uses a frequency counting approach with an array to track character
 * occurrences. It's optimized for lowercase English letters (a-z) and runs in
 * O(n) time complexity with O(1) space complexity (constant 26-element array).
 * 
 * Key Concepts Demonstrated:
 * - Array-based frequency counting
 * - Character-to-index mapping using ASCII arithmetic
 * - Early termination optimization
 * - Functional programming with foreach and exists
 * - Boolean logic and conditional expressions
 */

/**
 * Solution object containing the anagram validation algorithm
 */
object Solution {
    
    /**
     * Determines if two strings are anagrams of each other
     * 
     * This method uses a frequency counting approach:
     * 1. First checks if strings have equal length (necessary condition)
     * 2. Creates a frequency array for 26 lowercase letters (a-z)
     * 3. Increments frequency for each character in the first string
     * 4. Decrements frequency for each character in the second string
     * 5. If any frequency goes negative, strings are not anagrams
     * 
     * Time Complexity: O(n) where n is the length of the strings
     * Space Complexity: O(1) using a fixed-size array of 26 elements
     * 
     * @param s the first string to compare
     * @param t the second string to compare
     * @return true if the strings are anagrams, false otherwise
     * 
     * Assumptions:
     * - Input strings contain only lowercase English letters (a-z)
     * - Empty strings are considered anagrams of each other
     */
    def isAnagram(s: String, t: String): Boolean = {
        // Early termination: anagrams must have the same length
        if (s.length != t.length) false
        else {
            // Create frequency counter for 26 lowercase letters
            // Index 0 = 'a', Index 1 = 'b', ..., Index 25 = 'z'
            val freq = new Array[Int](26)
            
            // Phase 1: Count character frequencies in the first string
            // For each character, convert to array index using ASCII arithmetic
            // 'a' - 'a' = 0, 'b' - 'a' = 1, 'c' - 'a' = 2, etc.
            s.foreach { c => 
                freq(c - 'a') += 1
            }
            
            // Phase 2: Check second string against frequency counts
            // For each character in t, decrement its frequency
            // If frequency becomes negative, we have more of this character
            // in t than in s, so they cannot be anagrams
            val bad = t.exists { c =>
                val i = c - 'a'          // Convert character to array index
                freq(i) -= 1             // Decrement frequency
                freq(i) < 0              // Check if we've seen too many of this character
            }
            
            // Return true if no character exceeded its frequency (no "bad" characters)
            !bad
        }
    }
}

/**
 * Alternative implementation approaches for educational comparison
 */
object AlternativeSolutions {
    
    /**
     * Sorting-based approach to anagram detection
     * 
     * This method sorts both strings and compares them for equality.
     * While conceptually simpler, it has O(n log n) time complexity.
     * 
     * @param s first string
     * @param t second string
     * @return true if strings are anagrams
     */
    def isAnagramSorting(s: String, t: String): Boolean = {
        s.sorted == t.sorted
    }
    
    /**
     * Map-based frequency counting approach
     * 
     * Uses Scala's Map to count character frequencies.
     * More flexible for Unicode characters but slightly less efficient.
     * 
     * @param s first string
     * @param t second string
     * @return true if strings are anagrams
     */
    def isAnagramMap(s: String, t: String): Boolean = {
        if (s.length != t.length) false
        else {
            val freqS = s.groupBy(identity).view.mapValues(_.length).toMap
            val freqT = t.groupBy(identity).view.mapValues(_.length).toMap
            freqS == freqT
        }
    }
    
    /**
     * Functional approach using character frequency comparison
     * 
     * Uses collection operations to count and compare frequencies.
     * Most functional programming style but potentially less efficient.
     * 
     * @param s first string
     * @param t second string
     * @return true if strings are anagrams
     */
    def isAnagramFunctional(s: String, t: String): Boolean = {
        def charCount(str: String): Map[Char, Int] = 
            str.toList.groupBy(identity).view.mapValues(_.size).toMap
            
        s.length == t.length && charCount(s) == charCount(t)
    }
    
    /**
     * Pure functional implementation using immutable data structures
     * 
     * This version follows strict functional programming principles:
     * - No mutation
     * - No side effects
     * - Uses only immutable data structures
     * - Referentially transparent
     * 
     * @param s first string
     * @param t second string
     * @return true if strings are anagrams
     */
    def isAnagramPure(s: String, t: String): Boolean = {
        if (s.length != t.length) false
        else {
            // Create immutable frequency count using fold
            val freqS = s.foldLeft(Map.empty[Char, Int]) { (acc, char) =>
                acc + (char -> (acc.getOrElse(char, 0) + 1))
            }
            
            val freqT = t.foldLeft(Map.empty[Char, Int]) { (acc, char) =>
                acc + (char -> (acc.getOrElse(char, 0) + 1))
            }
            
            freqS == freqT
        }
    }
    
    /**
     * Alternative pure functional approach using sorted comparison
     * 
     * Simple and purely functional - sorts both strings and compares
     * 
     * @param s first string
     * @param t second string  
     * @return true if strings are anagrams
     */
    def isAnagramPureSort(s: String, t: String): Boolean = {
        s.sorted == t.sorted
    }
    
    /**
     * Pure functional approach using character subtraction
     * 
     * Uses immutable operations to count character differences
     * 
     * @param s first string
     * @param t second string
     * @return true if strings are anagrams
     */
    def isAnagramPureSubtraction(s: String, t: String): Boolean = {
        if (s.length != t.length) false
        else {
            val charCounts = s.foldLeft(Map.empty[Char, Int]) { (acc, char) =>
                acc + (char -> (acc.getOrElse(char, 0) + 1))
            }
            
            val finalCounts = t.foldLeft(charCounts) { (acc, char) =>
                acc + (char -> (acc.getOrElse(char, 0) - 1))
            }
            
            finalCounts.values.forall(_ == 0)
        }
    }
}

/**
 * Comprehensive test suite for anagram validation
 */
object AnagramTester {
    
    /**
     * Test case data structure for organized testing
     */
    case class TestCase(s: String, t: String, expected: Boolean, description: String)
    
    /**
     * Comprehensive test cases covering various scenarios
     */
    val testCases = List(
        // Basic anagram cases
        TestCase("listen", "silent", true, "Classic anagram example"),
        TestCase("elbow", "below", true, "Simple anagram"),
        TestCase("astronomer", "moonstarer", true, "Longer anagram"),
        TestCase("conversation", "voices rant on", false, "Spaces make it invalid for our algorithm"),
        
        // Non-anagram cases
        TestCase("hello", "world", false, "Completely different letters"),
        TestCase("python", "java", false, "Different programming languages"),
        TestCase("cat", "dog", false, "Different animals"),
        
        // Edge cases
        TestCase("", "", true, "Empty strings are anagrams"),
        TestCase("a", "a", true, "Single identical character"),
        TestCase("a", "b", false, "Single different character"),
        TestCase("ab", "ba", true, "Two character anagram"),
        TestCase("ab", "aa", false, "Same length, different frequencies"),
        
        // Length mismatch cases
        TestCase("abc", "ab", false, "Different lengths"),
        TestCase("a", "aa", false, "One vs two characters"),
        TestCase("abcd", "abc", false, "Four vs three characters"),
        
        // Repeated character cases
        TestCase("aab", "aba", true, "Anagram with repeated characters"),
        TestCase("aab", "aaa", false, "Different frequencies of repeated chars"),
        TestCase("abcc", "ccba", true, "Multiple repeated characters"),
        TestCase("aabbcc", "abcabc", true, "Complex repeated pattern"),
        
        // Palindrome and special cases
        TestCase("racecar", "caracer", true, "Anagram of palindrome"),
        TestCase("deed", "edde", true, "Simple palindrome anagram"),
        TestCase("abc", "cba", true, "Reverse order"),
        
        // Boundary cases for array indexing
        TestCase("z", "z", true, "Last letter of alphabet"),
        TestCase("a", "z", false, "First vs last letter"),
        TestCase("az", "za", true, "First and last letters swapped")
    )
    
    /**
     * Runs a single test case and reports the result
     */
    def runTestCase(testCase: TestCase, algorithm: (String, String) => Boolean, algorithmName: String): Boolean = {
        val result = algorithm(testCase.s, testCase.t)
        val status = if (result == testCase.expected) "✓ PASS" else "✗ FAIL"
        println(s"$status [$algorithmName] ${testCase.description}")
        println(s"      Input: \"${testCase.s}\" vs \"${testCase.t}\"")
        println(s"      Expected: ${testCase.expected}, Got: $result")
        if (result != testCase.expected) {
            println(s"      ERROR: Test failed!")
        }
        println()
        result == testCase.expected
    }
    
    /**
     * Runs all test cases for a given algorithm
     */
    def testAlgorithm(algorithm: (String, String) => Boolean, algorithmName: String): (Int, Int) = {
        println(s"=== Testing $algorithmName ===")
        var passed = 0
        var total = 0
        
        testCases.foreach { testCase =>
            total += 1
            if (runTestCase(testCase, algorithm, algorithmName)) {
                passed += 1
            }
        }
        
        println(s"Results for $algorithmName: $passed/$total tests passed")
        if (passed == total) {
            println("🎉 All tests passed!")
        } else {
            println(s"⚠️  ${total - passed} tests failed")
        }
        println()
        
        (passed, total)
    }
}

/**
 * Performance benchmarking utilities
 */
object PerformanceTester {
    
    /**
     * Measures execution time of a function
     */
    def timeExecution[T](operation: => T): (T, Long) = {
        val startTime = System.nanoTime()
        val result = operation
        val endTime = System.nanoTime()
        (result, endTime - startTime)
    }
    
    /**
     * Benchmarks different anagram algorithms
     */
    def benchmarkAlgorithms(s: String, t: String, iterations: Int = 100000): Unit = {
        println(s"=== Performance Benchmark ===")
        println(s"Testing strings: \"$s\" vs \"$t\"")
        println(s"Iterations: $iterations")
        println()
        
        // Warm up the JVM
        for (_ <- 1 to 1000) {
            Solution.isAnagram(s, t)
            AlternativeSolutions.isAnagramSorting(s, t)
            AlternativeSolutions.isAnagramMap(s, t)
            AlternativeSolutions.isAnagramPure(s, t)
            AlternativeSolutions.isAnagramPureSort(s, t)
            AlternativeSolutions.isAnagramPureSubtraction(s, t)
        }
        
        // Benchmark each algorithm
        val algorithms = List(
            ("Frequency Array (IMPURE)", (s: String, t: String) => Solution.isAnagram(s, t)),
            ("Sorting Based", (s: String, t: String) => AlternativeSolutions.isAnagramSorting(s, t)),
            ("Map Based", (s: String, t: String) => AlternativeSolutions.isAnagramMap(s, t)),
            ("Functional", (s: String, t: String) => AlternativeSolutions.isAnagramFunctional(s, t)),
            ("Pure Map", (s: String, t: String) => AlternativeSolutions.isAnagramPure(s, t)),
            ("Pure Sort", (s: String, t: String) => AlternativeSolutions.isAnagramPureSort(s, t)),
            ("Pure Subtraction", (s: String, t: String) => AlternativeSolutions.isAnagramPureSubtraction(s, t))
        )
        
        algorithms.foreach { case (name, algorithm) =>
            val (_, totalTime) = timeExecution {
                for (_ <- 1 to iterations) {
                    algorithm(s, t)
                }
            }
            
            val avgTime = totalTime.toDouble / iterations
            println(f"$name%-15s: ${avgTime}%.2f ns per operation")
        }
    }
}

/**
 * Main application driver
 */
object ValidAnagramDemo {
    
    def main(args: Array[String]): Unit = {
        println("🔤 Valid Anagram Algorithm Demonstration")
        println("=" * 50)
        println()
        
        // Demonstrate basic functionality
        println("=== Basic Algorithm Demonstration ===")
        val examples = List(
            ("listen", "silent"),
            ("hello", "world"),
            ("elbow", "below"),
            ("abc", "cab"),
            ("rat", "car")
        )
        
        examples.foreach { case (s, t) =>
            val result = Solution.isAnagram(s, t)
            val status = if (result) "✓ ARE" else "✗ are NOT"
            println(s"\"$s\" and \"$t\" $status anagrams")
        }
        println()
        
        // Run comprehensive tests
        println("=== Comprehensive Testing Suite ===")
        val mainResults = AnagramTester.testAlgorithm(Solution.isAnagram, "Main Algorithm")
        
        // Test alternative implementations
        val sortResults = AnagramTester.testAlgorithm(AlternativeSolutions.isAnagramSorting, "Sorting Algorithm")
        val mapResults = AnagramTester.testAlgorithm(AlternativeSolutions.isAnagramMap, "Map Algorithm")
        val funcResults = AnagramTester.testAlgorithm(AlternativeSolutions.isAnagramFunctional, "Functional Algorithm")
        
        // Test pure functional implementations
        val pureResults = AnagramTester.testAlgorithm(AlternativeSolutions.isAnagramPure, "Pure Functional (Map)")
        val pureSortResults = AnagramTester.testAlgorithm(AlternativeSolutions.isAnagramPureSort, "Pure Functional (Sort)")
        val pureSubResults = AnagramTester.testAlgorithm(AlternativeSolutions.isAnagramPureSubtraction, "Pure Functional (Subtraction)")
        
        // Summary
        println("=== Test Summary ===")
        println(f"Main Algorithm (IMPURE):           ${mainResults._1}/${mainResults._2} tests passed")
        println(f"Sorting Algorithm:                 ${sortResults._1}/${sortResults._2} tests passed")
        println(f"Map Algorithm:                     ${mapResults._1}/${mapResults._2} tests passed")
        println(f"Functional Algorithm:              ${funcResults._1}/${funcResults._2} tests passed")
        println(f"Pure Functional (Map):             ${pureResults._1}/${pureResults._2} tests passed")
        println(f"Pure Functional (Sort):            ${pureSortResults._1}/${pureSortResults._2} tests passed")
        println(f"Pure Functional (Subtraction):     ${pureSubResults._1}/${pureSubResults._2} tests passed")
        println()
        
        // Performance benchmarking
        PerformanceTester.benchmarkAlgorithms("astronomer", "moonstarer", 50000)
        println()
        
        // Functional Programming Analysis
        println("=== Functional Programming Principles Analysis ===")
        println("""
🚨 FUNCTIONAL PROGRAMMING VIOLATIONS in Main Algorithm:

The original Solution.isAnagram method violates several FP principles:

1. MUTABILITY VIOLATION:
   - Uses mutable Array[Int](26) 
   - Mutates array elements with freq(i) += 1 and freq(i) -= 1
   - Violates immutability principle

2. SIDE EFFECTS:
   - foreach and exists functions contain side effects (mutation)
   - Not referentially transparent
   - Functions don't behave like mathematical functions

3. IMPERATIVE STYLE:
   - Uses imperative mutation pattern instead of functional composition
   - Breaks functional programming paradigm

✅ CORRECTED PURE FUNCTIONAL IMPLEMENTATIONS:

1. Pure Map Approach:
   - Uses immutable Maps created with foldLeft
   - No mutation, no side effects
   - Referentially transparent

2. Pure Sort Approach:
   - Simple sorted string comparison
   - Purely functional operations
   - Immutable throughout

3. Pure Subtraction Approach:
   - Uses foldLeft to build and modify immutable maps
   - Counts differences without mutation
   - All operations return new immutable structures

TRADE-OFFS:
- Pure approaches sacrifice some performance for correctness
- Immutable operations create new objects (GC pressure)
- Main algorithm is faster but violates FP principles
- Choose based on requirements: performance vs. functional purity
        """)
        
        // Algorithm complexity analysis
        println("=== Algorithm Complexity Analysis ===")
        println("""
        1. Frequency Array (Main Algorithm) - IMPURE:
           - Time Complexity: O(n) where n is string length
           - Space Complexity: O(1) using fixed 26-element array
           - ⚠️  VIOLATES: Immutability, referential transparency
           - Best for: Performance-critical applications (when FP purity not required)
           
        2. Sorting Based - PURE:
           - Time Complexity: O(n log n) due to sorting
           - Space Complexity: O(n) for sorted string copies
           - ✅ PURE: No mutation, referentially transparent
           - Best for: Simplicity, any character set
           
        3. Map Based - PURE:
           - Time Complexity: O(n) for counting + O(k) for comparison where k is unique chars
           - Space Complexity: O(k) where k is number of unique characters
           - ✅ PURE: Uses immutable operations
           - Best for: Unicode support, variable character sets
           
        4. Pure Functional Approaches - PURE:
           - Time Complexity: O(n) with immutable map operations
           - Space Complexity: O(k) for character frequency maps
           - ✅ PURE: Complete adherence to functional programming principles
           - Best for: Functional programming paradigm, educational purposes
        """)
        
        println("🎯 CONCLUSION:")
        println("   The main algorithm is highly optimized but violates FP principles.")
        println("   Pure functional alternatives maintain correctness while following")
        println("   immutability and referential transparency principles.")
        println("   Choose based on your priorities: performance vs. functional purity.")
    }
}