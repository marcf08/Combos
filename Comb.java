/**
 * Class required for random numbers.
 */ 
import java.util.concurrent.ThreadLocalRandom;

/**
 * Class finds all combinations for a particular word.
 * This implementation only accepts words without
 * repeated letters. The program shuffles the letters
 * and checks that the combination is unique and does not 
 * repeat. In other words, the program has to "find"
 * the combinations in a computationally
 * expensive manner. This was used for performance 
 * testing.
 * TODO: Fix so that words with duplicate letters be used.
 * TODO: Fix stack overflow that occurs with >5 letters.
 */
public class Comb {

	public static String[] combinations;
	public static int count;
	public static int total;

	public static void main (String [] args) {

		String word = args[0].toLowerCase();

		if (isRepeat(word)) {
			System.out.println("String must have all unique characters.");
			System.exit(100);
		}

		total = calculateCombos(word);
		count = 0;

		combinations = new String[total];
		for (int i = 0; i < total; i++) {
			combinations[i] = "";
		}

		generateCombinations(word);

		for (int i = 0; i < total; i++) {
			System.out.println(combinations[i]);
		}

		System.out.println("Found " + total + " combinations including the given word.");

	}


	/**
	 * Method calculates the number of combinations. For a word
	 * of length n with no repeated characters, the number of
	 * combinations will be n!
	 * @param word the word supplied
	 * @return fac the factorial calculated from the length of the word
	 */
	private static int calculateCombos(String word) {
		int fac = 1;
		int total = word.length();

		for (int i = total; i > 0; i--) {
			fac *= i;
		}

		return fac;

	}

	/**
	 * Generates combinations of the given word. The
	 * method generates two random integers in the range 
	 * [0, Length]. It stores the randomly selected 
	 * character and deletes it from the String. The
	 * method inserts the randomly selected character
	 * into the position given by the first random 
	 * number. The method performs this operation 
	 * multiple times in order to give a shuffling
	 * effect. If the shuffled word already exists,
	 * the method recursively calls itself to try
	 * again. It proceeds in this manner until all
	 * combinations have been found.
	 * @param word the word used to generate combinations
	 */
	private static void generateCombinations(String word) {

		StringBuilder swap = new StringBuilder(word);	

		int randomOne; 
		int randomTwo;  

		for (int i = 0; i < word.length(); i++) {

			randomOne = ThreadLocalRandom.current().nextInt(0, word.length() - 1 + 1);	
			randomTwo = ThreadLocalRandom.current().nextInt(0, word.length() - 1 + 1);	
			char removed = swap.charAt(randomTwo);
			swap.deleteCharAt(randomTwo);
			swap.insert(randomOne, removed);

		}

		String shuffledWord = swap.toString();

		if ( isDuplicate(shuffledWord)) {
			generateCombinations(word);
		} else { 
			combinations[count] = shuffledWord; 
			count++;
			if (count == total) {
				return;
			}
			generateCombinations(word);
		}

	}

	/**
	 * Searches the combinations array for word.
	 * @param word the word to check against the array
	 * @return true if word is a duplicate and false otherwise
	 */
	private static boolean isDuplicate(String word) {
		for (int i = 0; i < combinations.length; i++) {
			if (combinations[i].equals(word)) {
				return true;
			}
		}
		return false;
	}



	/**
	 * Method checks the parameterized word to see
	 * if it has any duplicate letters.
	 * @param word the word to check for duplicates
	 * @return true if the word has duplicate letters and false otherwise
	 */
	private static boolean isRepeat(String word) {

		//Declare an array variable
		char [] wordArray = new char[word.length()];

		//Insert all characters from word into the word array
		for (int i = 0; i < word.length(); i++) {
			wordArray[i] = word.charAt(i);
		}

		//Check for duplicates
		for (int i = 0; i < wordArray.length; i++) {
			for (int p = i + 1; p < wordArray.length; p++) {
				if (wordArray[i] == wordArray[p]) {
					return true;
				}
			}
		}

		return false;

	}
}
